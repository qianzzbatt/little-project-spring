package com.ruyuan.little.project.spring.service.impl;

import com.alibaba.fastjson.JSON;
import com.ruyuan.little.project.common.enums.MessageTypeEnum;
import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.dto.OrderDelayMessageDTO;
import com.ruyuan.little.project.spring.dto.OrderMessageDTO;
import com.ruyuan.little.project.spring.enums.DelayMessageTypeEnum;
import com.ruyuan.little.project.spring.jms.MqProducerConfiguration;
import com.ruyuan.little.project.spring.service.OrderEventManager;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单事件管理组件
 **/
@Service
public class OrderEvenManagerImpl implements OrderEventManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderEvenManagerImpl.class);

    /**
     * mq的配置类
     */
    @Resource
    private MqProducerConfiguration mqProducerConfiguration;

    /**
     * 订单延时topic
     */
    @Value("${rocketmq.order.delay.topic}")
    private String orderDelayTopic;

    /**
     * 订单延时topic
     */
    @Value("${rocketmq.order.topic}")
    private String orderTopic;

    /**
     * 订单延时消息等级
     */
    @Value("${rocketmq.order.delay.level}")
    private Integer orderDelayLevel;

    @Override
    public void createOrderEvent(Order order) {
        this.sendOrderDelayMessage(DelayMessageTypeEnum.PAY_DELAY,order);
    }


    @Override
    public void cancelOrderEvent(Order order) {
        this.sendOrderMessage(MessageTypeEnum.WX_CANCEL_ORDER, order);
    }

    @Override
    public void payOrderEvent(Order order) {
        this.sendOrderDelayMessage(DelayMessageTypeEnum.FINISH_DELAY, order);
    }

    @Override
    public void orderFinishEvent(Order order) {
        this.sendOrderMessage(MessageTypeEnum.WX_FINISHED_ORDER, order);
    }


    /**
     * 发送订单消息
     *
     * @param messageTypeEnum 订单消息类型
     * @param order 订单信息
     */
    private void sendOrderMessage(MessageTypeEnum messageTypeEnum, Order order) {
        OrderMessageDTO orderMessageDTO = new OrderMessageDTO();
        orderMessageDTO.setMessageType(messageTypeEnum);
        orderMessageDTO.setContent(JSON.toJSONString(order));
        Message message = new Message();
        message.setTopic(orderTopic);
        message.setBody(JSON.toJSONString(orderMessageDTO).getBytes(StandardCharsets.UTF_8));
        try {
            mqProducerConfiguration.getProducer().send(message);
            LOGGER.info("send order message finished messageTypeEnum:{}, orderId:{}", messageTypeEnum, order.getId());
        } catch (Exception e) {
            // 发送订单消息失败
            LOGGER.error("send order message fail,error message:{}", e.getMessage());
        }
    }


    /**
     * 订单延时消息
     * @param delayMessageTypeEnum {@link DelayMessageTypeEnum} 延时消息类型
     * @param order
     */
    private void sendOrderDelayMessage(DelayMessageTypeEnum delayMessageTypeEnum, Order order) {
        // 发送订单延时消息
        OrderDelayMessageDTO orderDelayMessageDTO = new OrderDelayMessageDTO();
        orderDelayMessageDTO.setDelayMessageType(delayMessageTypeEnum);
        orderDelayMessageDTO.setContent(JSON.toJSONString(order));
        Message message = new Message();
        message.setTopic(orderDelayTopic);
        // orderDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
        // 延时等级从1开始，这里由于ecs服务器有效时间较短，因此这里设定为5分钟。实际情况订单支付延时一般是15-30分钟
        message.setDelayTimeLevel(orderDelayLevel);
        // 内容订单号
        message.setBody(JSON.toJSONString(orderDelayMessageDTO).getBytes(StandardCharsets.UTF_8));
        try {
            mqProducerConfiguration.getProducer().send(message);
            LOGGER.info("send order delay pay message finished orderId:{} delayTimeLevel:{} delayMessageType:{}",
                    order.getId(), orderDelayLevel,delayMessageTypeEnum.getName());
        } catch (Exception e) {
            // 发送订单支付延时消息失败
            LOGGER.error("send order delay message fail,error message:{}", e.getMessage());
        }
    }


}