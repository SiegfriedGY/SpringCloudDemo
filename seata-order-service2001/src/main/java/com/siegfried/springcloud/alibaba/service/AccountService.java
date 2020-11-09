package com.siegfried.springcloud.alibaba.service;

import com.siegfried.springcloud.alibaba.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

@FeignClient("seata-account-service")
public interface AccountService {

    @PostMapping("/account/decrease")   //写操作都要用Post方法
    CommonResult decrease(@RequestParam("userId") Long userId, @RequestParam("money") BigDecimal money);

}

