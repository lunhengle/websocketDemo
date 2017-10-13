package org.lhl.websocket.controller;

import org.lhl.websocket.service.WebsocketHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * websocket 处理类.
 */
@Controller
public class WebsockeController extends TextWebSocketHandler {
    @Resource
    public WebsocketHandler websocketHandler;

    @RequestMapping("/login")
    public String login(HttpServletRequest request, HttpServletResponse response) {
        Long myId = Long.valueOf(request.getParameter("myId"));
        System.out.println("login================SESSION_MY_ID ============" + myId);
        if (null != myId) {
            request.getSession().setAttribute("SESSION_MY_ID", myId);
        }
        return "/index";
    }

    /**
     * 请求发送消息.
     *
     * @param request 请求
     */
    @MessageMapping("/message/websocket")
    public void websocket(HttpServletRequest request) {
        System.out.println("websocket controller");
        Long myId = (Long) request.getSession().getAttribute("SESSION_MY_ID");
        System.out.println("websocket===============SESSION_MY_ID ============" + myId);
        websocketHandler.sendMessage(myId);
    }
}
