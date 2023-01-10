package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Entity.Category;
import com.gulu.Mapper.CategoryMapper;
import com.gulu.Service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{
}
