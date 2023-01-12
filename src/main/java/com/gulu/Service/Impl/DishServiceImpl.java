package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Common.CustomerException;
import com.gulu.DTO.DishDto;
import com.gulu.Entity.Dish;
import com.gulu.Entity.DishFlavor;
import com.gulu.Mapper.DishMapper;
import com.gulu.Service.DishFlavorService;
import com.gulu.Service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;
    /*
    新增菜品同时保存对应的口味数据
    */
    @Transactional//事务控制
    @Override
    public void saveWithFiavor(DishDto dishDto) {
        //保存菜品的基本信息到菜品表dish
        this.save(dishDto);

        Long dishid = dishDto.getId();//菜品id
        //菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors=flavors.stream().map((item) ->{
           item.setDishId(dishid);
           return item;
        }).collect(Collectors.toList());
        //保存菜品口味数据到菜品口味表dish_flavor
        //saveBatch批量保存
        dishFlavorService.saveBatch(flavors);
    }

    /**
     * 根据id查询菜品信息和对应的口味信息
     * @param id
     * @return
     */
    @Override
    public DishDto getByIdWithFlavor(Long id) {
        //查询菜品信息，从dish表中查询
        Dish dish = this.getById(id);
        DishDto dishDto=new DishDto();
        BeanUtils.copyProperties(dish,dishDto);
        //查询当前菜品对应的口味信息，从dish_flavor表查询
        LambdaQueryWrapper<DishFlavor> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> flavors = dishFlavorService.list(queryWrapper);
        dishDto.setFlavors(flavors);
        return dishDto;
    }

    @Transactional
    @Override
    public void updateWithFiavor(DishDto dishDto) {
        //更新dish表基本信息
        this.updateById(dishDto);

        //清理当前菜品对应口味数据--dish_flavor表的delete操作
        LambdaQueryWrapper<DishFlavor> lambdaQueryWrapper=new LambdaQueryWrapper();
        lambdaQueryWrapper.eq(DishFlavor::getDishId,dishDto.getId());
        dishFlavorService.remove(lambdaQueryWrapper);
        //添加当前提交过来的口味数据--dish_flavor的insert操作
        List<DishFlavor> flavors = dishDto.getFlavors();

        flavors=flavors.stream().map((item)->{
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        dishFlavorService.saveBatch(flavors);
    }

    @Override
    public void updateStatus(List<Long> ids, int status) {
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId,ids);
        List<Dish> dishes = this.list(queryWrapper);
        for (Dish dish : dishes) {
            if (status==1){
                dish.setStatus(1);
            }else dish.setStatus(0);
        }
        this.updateBatchById(dishes);
    }

    @Override
    public void deleteWithStatus(List<Long> ids) {
        //select count(*) from setmeal where id in {} and status =1
        //查询菜品状态，确定是否可以删除
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.in(Dish::getId,ids);
        queryWrapper.eq(Dish::getStatus,1);
        int count=this.count(queryWrapper);
        //如果不能删除，抛出一个业务异常
        if (count>0){
            throw new CustomerException("菜品正在售卖中，不能删除");
        }
        //如果可以删除，先删除菜品表中的数据 dish
        this.removeByIds(ids);
        LambdaQueryWrapper<DishFlavor> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.in(DishFlavor::getDishId,ids);
        //删除关系表的数据
        dishFlavorService.remove(queryWrapper1);
    }

}
