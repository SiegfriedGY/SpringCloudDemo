package com.gengyu.springcloud.controller;

import com.gengyu.springcloud.service.OrderHystrixService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@DefaultProperties(defaultFallback = "payment_global_fallbackMethod")
public class OrderHystrixController {

    @Autowired
    private OrderHystrixService orderHystrixService;

    @GetMapping("/consumer/payment/hystrix/ok/{id}")
    public String paymentInfo_OK(@PathVariable("id") Integer id){
        String result = orderHystrixService.paymentInfo_OK(id);
        log.info("-----result: " + result);
        return result;
    }

    @GetMapping("/consumer/payment/hystrix/timeout/{id}")
//    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
//            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="1500")
//    })
//    @HystrixCommand //要想使用默认的熔断方法，还要使用一下这个注解
    public String paymentInfo_TimeOut(@PathVariable("id") Integer id){
//        int a = 10/0;
        String result = orderHystrixService.paymentInfo_TimeOut(id);
        log.info("-----result: " + result);
        return result;
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " 80系统繁忙，请稍后再试, id: "  + id + "\t" + "这是consumer端熔断方法";
    }

    public String payment_global_fallbackMethod(){
        return "==========This is payment_global_fallbackMethod";
    }
}
