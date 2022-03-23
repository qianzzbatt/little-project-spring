package com.ruyuan.little.project.spring.dto;

import java.math.BigDecimal;

/**
 * @author admin
 */
public class Consumer {

    /**
     * 主键
     */
    private Integer id;

    /**
     * 消费者名称
     */
    private String consumerName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 积分
     */
    private Integer credits = 0;

    /**
     * 性别
     */
    private String sex;

    /**
     * 生日
     */
    private String birthday;

    /**
     * 购买次数
     */
    private Integer orderTimes;

    /**
     * 微信openId
     */
    private String openId;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 地址
     */
    private String address;

    /**
     * 总消费金额
     */
    private BigDecimal totalPrices;

    /**
     * 登录次数
     */
    private Integer loginTimes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getOrderTimes() {
        return orderTimes;
    }

    public void setOrderTimes(Integer orderTimes) {
        this.orderTimes = orderTimes;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(BigDecimal totalPrices) {
        this.totalPrices = totalPrices;
    }

    public Integer getLoginTimes() {
        return loginTimes;
    }

    public void setLoginTimes(Integer loginTimes) {
        this.loginTimes = loginTimes;
    }

    @Override
    public String toString() {
        return "Consumer{" +
                "id=" + id +
                ", consumerName='" + consumerName + '\'' +
                ", phone='" + phone + '\'' +
                ", credits=" + credits +
                ", sex='" + sex + '\'' +
                ", birthday='" + birthday + '\'' +
                ", orderTimes=" + orderTimes +
                ", openId='" + openId + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", totalPrices=" + totalPrices +
                ", loginTimes=" + loginTimes +
                '}';
    }
}