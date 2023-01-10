package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Entity.DishFlavor;
import com.gulu.Mapper.DishFlavorMapper;
import com.gulu.Mapper.DishMapper;
import com.gulu.Service.DishFlavorService;
import com.gulu.Service.DishService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper,DishFlavor> implements DishFlavorService {
}
