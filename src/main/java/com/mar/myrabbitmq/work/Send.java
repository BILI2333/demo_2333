package com.mar.myrabbitmq.work;

import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Send {
    /**                 |----- C1
     * P------Queue---- |
     *                  |----- C2
     * **/
    private static final String QUEUE_NAME="test_work_queue";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        //创建一个链接
        Connection connection = ConnectionUtils.getConnection();
        //从链接中获取一个通道
        Channel channel = connection.createChannel();
        //声明一个队列
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        for (int i =0 ;i< 50;i++){
            String msg = "hello " + i;
            System.out.println("[MQ send]:"+msg);
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(i*20);
        }
        channel.close();
        connection.close();
    }
}
