package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Common.CustomerException;
import com.gulu.DTO.SetmealDto;
import com.gulu.Entity.Setmeal;
import com.gulu.Entity.SetmealDish;
import com.gulu.Mapper.SetmealMapper;
import com.gulu.Service.SetmealDishService;
import com.gulu.Service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
    @Autowired
    private SetmealDishService setmealDishService;
    /**
     * 新增套餐同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    @Transactional//需要操作两张表，加入事务注解，要么全成功，要么全失败
    @Override
    public void saveWithDish(SetmealDto setmealDto) {
        //保存套餐的基本信息，操作setmeal表，执行insert操作
        this.save(setmealDto);
        List<SetmealDish> setmealDishes = setmealDto.getSetmealDishes();
        setmealDishes=setmealDishes.stream().map((item)->{
            item.setSetmealId(setmealDto.getId());
            return item;
        }).collect(Collectors.toList());
        //保存套餐和菜品的关联信息，操作setmeal_dish，执行insert操作
        setmealDishService.saveBatch(setmealDishes);
    }

    /**
     * 删除套餐和套餐菜品关联表
     * @param ids
     */
    @Transactional
    @Override
    public void removeWithDish(List<Long> ids) {
        //select count(*) from setmeal where id in {} and status =1
        //查询套餐状态，确定是否可以删除
        LambdaQueryWrapper<Setmeal> queryWrapper=new LambdaQueryWrapper();
        queryWrapper.in(Setmeal::getId,ids);
        queryWrapper.eq(Setmeal::getStatus,1);
        int count=this.count(queryWrapper);

        //如果不能删除，抛出一个业务异常
        if (count>0){
            throw new CustomerException("套餐正在售卖中，不能删除");
        }

        //如果可以删除，先删除套餐表中的数据 setmeal
        this.removeByIds(ids);
        //delete from setmeal_dish where setmeal_id in{1,2,3}
        LambdaQueryWrapper<SetmealDish> queryWrapper1=new LambdaQueryWrapper<>();
        queryWrapper1.in(SetmealDish::getSetmealId,ids);
        //删除关系表的数据
        setmealDishService.remove(queryWrapper1);
    }
}
