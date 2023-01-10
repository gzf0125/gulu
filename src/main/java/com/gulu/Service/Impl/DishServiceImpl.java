package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Entity.Dish;
import com.gulu.Mapper.DishMapper;
import com.gulu.Service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DishServiceImpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
