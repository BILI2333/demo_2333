package com.mar.myrabbitmq.confirm;
/**
 * create by: Orange
 * description: 批量模式
 * create time: 2021/2/20 15:39
 * * @Param: null
 *
 * @return
 */
import com.mar.myrabbitmq.util.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.TimeoutException;

public class Send3 {
    public static final String QUEUE_NAME = "test_queue_confirm1";
    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        /**生产者使用confirmSelect将channel设置成confirm模式**/
        channel.confirmSelect();
        //未确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //通道增加监听
        channel.addConfirmListener(new ConfirmListener() {
            //handleNack
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("---handleNack-----multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("---handleNack-----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
            //没有问题的handleAck
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                if (multiple) {
                    System.out.println("---handleAck-----multiple");
                    confirmSet.headSet(deliveryTag + 1).clear();
                } else {
                    System.out.println("---handleAck-----multiple false");
                    confirmSet.remove(deliveryTag);
                }
            }
        });

        String msgStr = "ssssss";
        while (true) {
            long nextSeqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("",QUEUE_NAME , null,msgStr.getBytes());
            confirmSet.add(nextSeqNo);
        }

    }
}
