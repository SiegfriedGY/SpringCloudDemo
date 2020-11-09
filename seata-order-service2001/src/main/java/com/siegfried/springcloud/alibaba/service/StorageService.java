package com.siegfried.springcloud.alibaba.service;

import com.siegfried.springcloud.alibaba.domain.CommonResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "seata-storage-service")
public interface StorageService {

    @PostMapping("/storage/decrease")   //写操作都要用Post方法
    CommonResult decrease(@RequestParam("productId") Long productId, @RequestParam("count") Integer count);
}
