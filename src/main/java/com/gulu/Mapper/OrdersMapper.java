package com.gulu.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gulu.Entity.Orders;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OrdersMapper extends BaseMapper<Orders> {
}
