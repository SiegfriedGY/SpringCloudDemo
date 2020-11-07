package com.gengyu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gengyu.springcloud.entities.CommonResult;
import com.gengyu.springcloud.entities.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "exceptionHandler")
    public CommonResult byResource(){
        return new CommonResult(200, "按资源名限流OK", new Payment(2020L, UUID.randomUUID().toString()));
    }

    public CommonResult exceptionHandler(BlockException be){
        return new CommonResult(444, be.getClass().getCanonicalName()+ "\t 服务不可用");
    }

    @GetMapping("/rateLimit/byUrl")
    @SentinelResource(value = "byUrl") //没写 blockHandler，则调用系统默认的错误页面
    public CommonResult byUrl(){
        return new CommonResult(200, "按url限流测试OK", new Payment(2020L, "serial_002"));
    }

    // customizedBlockHandler
    @GetMapping("/rateLimit/customizedBlockHandler")
    @SentinelResource(value = "customizedBlockHandler")
    public CommonResult customizedBlockHandler(){
        return new CommonResult(200, "按客户自定义测试OK", new Payment(2020L, "serial_003"));
    }
}
