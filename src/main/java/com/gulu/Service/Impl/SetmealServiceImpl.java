package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Entity.Setmeal;
import com.gulu.Mapper.SetmealMapper;
import com.gulu.Service.SetmealService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SetmealServiceImpl extends ServiceImpl<SetmealMapper,Setmeal> implements SetmealService {
}
