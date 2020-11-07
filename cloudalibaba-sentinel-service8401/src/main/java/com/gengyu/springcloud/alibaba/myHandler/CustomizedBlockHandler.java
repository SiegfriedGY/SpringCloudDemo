package com.gengyu.springcloud.alibaba.myHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gengyu.springcloud.entities.CommonResult;
import com.gengyu.springcloud.entities.Payment;

public class CustomizedBlockHandler {

    public static CommonResult exceptionHandler(BlockException be){
        return new CommonResult(4444, "按客户自定义测试, global exceptionHandler",
                new Payment(2020L, "serial_003"));
    }
}
