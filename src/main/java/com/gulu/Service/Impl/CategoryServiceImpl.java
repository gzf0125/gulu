package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Common.CustomerException;
import com.gulu.Entity.Category;
import com.gulu.Entity.Dish;
import com.gulu.Entity.Setmeal;
import com.gulu.Mapper.CategoryMapper;
import com.gulu.Service.CategoryService;
import com.gulu.Service.DishService;
import com.gulu.Service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper,Category> implements CategoryService{
    @Autowired
    private DishService dishService;
    @Autowired
    private SetmealService setmealService;
    /**
     * 根据id删除分类，删除之前需要进行判断
     * @param id
     */
    @Override
    public void remove(Long id) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,id);
        int count = dishService.count(dishLambdaQueryWrapper);
        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if (count>0){
            //已经关联了菜品，抛出一个业务异常
            throw new CustomerException("当前分类下关联了菜品不能删除");
        }

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper=new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,id);
        int count1 = setmealService.count(setmealLambdaQueryWrapper);
        if (count1>0){
            //已经关联了套餐，抛出一个业务异常
            throw new CustomerException("当前分类下关联了套餐不能删除");
        }


        //正常删除分类
        super.removeById(id);



    }
}
