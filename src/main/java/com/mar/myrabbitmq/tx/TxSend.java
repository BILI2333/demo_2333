package com.mar.myrabbitmq.tx;

import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class TxSend {
    public static final String QUEUE_NAME = "test_queue_tx";
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME,false,false,false,null);

        String msg = "hello tx message";

        try {
            channel.txSelect();//将当前channel设置成transation
            channel.basicPublish("",QUEUE_NAME,null,msg.getBytes());
            /*
            错误代码，会造成事务回滚
            int xx = 1/0;*/

            System.out.println("send "+msg);
            channel.txCommit();
        }catch (Exception e){
            channel.txRollback();//事务回滚
            System.out.println(" send message rollback!");
        }

        channel.close();
        connection.close();

    }
}
