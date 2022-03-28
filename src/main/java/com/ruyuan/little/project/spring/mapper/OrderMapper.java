package com.ruyuan.little.project.spring.mapper;

import com.ruyuan.little.project.spring.dto.Order;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单mapper组件
 **/
public interface OrderMapper {

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
    Order findById(Integer id);

    /**
     * 下订单
     *
     * @param order 订单信息
     * @return 操作结果
     */
    int add(Order order);

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
     * @param orderId 订单编号
     * @param status  状态
     * @return 操作结果
     */
    int cancelOrder(@Param("orderId") Integer orderId, @Param("status") String status);

    /**
     * 订单支付
     *
     * @param orderId 订单编号
     * @param status  状态
     * @return 操作结果
     */
    int payOrder(@Param("orderId") Integer orderId, @Param("status") String status);

    /**
     * 订单评论
     *
     * @param commentId 评论id
     * @param status    状态
     * @param orderId   订单id
     * @return 影响行数
     */
    int publishComment(@Param("commentId") Integer commentId, @Param("status") String status, @Param("orderId") Integer orderId);

    /**
     * 订单完成
     *
     * @param orderId 订单编号
     * @param status  状态
     * @return 操作结果
     */
    int finishOrder(@Param("orderId") Integer orderId, @Param("status") String status);

}