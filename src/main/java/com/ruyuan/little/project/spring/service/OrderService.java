package com.ruyuan.little.project.spring.service;

import com.ruyuan.little.project.spring.dto.Order;

import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单service组件
 **/
public interface OrderService {


    /**
     * 获取订单列表
     *
     * @param order 订单信息
     * @return 结果雷暴
     */
    List<Order> getPage(Order order);

    /**
     * 根据id获取订单详情
     *
     * @param id 订单id
     * @return 订单详情
     */
    Order findById(int id);

    /**
     * 下订单
     *
     * @param order 订单信息
     */
    void createOrder(Order order);

    /**
     * 获取分页总数
     *
     * @param order 订单信息
     * @return 总条数
     */
    int getPageTotal(Order order);

}