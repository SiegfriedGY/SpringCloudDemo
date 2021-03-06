package com.gengyu.springcloud.controller;

import com.gengyu.springcloud.entities.CommonResult;
import com.gengyu.springcloud.entities.Payment;
import com.gengyu.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/payment/lb")
    public String getPayment(){
        return "此次请求的服务端口为：" + this.serverPort;
    }

    @Resource
    private DiscoveryClient discoveryClient;

    @PostMapping(value = "/payment/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("======create结果：" + result);
        if (result > 0) {
            return new CommonResult(200, "创建成功, serverPort:" + serverPort, result);
        } else {
            return new CommonResult(444, "创建失败", null);
        }
    }

    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> select(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("======select结果：" + payment);
        if (payment != null) {
            return new CommonResult<>(200, "查询成功, serverPort:" + serverPort, payment);
        } else {
            return new CommonResult<>(444, "没有对应记录，查询id为:" + id, null);
        }
    }

    @GetMapping("payment/discovery")
    private DiscoveryClient getDiscoveryClient(){

        List<String> services = discoveryClient.getServices();
        log.info("services:" + services);

        List<ServiceInstance> instances = discoveryClient.getInstances("cloud-payment-service");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + ", " + instance.getHost()
                    +", " + instance.getPort() + ", " + instance.getUri());
        }
        return this.discoveryClient;
    }

    @GetMapping("/payment/feign/timeout")
    public String testTimeOut(){
        try {
            TimeUnit.SECONDS.sleep(4);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return serverPort;
    }

    // 提供一个给8001调的接口
    @GetMapping("/payment/sleuthTest")
    public String sleuthTest(){
        return "--------------This is sleuthTest" + serverPort;
    }
}
