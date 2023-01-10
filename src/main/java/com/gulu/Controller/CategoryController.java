package com.gulu.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulu.Common.R;
import com.gulu.Entity.Category;
import com.gulu.Service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * 分类管理
 */
@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
    @Autowired//自动注入自动装配指的就是使用将 Spring 容器中的 bean 自动的和我们需要这个 bean 的类组装在一起。
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category:{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    @GetMapping("page")
    public R<Page> page(int page,int pageSize){
        //分页构造器
        Page<Category> pageInfo=new Page<>(page,pageSize);
        //条件构造器，排序的条件
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        //添加排序条件，根据sort进行排序
        queryWrapper.orderByDesc(Category::getSort);

        //进行分页查询
        categoryService.page(pageInfo,queryWrapper);

        return R.success(pageInfo);
    }

    /**
     * 根据id删除分类
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> delete(Long id){
        log.info("删除分类：id为{}",id);
        categoryService.remove(id);
//        categoryService.removeById(id);

        return R.success("分类信息删除成功");
    }
}
