package com.ruyuan.little.project.spring.service;

import com.ruyuan.little.project.spring.dto.Comment;
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

    /**
     * 订单取消
     *
     * @param orderId 订单id
     * @return 操作结果
     */
    int cancelOrder(Integer orderId);

    /**
     * 评论
     *
     * @param orderId 订单id
     * @param score   评分
     * @param commentContent 评论
     * @return 操作结果
     */
    int publishComment(Integer orderId, Integer score, String commentContent);

    /**
     * 订单完成
     *
     * @param orderId 订单编号
     * @return 操作结果
     */
    int finishOrder(Integer orderId);

    /**
     * 订单支付
     *
     * @param orderId 订单id
     * @return 操作结果
     */
    int payOrder(Integer orderId);

    /**
     * 根据状态和是否已统计查询订单信息
     *
     * @param status 状态
     * @param counted 是否已统计
     * @return 订单信息
     */
    List<Order> findByOrderStatusAndCounted(String status, String counted);

    /**
     * 定时任务批量修改评论状态信息
     *
     * @param updateCommentList 更新评论状态列表
     * @return
     */
    int updateCommentStatusList(List<Comment> updateCommentList);

    /**
     * 定时任务批量修改订单是否已统计字段信息
     *
     * @param updateOrderCountedList 更新订单是否已统计字段列表
     * @return
     */
    int updateOrderCountedList(List<Order> updateOrderCountedList);

}