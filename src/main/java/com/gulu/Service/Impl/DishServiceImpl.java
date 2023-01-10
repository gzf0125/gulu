package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.DTO.DishDto;
import com.gulu.Entity.Dish;
import com.gulu.Entity.DishFlavor;
import com.gulu.Mapper.DishMapper;
import com.gulu.Service.DishFlavorService;
import com.gulu.Service.DishService;
import lombok.extern.slf4j.Slf4j;
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

}
