package com.gulu.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulu.Entity.Dish;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
}
