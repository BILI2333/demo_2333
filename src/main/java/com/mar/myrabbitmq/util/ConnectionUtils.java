package com.mar.myrabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils{
    /**
     * MQ链接
     * **/
    public static Connection getConnection() throws IOException, TimeoutException {
        //定义一个链接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置服务地址
        factory.setHost("127.0.0.1");

        // AMQP协议 设置端口号5672
        factory.setPort(5672);

        //设置virtual host
        factory.setVirtualHost("/vhost_mar");
        //设置用户名，密码
        factory.setUsername("user_mar");
        factory.setPassword("123");

        return factory.newConnection();
    }
}
