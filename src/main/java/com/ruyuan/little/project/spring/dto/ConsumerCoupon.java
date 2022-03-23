package com.ruyuan.little.project.spring.dto;

import com.ruyuan.little.project.spring.enums.ConsumerCouponStatusEnum;
import com.ruyuan.little.project.spring.utils.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:消费者优惠券
 **/
public class ConsumerCoupon {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 消费者id
     */
    private Integer consumerId;

    /**
     * 优惠券id
     */
    private Integer couponId;

    /**
     * 消费者名称
     */
    private String consumerName;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 状态 {@link ConsumerCouponStatusEnum}
     */
    private String status;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 领取时间
     */
    private String createTime;

    /**
     * 失效时间
     */
    private String expireTime;

    public ConsumerCoupon() {

    }

    public ConsumerCoupon(Consumer consumer, Coupon coupon) {
        this.consumerId = consumer.getId();
        this.consumerName = consumer.getConsumerName();
        this.couponId = coupon.getId();
        this.couponName = coupon.getCouponName();
        this.status = ConsumerCouponStatusEnum.NOT_USED.getStatus();
        this.amount = coupon.getAmount();
        this.createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DateUtils.FULL_TIME_SPLIT_PATTERN));
        this.expireTime = LocalDateTime.of(LocalDate.now().plusDays(coupon.getValidityDays()), LocalTime.MAX)
                .format(DateTimeFormatter.ofPattern(DateUtils.FULL_TIME_SPLIT_PATTERN));
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getCouponId() {
        return couponId;
    }

    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getCouponName() {
        return couponName;
    }

    public void setCouponName(String couponName) {
        this.couponName = couponName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(String expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "ConsumerCoupon{" +
                "id=" + id +
                ", consumerId=" + consumerId +
                ", couponId=" + couponId +
                ", consumerName='" + consumerName + '\'' +
                ", couponName='" + couponName + '\'' +
                ", status='" + status + '\'' +
                ", amount=" + amount +
                ", createTime='" + createTime + '\'' +
                ", expireTime='" + expireTime + '\'' +
                '}';
    }
}