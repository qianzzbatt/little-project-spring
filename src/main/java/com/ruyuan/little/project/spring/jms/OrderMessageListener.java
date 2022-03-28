package com.ruyuan.little.project.spring.jms;

import com.alibaba.fastjson.JSON;
import com.ruyuan.little.project.common.enums.MessageTypeEnum;
import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.dto.OrderMessageDTO;
import com.ruyuan.little.project.spring.service.ConsumerService;
import com.ruyuan.little.project.spring.service.CouponService;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:消息消息listener组件  主动取消消息监听
 **/
public class OrderMessageListener implements MessageListenerOrderly {

    /**
     * 日志管理组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMessageListener.class);

    /**
     * 消费者service组件
     */
    @Resource
    private ConsumerService consumerService;

    /**
     * 优惠券service组件
     */
    @Resource
    private CouponService couponService;

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        for (MessageExt ext : list) {
            String content = new String(ext.getBody(), StandardCharsets.UTF_8);
            LOGGER.info("received order  message:{}", content);
            // 订单消息
            OrderMessageDTO orderMessage = JSON.parseObject(content, OrderMessageDTO.class);
            // 订单内容
            Order order = JSON.parseObject(orderMessage.getContent(), Order.class);
            MessageTypeEnum messageType = orderMessage.getMessageType();

            if (messageType.equals(MessageTypeEnum.WX_CANCEL_ORDER)) {
                // 订单取消 积分还原
                if (order.getDeductCredits() > 0) {
                    LOGGER.info("订单取消，积分还原");
                    consumerService.orderCancelInformUnDeductCredits(order);
                }
                // 订单取消 优惠券还原
                if (!StringUtils.isEmpty(order.getConsumerCouponId())) {
                    LOGGER.info("订单取消，优惠券还原");
                    couponService.orderCancelUpdateCouponStatus(order);
                }
            }
            else if (messageType.equals(MessageTypeEnum.WX_FINISHED_ORDER)) {
                // 订单完成 积分增加
                LOGGER.info("订单完成，积分增加");
                if (order.getReceiveCredits() > 0) {
                    consumerService.orderFinishInformReceiveCredits(order);
                }
                // 订单完成 优惠券发放
                LOGGER.info("订单完成，优惠券发放");
                if (order.getReceiveCouponId() != null) {
                    couponService.orderFinishReceiverCoupon(order);
                }
            }
            LOGGER.info("订单消息处理完成");

        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}