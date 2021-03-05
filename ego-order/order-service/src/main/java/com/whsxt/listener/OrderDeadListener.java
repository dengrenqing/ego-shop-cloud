package com.whsxt.listener;

import com.rabbitmq.client.Channel;
import com.whsxt.constant.QueueConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Author 武汉尚学堂
 */
@Component
@Slf4j
public class OrderDeadListener {

    /**
     * 处理订单超时未支付的监听
     *
     * @param message
     * @param channel
     */
    @RabbitListener(queues = QueueConstant.ORDER_DEAD_QUEUE, concurrency = "3-5")
    public void orderDeadHandler(Message message, Channel channel) {
        String orderNum = new String(message.getBody());
        log.error("订单{}超时了", orderNum);
        // 测试 签收一下
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
