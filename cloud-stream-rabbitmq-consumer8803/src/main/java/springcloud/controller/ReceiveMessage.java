package springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

//@RestController   这是不用掉接口，是个Listener，会自动监听和接受消息
@Component
@EnableBinding(Sink.class)
@Slf4j
public class ReceiveMessage {

    @Value("${server.port}")
    private String serverPort;

    @StreamListener(Sink.INPUT)
    public void input(Message<String> message){
      log.info("消费者【" + serverPort +"】接收到消息：" + message.getPayload());  // 接受provider发送的消息
    }

}
