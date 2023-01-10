package com.gulu.Service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gulu.Entity.Category;

public interface CategoryService extends IService<Category> {
    void remove(Long id);
}
