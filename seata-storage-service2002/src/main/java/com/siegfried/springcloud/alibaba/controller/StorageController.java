package com.siegfried.springcloud.alibaba.controller;

import com.siegfried.springcloud.alibaba.domain.CommonResult;
import com.siegfried.springcloud.alibaba.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class StorageController {

//    @Autowired
    @Resource
    private StorageService storageService;

    @PostMapping("/storage/decrease")   // 这里视频上写的是@RequestMapping
    public CommonResult decreaseStorage(Long productId, Integer count){
        storageService.decrease(productId, count);
        return new CommonResult(200, "扣减库存成功！");
    }
}
