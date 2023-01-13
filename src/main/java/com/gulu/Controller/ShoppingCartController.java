package com.gulu.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.gulu.Common.BaseContext;
import com.gulu.Common.R;
import com.gulu.Entity.Dish;
import com.gulu.Entity.ShoppingCart;
import com.gulu.Service.ShoppingCartService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @GetMapping("/list")
    public R<List<ShoppingCart>> list(){
        Long currentId = BaseContext.getCurrentId();
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);
        return R.success(shoppingCarts);
    }

    /**
     * 添加购物车
     * @param shoppingCart
     * @return
     */
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        Long currentId = BaseContext.getCurrentId();
        //设置当前用户id
        shoppingCart.setUserId(currentId);
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        queryWrapper.eq(shoppingCart.getDishFlavor()!=null,ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        //查询当前菜品或套餐是否已经在购物车,如果口味不同新建一条信息
        if (one!=null){

            if (shoppingCart.getDishId()!=null||shoppingCart.getSetmealId()!=null){
                one.setNumber(one.getNumber()+1);
            }
            LambdaUpdateWrapper<ShoppingCart> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
            updateWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            updateWrapper.eq(shoppingCart.getDishFlavor()!=null,ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
            updateWrapper.eq(ShoppingCart::getUserId,currentId);
            boolean update = shoppingCartService.update(one,updateWrapper);
        }else shoppingCartService.save(shoppingCart);
        return R.success(shoppingCart);
    }

    @PostMapping("/sub")
    public R<ShoppingCart> sub(@RequestBody ShoppingCart shoppingCart){
        Long currentId = BaseContext.getCurrentId();
        //设置当前用户id
        shoppingCart.setUserId(currentId);
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
        queryWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        queryWrapper.eq(shoppingCart.getDishFlavor()!=null,ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
        queryWrapper.eq(ShoppingCart::getUserId,currentId);
        ShoppingCart one = shoppingCartService.getOne(queryWrapper);
        //查询当前菜品或套餐是否已经在购物车,如果口味不同新建一条信息
        if (one!=null){
            if (shoppingCart.getDishId()!=null||shoppingCart.getSetmealId()!=null){
                one.setNumber(one.getNumber()-1);
                //如果数量为0，则删去该条信息
                if (one.getNumber()==0){
                    shoppingCartService.remove(queryWrapper);
                }
            }
            LambdaUpdateWrapper<ShoppingCart> updateWrapper=new LambdaUpdateWrapper<>();
            updateWrapper.eq(shoppingCart.getDishId()!=null,ShoppingCart::getDishId,shoppingCart.getDishId());
            updateWrapper.eq(shoppingCart.getSetmealId()!=null,ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
            updateWrapper.eq(shoppingCart.getDishFlavor()!=null,ShoppingCart::getDishFlavor,shoppingCart.getDishFlavor());
            updateWrapper.eq(ShoppingCart::getUserId,currentId);
            shoppingCartService.update(one,updateWrapper);
        }
        return R.success(shoppingCart);

    }

    @DeleteMapping("/clean")
    public R<String> clean(){
        Long currentId = BaseContext.getCurrentId();
        shoppingCartService.removebyUserId(currentId);
        return R.success("清空购物车成功");
    }
}
