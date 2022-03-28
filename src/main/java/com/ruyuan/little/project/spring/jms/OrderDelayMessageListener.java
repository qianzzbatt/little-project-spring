package com.ruyuan.little.project.spring.jms;

import com.alibaba.fastjson.JSON;
import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.dto.OrderDelayMessageDTO;
import com.ruyuan.little.project.spring.enums.DelayMessageTypeEnum;
import com.ruyuan.little.project.spring.enums.EducationBusinessErrorCodeEnum;
import com.ruyuan.little.project.spring.enums.OrderStatusEnum;
import com.ruyuan.little.project.spring.expection.BusinessException;
import com.ruyuan.little.project.spring.mapper.OrderMapper;
import com.ruyuan.little.project.spring.service.ConsumerService;
import com.ruyuan.little.project.spring.service.CouponService;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:延时消息listener组件
 **/
public class OrderDelayMessageListener implements MessageListenerOrderly {

    /**
     * 日志管理组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderDelayMessageListener.class);

    /**
     * 消费者service组件
     */
    @Resource
    private ConsumerService consumerService;

    /**
     * 订单mapper组件
     */
    @Resource
    private OrderMapper orderMapper;

    /**
     * 优惠券service组件
     */
    @Resource
    private CouponService couponService;

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> list, ConsumeOrderlyContext consumeOrderlyContext) {
        for (MessageExt ext : list) {
            LOGGER.info("延时消息队列消息总数{}",list.size());
            String content = new String(ext.getBody(), StandardCharsets.UTF_8);
            LOGGER.info("received order delay message:{}", content);
            // 订单消息
            OrderDelayMessageDTO orderMessage = JSON.parseObject(content, OrderDelayMessageDTO.class);
            // 订单内容
            Order order = JSON.parseObject(orderMessage.getContent(), Order.class);
            DelayMessageTypeEnum delayMessageType = orderMessage.getDelayMessageType();

            try {
                //支付超时逻辑
                if (DelayMessageTypeEnum.PAY_DELAY.equals(delayMessageType)) {
                    LOGGER.info("订单支付超时，现在开始取消订单，订单信息{}", order);
                    // 订单取消
                    order = orderMapper.findById(order.getId());
                    if (!OrderStatusEnum.WAITING_FOR_PAY.getStatus().equals(order.getStatus())){
                        throw new BusinessException(EducationBusinessErrorCodeEnum.ORDER_CANT_CANCEL.getMsg());
                    }
                    order.setStatus(OrderStatusEnum.CANCELED.getStatus());
                    // 更新订单取消时间和订单状态
                    int updateRow = orderMapper.cancelOrder(order.getId(), OrderStatusEnum.CANCELED.getStatus());
                    if (updateRow > 0) {
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
                }
                else if (DelayMessageTypeEnum.FINISH_DELAY.equals(delayMessageType)) {
                    // 订单完成
                    order = orderMapper.findById(order.getId());
                    if (!OrderStatusEnum.PAID.getStatus().equals(order.getStatus())){
                        throw new BusinessException(EducationBusinessErrorCodeEnum.ORDER_CANT_FINISHED.getMsg());
                    }
                    order.setStatus(OrderStatusEnum.WAITING_FOR_COMMENT.getStatus());
                    // 结束订单 更新订单状态和结束订单的时间
                    int updateRow = orderMapper.finishOrder(order.getId(), OrderStatusEnum.WAITING_FOR_COMMENT.getStatus());
                    if (updateRow > 0) {
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
                }
                LOGGER.info("订单延时消息处理完成");
            }catch (BusinessException e){
                // 订单已经取消，无需再取消
                LOGGER.info("订单已经取消，无需再取消");
            }
        }
        return ConsumeOrderlyStatus.SUCCESS;
    }
}