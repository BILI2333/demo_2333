package com.mar.myrabbitmq.ps;

import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    public static final String EXCHANGE_NAME = "test_exchange_fanout";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();

        Channel channel = connection.createChannel();

        //声明交换机，无需在页面新增
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");//fanout--分发

        //发送消息
        String msg = "hello ps";

        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());

        System.out.println("Send :" + msg);

        channel.close();
        connection.close();
    }
}
