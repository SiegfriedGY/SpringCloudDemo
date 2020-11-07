package com.gengyu.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
public class FlowLimitController {

    @GetMapping("/testA")
    public String testA(){
        // 增加处理时间，则第二个请求进来，此线程就无法处理，就能体现出线程阈值的限流效果。
/*        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
        return "-----------testA";
    }

    @GetMapping("/testB")
    public String testB(){
        log.info(Thread.currentThread().getName()+"------testB");
        return "-----------testB";
    }

    @GetMapping("/testC")
    public String testC(){
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "-----------testC";
    }

    @GetMapping("/testD")
    public String testD(){
        int a = 10/0;
        return "-----------testD";
    }

    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "testHotKey_handler") //一般而言，value用rest地址即可
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1
                            ,@RequestParam(value = "p2", required = false) String p2){
        String result = (p1==null? "p1=null":"p1="+p1+" | ").concat(p2==null? "p2=null":"p2="+p2);
        return "-----------testHotKey, result: " + result;
    }

    public String testHotKey_handler(String p1, String p2, BlockException be){
        return "testHotKey_handler...";
    }
}
