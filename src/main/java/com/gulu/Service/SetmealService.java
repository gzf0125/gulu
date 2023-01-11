package com.gulu.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulu.DTO.SetmealDto;
import com.gulu.Entity.Setmeal;
import org.apache.ibatis.annotations.Mapper;


public interface SetmealService extends IService<Setmeal> {
    /**
     * 新增套餐同时保存套餐和菜品的关联关系
     * @param setmealDto
     */
    void saveWithDish(SetmealDto setmealDto);
}
