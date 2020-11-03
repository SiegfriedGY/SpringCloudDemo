package com.gengyu.springcloud.controller;

import com.gengyu.springcloud.customizedLB.LoadBalancer;
import com.gengyu.springcloud.entities.CommonResult;
import com.gengyu.springcloud.entities.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@Slf4j
public class OrderController {

//    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://cloud-payment-service";    //大小写均可
    // 注意：一旦写成服务名称（不管底下有一个服务还是多个服务），就必须启用restTemplate的@LoadBalanced功能，否则无法找到服务，会报错！！

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private LoadBalancer loadBalancer;

    @GetMapping("/consumer/payment/create")
    public CommonResult<Payment> create(Payment payment){
        return restTemplate.postForObject(PAYMENT_URL+"/payment/create", payment, CommonResult.class);
    }

    @GetMapping("/consumer/payment/get/{id}")
    public CommonResult<Payment> getPayment(@PathVariable("id") Long id){
        return restTemplate.getForObject(PAYMENT_URL+"/payment/get/"+id, CommonResult.class);
    }

    @GetMapping("/consumer/payment/getForEntity/{id}")
    public CommonResult<Payment> getPayment2(@PathVariable("id") Long id){
        ResponseEntity<CommonResult> entity = restTemplate.getForEntity(PAYMENT_URL + "/payment/get/" + id, CommonResult.class);
        if (entity.getStatusCode().is2xxSuccessful()){
            log.info("--------------" +entity.getStatusCode() + ", " + entity.getHeaders());
            return entity.getBody();
        }
        return new CommonResult<Payment>(444,"失败");
    }

    @GetMapping("/consumer/payment/lb")
    public String getPayment(){

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        if (instances == null || instances.size()==0){
            return "没有可用服务！";
        } else {
            ServiceInstance instance = loadBalancer.getInstance(instances);
            String url = instance.getUri() + "/payment/lb";
            log.info("========url:" + url);
            return restTemplate.getForObject(url, String.class);
        }
    }

    @GetMapping("/consumer/payment/zipkin")
    public String paymentZipkin(){
        log.info("啊啊啊");
//        return restTemplate.getForObject(PAYMENT_URL+"/payment/zipkin", String.class);
        // 因为我让8001的这个接口又去调8002的接口（8002也要启动），所以这里不能用微服务名来调。
        // 且一旦使用restTemplate + IP地址的方式，就不能用@LoadBalanced
        return restTemplate.getForObject("http://localhost:8001/payment/zipkin", String.class);
    }

}
