package com.gengyu.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    @Bean
    @LoadBalanced
    //注意，如果不写这个注解，直接访问consumer，就会报错，无法去找具体的服务，而不只是负载均衡不均衡的问题了！
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
