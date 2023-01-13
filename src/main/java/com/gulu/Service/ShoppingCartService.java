package com.gulu.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulu.Entity.ShoppingCart;

public interface ShoppingCartService extends IService<ShoppingCart> {
    void removebyUserId(Long id);
}
