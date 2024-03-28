package com.tsh.sd43.controller.web_socket;

import com.tsh.sd43.infrastructure.configs.web_socket.Hello;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;   

@Controller
public class WebSocketController {

    @MessageMapping("/bills")
    @SendTo("/bill/bills")
    public Hello getListBill(@Payload Hello message) {
        System.out.println(message);
        return message ;
    }
}
