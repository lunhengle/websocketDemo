package org.lhl.websocket.service;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.annotation.Resource;
import java.util.*;

/**
 * websocket handler.
 */
@Component("websocketHandler")
public class WebsocketHandler extends TextWebSocketHandler {
    /**
     * 注入服务.
     */
    @Resource(name = "websocketService")
    private WebsocketService websocketService;
    /**
     * 定时器.
     */
    private Timer timer;
    /**
     * 用户session.
     */
    private static Map<Long, WebSocketSession> users;

    static {
        users = new HashMap<>();
    }

    /**
     * 触发链接成功 在页面上 open 方法.
     *
     * @param session
     * @throws Exception
     */
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("=======================afterConnectionEstablished");
        Long myId = (Long) session.getAttributes().get("WEBSOCKET_MY_ID");
        users.put(myId, session);
        System.out.println("afterConnectionEstablished session size ================" + users.size());
        this.sendMessage(myId);
        System.out.println("===================sendSaleRecordMessage");
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        System.out.println("=========================afterConnectionClosed");
        Long myId = (Long) session.getAttributes().get("WEBSOCKET_MY_ID");
        users.remove(myId);
        System.out.println("afterConnectionClosed session size ================" + users.size());
    }

    /**
     * 发送消息.
     *
     * @param myId 我的id
     */
    public void sendMessage(Long myId) {
        WebSocketSession session = users.get(myId);
        if (session != null) {
            timer = new Timer(true);
            long delay = 0;
            MessageTimeTask orderTimeTask = new MessageTimeTask(session, myId);
            timer.schedule(orderTimeTask, delay, 1000);
        }
    }


    /**
     * 消息时间任务.
     */
    class MessageTimeTask extends TimerTask {
        private WebSocketSession session;
        private Long myId;

        public MessageTimeTask(WebSocketSession session, Long myId) {
            this.session = session;
            this.myId = myId;
        }

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            try {
                if (session.isOpen()) {
                    List<String> messages = websocketService.getMessage(myId);
                    if (null != messages) {
                        for (String message : messages) {
                            TextMessage textMessage = new TextMessage(message);
                            session.sendMessage(textMessage);
                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
