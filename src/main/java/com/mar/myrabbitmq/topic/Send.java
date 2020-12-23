package com.mar.myrabbitmq.topic;

import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_topic";
    public static void main(String[] args) throws IOException, TimeoutException {

        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME,"topic");//分发

        String msg = "商品 .....";

        channel.basicPublish(EXCHANGE_NAME,"goods.delete",null,msg.getBytes());
        System.out.println("-----send "+msg);

        channel.close();
        connection.close();
    }
}
