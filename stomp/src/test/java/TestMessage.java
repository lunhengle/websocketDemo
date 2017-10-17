import org.junit.Test;
import org.junit.runner.RunWith;
import org.lhl.stomp.bean.Greeting;
import org.lhl.stomp.bean.HelloMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-mvc.xml"})
public class TestMessage {
    /**
     * 注入发送消息.
     */
    @Autowired
    private SimpMessageSendingOperations simpMessageSendingOperations;

    @Test
    public void testMessage() {
        HelloMessage helloMessage = new HelloMessage();
        helloMessage.setName("lunhengle");
        simpMessageSendingOperations.convertAndSendToUser(helloMessage.getName(), "/message", new Greeting("hello " + helloMessage.getName()));
     /*   MessageTask task = new MessageTask(helloMessage);
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.schedule(task, 3, TimeUnit.SECONDS);
    */}

    @Test
    public void testMessageAny(){
        simpMessageSendingOperations.convertAndSend("/topic/lun",new Greeting("lunhengle"));
    }

    private class MessageTask implements Callable {
        private HelloMessage helloMessage;

        public MessageTask(HelloMessage helloMessage) {
            this.helloMessage = helloMessage;
        }

        /**
         * Computes a result, or throws an exception if unable to do so.
         *
         * @return computed result
         * @throws Exception if unable to compute a result
         */
        @Override
        public Object call() throws Exception {
            System.out.println("/message/" + helloMessage.getName());
            simpMessageSendingOperations.convertAndSendToUser(helloMessage.getName(), "/user/message", new Greeting("hello " + helloMessage.getName()));
            return helloMessage;
        }
    }
}
