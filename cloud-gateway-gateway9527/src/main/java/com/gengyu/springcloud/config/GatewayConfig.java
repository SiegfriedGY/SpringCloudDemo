package com.gengyu.springcloud.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {

    // Route配置可以使用配置文件，也可以用配置类
    @Bean
    public RouteLocator customizedRouteLocator(RouteLocatorBuilder builder){
        RouteLocatorBuilder.Builder routes = builder.routes();  // 得到的这个routes就相当于配文里的routes
        routes.route("payment_route3", r -> r.path("/guonei")   // 这样，访问localhost:9527/guonei即可转发到uri里面的地址
                .uri("http://news.baidu.com/baidu")).build();

        routes.route("payment_route4", r -> r.path("/qunimei")
                .uri("https://aeon.co/")).build();

//        routes.route("payment_route5", r -> r.path("/payment/get/**").uri("http://localhost:8001//payment/get/**")).build();

        return routes.build();
    }
}
