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

@RestController
@Slf4j
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Value("${server.port}")
    private String serverPort;

    @Resource
    private DiscoveryClient discoveryClient;

    @GetMapping("/payment/lb")
    public String getPayment(){
        return "此次请求的服务端口为：" + this.serverPort;
    }

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

    @GetMapping("/payment/discovery")
    public Object discovery(){
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("------------" + service);
        }

        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances) {
            log.info(instance.getInstanceId() + ", " + instance.getHost()
            +", " + instance.getPort() + ", " + instance.getUri());
        }

        return this.discoveryClient;
    }
}
