package com.gulu.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulu.DTO.DishDto;
import com.gulu.Entity.Dish;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表，dish、dish_flavor
    void saveWithFiavor(DishDto dishDto);
}
