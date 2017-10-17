package org.lhl.stomp.controller;

import org.lhl.stomp.bean.Greeting;
import org.lhl.stomp.bean.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class GreetingController {
    /**
     * 注入发送消息.
     */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @SubscribeMapping("/greeting")
    public Greeting greeting() {
        System.out.println("topic/greeting.........................................");
        return new Greeting("Hello  !");
    }

    @MessageMapping("/hello")
    @SendTo("/topic/lun")
    public Greeting hello(HelloMessage helloMessage) {
        System.out.println("test topic lun..........................................");
        return new Greeting("Hello  " + helloMessage.getName() + "!");
    }

    @MessageMapping("/message")
    public Greeting message(HelloMessage helloMessage) {
        simpMessageSendingOperations.convertAndSendToUser(helloMessage.getName(), "/message", new Greeting("hello " + helloMessage.getName()));
        return new Greeting("Hello " + helloMessage.getName());
    }

}
