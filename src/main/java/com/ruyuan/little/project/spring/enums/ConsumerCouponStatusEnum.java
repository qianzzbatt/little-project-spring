package com.ruyuan.little.project.spring.enums;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:消费者优惠券状态
 **/
public enum ConsumerCouponStatusEnum {

    /**
     * 未使用
     */
    NOT_USED("0", "未使用"),

    /**
     * 已使用
     */
    USED("1", "已使用"),

    /**
     * 已过期
     */
    EXPIRED("2", "已过期"),
    ;

    private String status;

    private String desc;

    ConsumerCouponStatusEnum(String status, String desc) {
        this.status = status;
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}