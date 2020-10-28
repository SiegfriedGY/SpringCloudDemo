package com.gengyu.springcloud.service;

import org.springframework.stereotype.Component;

@Component
public class OrderFallbackService implements OrderHystrixService {

    @Override
    public String paymentInfo_OK(Integer id) {
        return "====OrderFallbackService, paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "====OrderFallbackService, paymentInfo_TimeOut";
    }
}
