package com.mar.myrabbitmq.simple;

import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消费者获得消息
 * **/
public class Recv1 {
    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] args) throws IOException, TimeoutException {
        //获取链接
        Connection connection = ConnectionUtils.getConnection();
        //创建链接
        Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        DefaultConsumer consumer =new DefaultConsumer(channel){
            //获取到达消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msgString = new String(body,"utf-8");
                System.out.println("new api[]:"+msgString);
            }
        };
        //监听队列
        channel.basicConsume(QUEUE_NAME,true,consumer);
    }
}
