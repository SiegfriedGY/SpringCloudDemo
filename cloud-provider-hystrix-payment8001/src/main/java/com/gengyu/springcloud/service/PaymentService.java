package com.gengyu.springcloud.service;

import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /**
     * 正常访问
     * @param id
     * @return
     */
    public String paymentInfo_OK(Integer id){
        return "线程池：" + Thread.currentThread().getName() + "  paymentInfo_OK, id: "  + id + "\t" + "哈哈哈";
    }

    @HystrixCommand(fallbackMethod = "paymentInfo_TimeOutHandler", commandProperties = {
            @HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="5000")
    })
    public String paymentInfo_TimeOut(Integer id){
//        int a = 10/0;       // 而对于错误，则直接自动跳到fallback方法，不需要额外进行关联。
        int seconds = 4;    // 对于超时，需要在上面的commandProperties={}里面设置，与fallback方法进行关联
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "线程池：" + Thread.currentThread().getName() + " paymentInfo_TimeOut, id: "  + id + "\t" + "哈哈哈, 耗时:" + seconds;
    }

    public String paymentInfo_TimeOutHandler(Integer id){
        return "线程池：" + Thread.currentThread().getName() + " 8001系统繁忙，请稍后再试, id: "  + id + "\t" + "这是provider端熔断方法";
    }

    // 以上都是服务降级，以下才是服务熔断 CircuitBreaker
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback", commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled", value = "true"),  //是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "10"),   // 最少请求次数（必须达到这个请求数，才开始统计，否则不统计，也不会打开断路器）
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "60"),   // (请求次数中)失败率达到多少后跳闸
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "10000"),   //下次尝试的时间窗口期（即half-open状态）,最近的多少秒
    })
    public String paymentCircuitBreaker(Integer id){
        if (id<0) {
            throw new RuntimeException("-----------id不能为负数");   // 一旦抛异常，就会自动调用底下的fallback方法，和10/0是一样的。
        }
        String serialNumber = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t" + "调用成功，流水号为：" + serialNumber;
    }
    public String paymentCircuitBreaker_fallback(Integer id){
        return "id不能为负数！id为："+ id;
    }
}
