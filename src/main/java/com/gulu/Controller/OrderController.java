package com.gulu.Controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.gulu.Common.BaseContext;
import com.gulu.Common.R;
import com.gulu.DTO.OrdersDto;
import com.gulu.Entity.OrderDetail;
import com.gulu.Entity.Orders;
import com.gulu.Mapper.OrdersDetailMapper;
import com.gulu.Service.OrderDetailService;
import com.gulu.Service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;

    /**
     * 用户下单
     * @param orders
     * @return
     */
    @PostMapping("/submit")
    public R<String> submit(@RequestBody Orders orders){
        log.info("订单数据：{}",orders);
        orderService.submit(orders);
        return R.success("下单成功");
    }

    @GetMapping("/userPage")
    public R<Page> page(@RequestParam int page,int pageSize){
        log.info("{},{}",page,pageSize);
        //构造分页构造器
        Page<Orders> ordersPage=new Page<>(page,pageSize);
        Page<OrdersDto> ordersDtoPage=new Page<>();
        //构造条件构造器
        LambdaQueryWrapper<Orders> queryWrapper=new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.eq(Orders::getUserId,BaseContext.getCurrentId());
        //添加排序条件
        queryWrapper.orderByDesc(Orders::getOrderTime);
        //执行查询
        orderService.page(ordersPage,queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(ordersPage,ordersDtoPage,"records");

        List<Orders> records=ordersPage.getRecords();
        List<OrdersDto> ordersDtoList=records.stream().map((item)->{
            OrdersDto ordersDto=new OrdersDto();
            BeanUtils.copyProperties(item,ordersDto);
            Long orderId = item.getId();
            LambdaQueryWrapper<OrderDetail> queryWrapper1=new LambdaQueryWrapper<>();
            queryWrapper1.eq(OrderDetail::getOrderId,orderId);
            List<OrderDetail> orderDetailList = orderDetailService.list(queryWrapper1);
            ordersDto.setOrderDetails(orderDetailList);
            return ordersDto;
        }).collect(Collectors.toList());
        ordersDtoPage.setRecords(ordersDtoList);
        return R.success(ordersDtoPage);
    }
}
