package com.ruyuan.little.project.spring.service;

import com.ruyuan.little.project.spring.dto.Consumer;
import com.ruyuan.little.project.spring.dto.ConsumerCoupon;
import com.ruyuan.little.project.spring.dto.Order;

import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:消费者service组件
 **/
public interface ConsumerService {


    /**
     * 消费者登录
     *
     * @param consumer 消费者实体
     * @return 结果
     */
    Consumer login(Consumer consumer);

    /**
     * 根据条件查询总条数
     *
     * @param consumer 消费者id
     * @return 结果
     */
    int getPageTotal(Consumer consumer);

    /**
     * 分页查询消费者
     *
     * @param consumer 查询条件
     * @return 结果
     */
    List<Consumer> getPage(Consumer consumer);

    /**
     * 根据id查询消费者详情
     *
     * @param id 消费者id
     * @return 结果
     */
    Consumer findById(int id);

    /**
     * 修改用户登录次数
     *
     * @param id 消费者id
     */
    void updateLoginTimes(Integer id);

    /**
     * 根据消费者id查找优惠券
     *
     * @param id 消费者id
     * @return 结果
     */
    List<ConsumerCoupon> findCouponByConsumerId(int id);

    /**
     * 根据消费者id和优惠券状态查找优惠券
     * @param id 消费者id
     * @param status 优惠券状态
     * @return 结果
     */
    List<ConsumerCoupon> findCouponByConsumerIdAndStatus(Integer id, String status);

    /**
     * 下单通知扣减积分
     *
     * @param order 订单信息
     * @return 结果
     */
    int orderCreateInformDeductCredits(Order order);


    /**
     * 订单撤回积分回退
     *
     * @param order 订单信息
     * @return 结果
     */
    int orderCancelInformUnDeductCredits(Order order);

    /**
     * 订单结束通知接收积分
     *
     * @param order 订单信息
     * @return 结果
     */
    int orderFinishInformReceiveCredits(Order order);


}