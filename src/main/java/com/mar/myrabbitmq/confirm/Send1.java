package com.mar.myrabbitmq.confirm;

import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send1 {
    public static final String QUEUE_NAME = "test_queue_confirm1";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**生产者使用confirmSelect将channel设置成confirm模式**/
        channel.confirmSelect();

        String msg = "hello confirm message";
        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        if (!channel.waitForConfirms()){
            System.out.println("message send failed");
        }else {
            System.out.println("message send ok");
        }

        channel.close();
        connection.close();
    }
}