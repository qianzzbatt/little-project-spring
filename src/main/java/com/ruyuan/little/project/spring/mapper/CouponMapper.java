package com.ruyuan.little.project.spring.mapper;

import com.ruyuan.little.project.spring.dto.ConsumerCoupon;
import com.ruyuan.little.project.spring.dto.Coupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:优惠券mapper组件
 **/
public interface CouponMapper {

    /**
     * 获取优惠券列表信息
     *
     * @param coupon 优惠券信息
     * @return 查询结果
     */
    List<Coupon> getPage(Coupon coupon);

    /**
     * 根据优惠券id查询优惠券详情
     *
     * @param id 优惠券id
     * @return 优惠券详情
     */
    Coupon findById(int id);

    /**
     * 添加优惠券
     *
     * @param coupon 优惠券信息
     * @return 操作结果
     */
    int add(Coupon coupon);

    /**
     * 消费者获取优惠券
     *
     * @param consumerCoupon 消费者优惠券信息
     * @return 操作结果
     */
    int receiveCoupon(ConsumerCoupon consumerCoupon);

    /**
     * 获取指定消费者id的指定年月的优惠券总数
     *
     * @param id   消费者id
     * @param time 年月，格式 yyyy-MM-dd
     * @return 查询数量结果
     */
    int countByIdAndLikeTime(@Param("id") int id,
                             @Param("time") String time);

    /**
     * 根据消费者优惠券id修改状态
     *
     * @param id     优惠券id
     * @param status 状态
     * @return 操作结果
     */
    int updateStatusById(@Param("id") Integer id,
                         @Param("status") String status);
}