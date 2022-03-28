package com.ruyuan.little.project.spring.service;

import com.ruyuan.little.project.spring.dto.Order;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单事件通知组件
 **/
public interface OrderEventManager {

    /**
     * 创建订单事件
     *
     * @param order 订单信息
     */
    void createOrderEvent(Order order);

    /**
     * 取消订单事件
     *
     * @param order 订单信息
     */
    void cancelOrderEvent(Order order);

    /**
     * 订单支付事件
     *
     * @param order 订单信息
     */
    void payOrderEvent(Order order);

    /**
     * 订单完成事件
     *
     * @param order 订单信息
     */
    void orderFinishEvent(Order order);


}