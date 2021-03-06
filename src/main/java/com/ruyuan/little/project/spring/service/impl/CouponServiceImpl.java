package com.ruyuan.little.project.spring.service.impl;

import com.ruyuan.little.project.spring.dto.ConsumerCoupon;
import com.ruyuan.little.project.spring.dto.Coupon;
import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.enums.ConsumerCouponStatusEnum;
import com.ruyuan.little.project.spring.enums.EducationBusinessErrorCodeEnum;
import com.ruyuan.little.project.spring.expection.BusinessException;
import com.ruyuan.little.project.spring.mapper.CouponMapper;
import com.ruyuan.little.project.spring.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:优惠券service实现类组件
 **/
@Service
public class CouponServiceImpl implements CouponService {

    /**
     * 优惠券mapper组件
     */
    @Resource
    private CouponMapper couponMapper;

    @Override
    public List<Coupon> getPage(Coupon coupon) {
        return couponMapper.getPage(coupon);
    }

    @Override
    public Coupon findById(int id) {
        return couponMapper.findById(id);
    }

    @Override
    public int add(Coupon coupon) {
        return couponMapper.add(coupon);
    }

    @Override
    public int usedCoupon(Order order) {
        if (Objects.isNull(order.getConsumerCouponId())) {
            return 0;
        }
        return couponMapper.updateStatusById(order.getConsumerCouponId(), ConsumerCouponStatusEnum.USED.getStatus());
    }

    @Override
    public int orderCancelUpdateCouponStatus(Order order) {
        return couponMapper.updateStatusById(order.getConsumerCouponId(), ConsumerCouponStatusEnum.NOT_USED.getStatus());
    }

    @Override
    public int orderFinishReceiverCoupon(Order order) {
        Integer receiveCouponId = order.getReceiveCouponId();
        if (Objects.isNull(receiveCouponId)) {
            return 0;
        }
        Coupon coupon = couponMapper.findById(receiveCouponId);
        if (coupon == null) {
            throw new BusinessException(EducationBusinessErrorCodeEnum.COUPON_NOT_EXISTS.getMsg());
        }
        ConsumerCoupon consumerCoupon = new ConsumerCoupon(order, coupon);
        return couponMapper.receiveCoupon(consumerCoupon);
    }

}