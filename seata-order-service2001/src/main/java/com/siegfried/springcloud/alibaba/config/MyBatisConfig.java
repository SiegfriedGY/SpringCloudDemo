package com.siegfried.springcloud.alibaba.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.siegfried.springcloud.alibaba.dao")
public class MyBatisConfig {
}
