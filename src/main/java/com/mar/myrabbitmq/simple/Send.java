package com.mar.myrabbitmq.simple;

import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {

    private static final String QUEUE_NAME="test_simple_queue";
    public static void main(String[] aags) throws IOException, TimeoutException {
        //创建一个链接
        Connection connection = ConnectionUtils.getConnection();
        //从链接中获取一个通道
        Channel channel = connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello simple";

        channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());

        System.out.println("--send msg: "+msg);
        channel.close();
        connection.close();
    }
}
