package com.ruyuan.little.project.spring.service;

import com.ruyuan.little.project.spring.dto.Coupon;
import com.ruyuan.little.project.spring.dto.Order;

import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:优惠券service组件
 **/
public interface CouponService {

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
     * 使用消费券
     *
     * @param order 订单信息
     * @return 操作结果
     */
    int usedCoupon(Order order);

}