package com.siegfried.springcloud.alibaba;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class) // 阻止数据源的自动创建（已自定义）
public class SeataStorageMain2002 {
    public static void main(String[] args) { SpringApplication.run(SeataStorageMain2002.class, args); }
}
