package com.ruyuan.little.project.spring.dto;

import java.math.BigDecimal;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:优惠券实体
 **/
public class Coupon {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 优惠券名称
     */
    private String couponName;

    /**
     * 优惠券状态
     */
    private String status;

    /**
     * 有效天数
     */
    private Integer validityDays;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 优惠券总数
     */
    private Integer total;

    /**
     * 剩余数量
     */
    private Integer remainNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getValidityDays() {
        return validityDays;
    }

    public void setValidityDays(Integer validityDays) {
        this.validityDays = validityDays;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getRemainNum() {
        return remainNum;
    }

    public void setRemainNum(Integer remainNum) {
        this.remainNum = remainNum;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", couponName='" + couponName + '\'' +
                ", status='" + status + '\'' +
                ", validityDays=" + validityDays +
                ", amount=" + amount +
                ", total=" + total +
                ", remainNum=" + remainNum +
                '}';
    }
}