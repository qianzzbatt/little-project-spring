package com.ruyuan.little.project.spring.service.impl;

import com.ruyuan.little.project.spring.dto.Comment;
import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.enums.CommentStatusEnum;
import com.ruyuan.little.project.spring.enums.CourseTypeEnum;
import com.ruyuan.little.project.spring.enums.EducationBusinessErrorCodeEnum;
import com.ruyuan.little.project.spring.enums.OrderStatusEnum;
import com.ruyuan.little.project.spring.expection.BusinessException;
import com.ruyuan.little.project.spring.mapper.CommentMapper;
import com.ruyuan.little.project.spring.mapper.OrderMapper;
import com.ruyuan.little.project.spring.service.ConsumerService;
import com.ruyuan.little.project.spring.service.CouponService;
import com.ruyuan.little.project.spring.service.OrderEventManager;
import com.ruyuan.little.project.spring.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单service实现类组件
 **/
@Service
public class OrderServiceImpl implements OrderService {

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

    /**
     * 消费者service组件
     */
    @Resource
    private ConsumerService consumerService;

    /**
     * 订单事件管理组件
     */
    @Resource
    private OrderEventManager orderEventManager;

    @Resource
    private CommentMapper commentMapper;

    @Override
    public List<Order> getPage(Order order) {
        List<Order> orderList = orderMapper.getPage(order);
        if (!CollectionUtils.isEmpty(orderList)){
            orderList.forEach(order1 -> {
                order1.setStatusName(OrderStatusEnum.getDescByStatus(order1.getStatus()));
                order1.setCourseName(CourseTypeEnum.getNameByCode(order1.getCourse()));
            });
        }
        return orderList;
    }

    @Override
    public Order findById(int id) {
        Order order = orderMapper.findById(id);
        order.setStatusName(OrderStatusEnum.getDescByStatus(order.getStatus()));
        order.setCourseName(CourseTypeEnum.getNameByCode(order.getCourse()));
        return order;
    }

    @Override
    public void createOrder(Order order) {
        // 根据订单金额获取积分
        BigDecimal payAmount = order.getPayAmount();
        if (payAmount != null) {
            order.setReceiveCredits(payAmount.intValue());
        }
        order.setStatus(OrderStatusEnum.WAITING_FOR_PAY.getStatus());
        order.setStatusName(OrderStatusEnum.getDescByStatus(order.getStatus()));
        order.setCourseName(CourseTypeEnum.getNameByCode(order.getCourse()));
        // 下订单
        orderMapper.add(order);
        // 使用优惠券
        couponService.usedCoupon(order);
        // 扣除积分
        consumerService.orderCreateInformDeductCredits(order);

        orderEventManager.createOrderEvent(order);
    }

    @Override
    public int getPageTotal(Order order) {
        return orderMapper.getPageTotal(order);
    }

    @Override
    public int cancelOrder(Integer orderId) {
        Order order = orderMapper.findById(orderId);
        if (!OrderStatusEnum.WAITING_FOR_PAY.getStatus().equals(order.getStatus())){
            throw new BusinessException(EducationBusinessErrorCodeEnum.ORDER_CANT_CANCEL.getMsg());
        }
        order.setStatus(OrderStatusEnum.CANCELED.getStatus());
        // 更新订单取消时间和订单状态
        int updateRow = orderMapper.cancelOrder(orderId, OrderStatusEnum.CANCELED.getStatus());
        if (updateRow > 0) {
            // 影响行数大于0，发送取消订单的消息
            orderEventManager.cancelOrderEvent(order);
        }
        return updateRow;
    }

    @Override
    public int payOrder(Integer orderId) {
        Order order = orderMapper.findById(orderId);
        if (!OrderStatusEnum.WAITING_FOR_PAY.getStatus().equals(order.getStatus())){
            throw new BusinessException(EducationBusinessErrorCodeEnum.ORDER_CANT_PAID.getMsg());
        }
        // 更新订单支付时间和订单状态
        int updateRow = orderMapper.payOrder(orderId, OrderStatusEnum.PAID.getStatus());
        //支付成功时同步发送延时完成订单消息
        if (updateRow > 0) {
            // 影响行数大于0，发送支付订单的消息
            orderEventManager.payOrderEvent(order);
        }
        return updateRow;
    }

    @Override
    public int publishComment(Integer orderId, Integer score, String commentContent) {
        // 根据id查询订单信息
        Order order = orderMapper.findById(orderId);
        if (order.getCommentId() != null) {
            throw new BusinessException(EducationBusinessErrorCodeEnum.CANT_COMMENT.getMsg());
        }

        // 封装订单的评论信息
        Comment comment = this.builderComment(orderId, score, commentContent, order);

        // 添加评论
        commentMapper.add(comment);

        // 更新订单评论信息
        return orderMapper.publishComment(comment.getId(), OrderStatusEnum.FINISHED.getStatus(), orderId);
    }

    /**
     * 构建评论信息
     *
     * @param orderId        订单id
     * @param score          评分
     * @param commentContent 订单评论信息
     * @param order          订单信息
     * @return 结果
     */
    private Comment builderComment(Integer orderId, Integer score, String commentContent, Order order) {
        Comment commentDto = new Comment();
        commentDto.setOrderId(orderId);
        commentDto.setScore(score);
        commentDto.setCommentContent(commentContent);
        commentDto.setStatus(CommentStatusEnum.NON_CHECKED.getCode());
        commentDto.setConsumerId(order.getConsumerId());
        commentDto.setTeacherId(order.getTeacherId());
        return commentDto;
    }

    @Override
    public int finishOrder(Integer orderId) {
        Order order = orderMapper.findById(orderId);
        if (!OrderStatusEnum.PAID.getStatus().equals(order.getStatus())){
            throw new BusinessException(EducationBusinessErrorCodeEnum.ORDER_CANT_FINISHED.getMsg());
        }
        order.setStatus(OrderStatusEnum.WAITING_FOR_COMMENT.getStatus());
        // 结束订单 更新订单状态和结束订单的时间
        int updateRow = orderMapper.finishOrder(orderId, OrderStatusEnum.WAITING_FOR_COMMENT.getStatus());
        if (updateRow > 0) {
            // 影响行数大于0 发送完成订单的消息
            orderEventManager.orderFinishEvent(order);
            //下面逻辑可以放到消息监听器中去处理了
/*            // 订单完成 积分增加
            if (order.getReceiveCredits() > 0) {
                consumerService.orderFinishInformReceiveCredits(order);
            }

            // 订单完成 优惠券发放
            if (order.getReceiveCouponId() != null) {
                couponService.orderFinishReceiverCoupon(order);
            }*/
        }
        return updateRow;
    }

}