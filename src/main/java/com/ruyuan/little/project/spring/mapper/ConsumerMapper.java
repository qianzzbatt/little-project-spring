package com.ruyuan.little.project.spring.mapper;

import com.ruyuan.little.project.spring.dto.Consumer;
import com.ruyuan.little.project.spring.dto.ConsumerCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author admin
 */
public interface ConsumerMapper {

    /**
     * 添加消费者
     *
     * @param consumer 消费者实体
     * @return 结果
     */
    int add(Consumer consumer);

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
     * 扣减积分
     *
     * @param deductCredits 积分
     * @param consumerId    消费者id
     * @return 结果
     */
    int deductCredits(@Param(value = "deductCredits") Integer deductCredits,
                      @Param(value = "consumerId") Integer consumerId);

    /* *//**
     * 回退积分
     *
     * @param deductCredits 积分
     * @param consumerId    消费者id
     * @return 结果
     *//*
    int unDeductCredits(@Param(value = "deductCredits") Integer deductCredits,
                        @Param(value = "consumerId") Integer consumerId);

    *//**
     * 派发积分
     *
     * @param receiveCredits 派发积分数量
     * @param consumerId     消费者id
     * @return 影响行数
     *//*
    int receiveCredits(@Param(value = "receiveCredits") Integer receiveCredits,
                       @Param(value = "consumerId") Integer consumerId);*/

    /**
     * 更新登录次数
     *
     * @param id 消费者id
     * @return 影响行数
     */
    int updateLoginTimes(Integer id);

    /**
     * 根据消费者id查找优惠券
     *
     * @param id 消费者id
     * @return 结果
     */
    List<ConsumerCoupon> findCouponByConsumerId(int id);

    /**
     * 根据消费者id和优惠券状态查找优惠券
     *
     * @param id 消费者id
     * @param status 优惠券状态
     * @return 结果
     */
    List<ConsumerCoupon> findCouponByConsumerIdAndStatus(@Param(value = "id") Integer id,
                                                         @Param(value = "status") String status);

}