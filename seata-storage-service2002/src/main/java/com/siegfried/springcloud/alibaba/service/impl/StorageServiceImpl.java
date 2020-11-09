package com.siegfried.springcloud.alibaba.service.impl;

import com.siegfried.springcloud.alibaba.dao.StorageDao;
import com.siegfried.springcloud.alibaba.service.StorageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StorageServiceImpl implements StorageService {

    @Autowired
    private StorageDao storageDao;

    @Override
    public void decrease(Long productId, Integer count) {
        log.info("---------> storage service 扣减库存 starts");
        storageDao.decrease(productId, count);
        log.info("---------> storage service 扣减库存 ends");
    }
}
