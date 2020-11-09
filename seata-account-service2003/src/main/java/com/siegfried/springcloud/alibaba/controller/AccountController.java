package com.siegfried.springcloud.alibaba.controller;

import com.siegfried.springcloud.alibaba.domain.CommonResult;
import com.siegfried.springcloud.alibaba.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/account/decrease")
    public CommonResult decreaseAccount(@RequestParam("userId") Long userId, @RequestParam("money")BigDecimal money){
        accountService.decrease(userId, money);
        return new CommonResult(200, "扣除账户成功！");
    }
}
