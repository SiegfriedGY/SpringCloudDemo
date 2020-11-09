package com.gengyu.springcloud.alibaba.myHandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gengyu.springcloud.entities.CommonResult;
import com.gengyu.springcloud.entities.Payment;

public class CustomizedBlockHandler {

    public static CommonResult exceptionHandler(BlockException be){
        return new CommonResult(4444, "按客户自定义测试, global exceptionHandler----1");
    }

    public static CommonResult exceptionHandler2(BlockException be){
        return new CommonResult(4444, "按客户自定义测试, global exceptionHandler----2");
    }
}
