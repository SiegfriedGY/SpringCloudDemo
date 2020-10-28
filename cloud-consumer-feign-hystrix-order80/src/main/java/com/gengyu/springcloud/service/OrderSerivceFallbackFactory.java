package com.gengyu.springcloud.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class OrderSerivceFallbackFactory implements FallbackFactory<OrderHystrixService> {
    @Override
    public OrderHystrixService create(Throwable throwable) {
        return new OrderHystrixService() {
            @Override
            public String paymentInfo_OK(Integer id) {
                return "这是OrderSerivceFallbackFactory的paymentInfo_OK";
            }

            @Override
            public String paymentInfo_TimeOut(Integer id) {
                return "这是OrderSerivceFallbackFactory的paymentInfo_TimeOut";
            }
        };
    }
}
