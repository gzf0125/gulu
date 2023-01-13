package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Entity.OrderDetail;
import com.gulu.Mapper.OrdersDetailMapper;
import com.gulu.Service.OrderDetailService;
import org.springframework.stereotype.Service;

@Service
public class OrderDetailServiceImpl extends ServiceImpl<OrdersDetailMapper,OrderDetail> implements OrderDetailService {
}
