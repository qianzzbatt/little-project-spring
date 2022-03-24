package com.ruyuan.little.project.spring.service.impl;

import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.enums.CourseTypeEnum;
import com.ruyuan.little.project.spring.enums.OrderStatusEnum;
import com.ruyuan.little.project.spring.mapper.OrderMapper;
import com.ruyuan.little.project.spring.service.ConsumerService;
import com.ruyuan.little.project.spring.service.CouponService;
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
    @Autowired
    private ConsumerService consumerService;

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

    }

    @Override
    public int getPageTotal(Order order) {
        return orderMapper.getPageTotal(order);
    }

}