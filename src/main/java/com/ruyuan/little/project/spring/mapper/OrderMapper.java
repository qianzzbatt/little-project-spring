package com.ruyuan.little.project.spring.mapper;

import com.ruyuan.little.project.spring.dto.Order;

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

}