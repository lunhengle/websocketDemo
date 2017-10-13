package org.lhl.websocket.service;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * websocket 处理类.
 */
@Service("websocketService")
public class WebsocketServiceImpl implements WebsocketService {

    /**
     * 获取消息内容.
     *
     * @param myId 人员ID
     * @return 消息内容
     */
    @Override
    public List<String> getMessage(Long myId) {
        List<String> list = new ArrayList<>();
        if (myId.equals(1L)) {
            list.add("张三提示消息1");
            list.add("张三提示消息2");
            list.add("张三提示消息3");
            list.add("张三提示消息4");
            list.add("张三提示消息5");
        } else if (myId.equals(2L)) {
            list.add("李四提示消息1");
            list.add("李四提示消息2");
            list.add("李四提示消息3");
            list.add("李四提示消息4");
            list.add("李四提示消息5");
        }
        return list;
    }
}
