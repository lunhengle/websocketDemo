package org.lhl.websocket.service;

import org.springframework.web.socket.WebSocketSession;

import java.util.List;

/**
 * socket 服务.
 */
public interface WebsocketService {
    /**
     * 获取消息内容.
     *
     * @param myId 人员ID
     * @return 消息内容
     */
    List<String> getMessage(Long myId);

}
