package com.gulu.Service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gulu.Common.BaseContext;
import com.gulu.Common.CustomerException;
import com.gulu.Entity.*;
import com.gulu.Mapper.OrdersMapper;
import com.gulu.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrdersMapper,Orders> implements OrderService{
    /**
     * 用户下单
     * @param orders
     */
    @Autowired
    private ShoppingCartService shoppingCartService;
    @Autowired
    private UserService userService;
    @Autowired
    private AddressBookService addressBookService;
    @Autowired
    private OrderDetailService orderDetailService;


    @Transactional
    @Override
    public void submit(Orders orders) {
        //获取当前用户id
        Long userid = BaseContext.getCurrentId();
        //查询当前用户的购物车数据
        LambdaQueryWrapper<ShoppingCart> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,userid);
        List<ShoppingCart> shoppingCarts = shoppingCartService.list(queryWrapper);
        if (shoppingCarts==null||shoppingCarts.size()==0){
            throw new CustomerException("购物车为空，不能下单");
        }

        //查询用户数据
        User user = userService.getById(userid);
//        查询地址数据
        Long addressBookId = orders.getAddressBookId();
        AddressBook addressBook = addressBookService.getById(addressBookId);
        if (addressBook==null){
            throw  new CustomerException("地址有误不能下单");
        }
        //向订单表插入1条数据
        //设置订单号
        long orderId = IdWorker.getId();

        //原子操作，保证多线程安全
        AtomicInteger amount=new AtomicInteger(0);
        List<OrderDetail> orderDetails = shoppingCarts.stream().map((item) -> {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(item.getNumber());
            orderDetail.setDishFlavor(item.getDishFlavor());
            orderDetail.setDishId(item.getDishId());
            orderDetail.setSetmealId(item.getSetmealId());
            orderDetail.setName(item.getName());
            orderDetail.setImage(item.getImage());
            orderDetail.setAmount(item.getAmount());
            amount.addAndGet(item.getAmount().multiply(new BigDecimal(item.getNumber())).intValue());
            return orderDetail;
        }).collect(Collectors.toList());

        orders.setNumber(String.valueOf(orderId));
        //设置主键
        orders.setId(orderId);
        //设置下单时间
        orders.setOrderTime(LocalDateTime.now());
        //设置结账时间
        orders.setCheckoutTime(LocalDateTime.now());
        //设置订单状态
        orders.setStatus(2);//2待派送
        //设置实收金额
        orders.setAmount(new BigDecimal(amount.get()));
        //设置客户名字
        orders.setUserName(user.getName());
        //设置收件人名字
        orders.setConsignee(addressBook.getConsignee());
        //设置收件人电话
        orders.setPhone(addressBook.getPhone());
        //设置收件人地址
        orders.setAddress((addressBook.getProvinceName()!=null?addressBook.getProvinceName():"")+
                (addressBook.getCityName()!=null?addressBook.getCityName():"")+
                (addressBook.getDistrictName()!=null?addressBook.getDistrictName():"")+
                (addressBook.getDetail()==null?"":addressBook.getDetail()));
        orders.setUserId(userid);
        this.save(orders);

        //向订单明细表插入数据
        orderDetailService.saveBatch(orderDetails);

        //清空购物车
        shoppingCartService.remove(queryWrapper);

    }


}
