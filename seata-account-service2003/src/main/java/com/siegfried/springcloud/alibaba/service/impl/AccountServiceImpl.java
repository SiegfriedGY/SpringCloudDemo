package com.siegfried.springcloud.alibaba.service.impl;

import com.siegfried.springcloud.alibaba.dao.AccountDao;
import com.siegfried.springcloud.alibaba.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("------> account service 扣减账户 starts");
        accountDao.decrease(userId, money);
        log.info("------> account service 扣减账户 ends");
    }
}
