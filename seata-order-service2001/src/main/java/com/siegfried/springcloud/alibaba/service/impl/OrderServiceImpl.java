package com.siegfried.springcloud.alibaba.service.impl;

import com.siegfried.springcloud.alibaba.dao.OrderDao;
import com.siegfried.springcloud.alibaba.domain.Order;
import com.siegfried.springcloud.alibaba.service.AccountService;
import com.siegfried.springcloud.alibaba.service.OrderService;
import com.siegfried.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private StorageService storageService;

    @Autowired
    private AccountService accountService;

    @Override
    @GlobalTransactional(name = "123", rollbackFor = Exception.class)
    public void create(Order order) {

        // 一调三（调用自己的dao，且调用另外两个Feign Services）
        log.info("-------->开始创建订单");
        orderDao.create(order);

        log.info("-------->订单微服务开始调用库存做扣减, starts");
        storageService.decrease(order.getProductId(), order.getCount());
        log.info("-------->订单微服务开始调用库存做扣减, ends");

        log.info("-------->订单微服务开始调用账户做扣减, starts");
        accountService.decrease(order.getUserId(), order.getMoney());
        log.info("-------->订单微服务开始调用账户做扣减, ends");

        log.info("-------->完成订单，starts");
        orderDao.finishOrder(order.getId());
        log.info("-------->完成订单，ends");
    }

}
