package com.siegfried.springcloud.alibaba.controller;

import com.siegfried.springcloud.alibaba.domain.CommonResult;
import com.siegfried.springcloud.alibaba.domain.Order;
import com.siegfried.springcloud.alibaba.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/order/create")
    public CommonResult createOrder(Order order){
        orderService.create(order);
        return new CommonResult(200, "创建订单成功");
    }

}
