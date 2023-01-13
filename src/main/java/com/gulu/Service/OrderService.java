package com.gulu.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulu.Entity.Orders;

public interface OrderService extends IService<Orders> {
    /**
     * 提交订单
     * @param orders
     */
    void submit(Orders orders);
}
