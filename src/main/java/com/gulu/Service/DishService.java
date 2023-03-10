package com.gulu.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulu.DTO.DishDto;
import com.gulu.Entity.Dish;

import java.util.List;

public interface DishService extends IService<Dish> {
    //新增菜品，同时插入菜品对应的口味数据，需要操作两张表，dish、dish_flavor
    void saveWithFiavor(DishDto dishDto);

    //根据id查询菜品信息和对应的口味信息
    DishDto getByIdWithFlavor(Long id);

    //更新菜品信息，同时更新对应的口味信息
    void updateWithFiavor(DishDto dishDto);

    void updateStatus(List<Long> ids, int status);

    void deleteWithStatus(List<Long> ids);
}
