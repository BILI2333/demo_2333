package com.mar.myrabbitmq.ps;

import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Recv2 {
    public static final String EXCHANGE_NAME = "test_exchange_fanout";
    private static final String QUEUE_NAME = "test_queue_fanout_sms";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        //声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列
        channel.queueBind(QUEUE_NAME,EXCHANGE_NAME,"");

        channel.basicQos(1);
        //定义一个消费者
        Consumer consumer = new DefaultConsumer(channel){
            //消息到达触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body) throws IOException {
                String msg = new String(body,"utf-8");
                System.out.println("[2] Recv msg:"+msg);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                finally {
                    System.out.println("[2] done");
                }
                //手动回执一个消息
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        };
        boolean autoAck = false;//自动应答改成false
        channel.basicConsume(QUEUE_NAME,autoAck,consumer);
    }
    
}
