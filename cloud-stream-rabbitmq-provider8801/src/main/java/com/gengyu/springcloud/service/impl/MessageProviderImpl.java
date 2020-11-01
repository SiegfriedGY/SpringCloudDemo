package com.gengyu.springcloud.service.impl;

import com.gengyu.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import java.util.UUID;

// 这里不再需要spring的@Service
@EnableBinding(Source.class) //定义消息的推送管道
@Slf4j
public class MessageProviderImpl implements IMessageProvider {

    @Autowired
    private MessageChannel output;  // 消息发送管道

    @Override
    public String send() {
        String serialNum = UUID.randomUUID().toString();
        output.send(MessageBuilder.withPayload(serialNum).build()); //构建出一个消息
        log.info("----------serialNum:" + serialNum);
        return serialNum;
    }
}
