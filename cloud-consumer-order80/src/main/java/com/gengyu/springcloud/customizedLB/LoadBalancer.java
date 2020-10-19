package com.gengyu.springcloud.customizedLB;

import org.springframework.cloud.client.ServiceInstance;

import java.util.List;

/**
 *  自定义负载均衡算法
 */
public interface LoadBalancer {
    ServiceInstance getInstance(List<ServiceInstance> serviceInstances);
}
