package com.ruyuan.little.project.spring.dto;

import com.ruyuan.little.project.spring.annotation.IsDate;
import com.ruyuan.little.project.spring.constants.StringPoolConstant;
import org.springframework.util.StringUtils;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:订单实体
 **/
public class Order {

    /**
     * 起始页
     */
    private Integer startIndex = 0;

    /**
     * 结束页
     */
    private Integer endIndex = 10;

    /**
     * 页号
     */
    private Integer page = 1;

    /**
     * 每页查询条数
     */
    private Integer size = 10;

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 消费者id
     */
    private Integer consumerId;

    /**
     * 消费者名称
     */
    private String consumerName;

    /**
     * 家教老师id
     */
    private Integer teacherId;

    /**
     * 家教老师名称
     */
    private String teacherName;

    /**
     * 学科 {@link com.ruyuan.little.project.spring.enums.CourseTypeEnum}
     */
    private String course;

    /**
     * 学科 {@link com.ruyuan.little.project.spring.enums.CourseTypeEnum}
     */
    private String courseName;

    /**
     * 订单状态 {@link com.ruyuan.little.project.spring.enums.OrderStatusEnum}
     */
    private String status;

    /**
     * 订单状态描述
     */
    private String statusName;

    /**
     * 每天教学价格
     */
    private BigDecimal priceOfDay;

    @NotNull(message = "起始时间不能为空")
    @IsDate(message = "起始时间格式不正确",formatter = "yyyy-MM-dd")
    private String startDate;

    @NotNull(message = "结束时间不能为空")
    @IsDate(message = "结束时间格式不正确",formatter = "yyyy-MM-dd")
    private String endDate;

    /**
     * 订单创建时间
     */
    private String createTime;

    /**
     * 订单支付时间
     */
    private String payTime;

    /**
     * 撤销时间
     */
    private String cancelTime;

    /**
     * 完成时间
     */
    private String finishTime;

    /**
     * 间隔天数
     */
    private Integer betweenDay;

    /**
     * 总价
     */
    private BigDecimal totalPrices;

    /**
     * 扣除积分
     */
    private Integer deductCredits = 0;

    /**
     * 优惠券id
     */
    private Integer consumerCouponId;

    /**
     * 优惠券金额
     */
    private BigDecimal couponAmount;

    /**
     * 积分抵扣金额
     */
    private BigDecimal creditsAmount;

    /**
     * 支付金额
     */
    private BigDecimal payAmount;

    /**
     * 支付渠道
     */
    private String payChannel;

    /**
     * 订单收获积分
     */
    private Integer receiveCredits = 0;

    /**
     * 订单获得优惠券id
     */
    private Integer receiveCouponId;

    /**
     * 评价id
     */
    private Integer commentId;

    /**
     * 评价实体
     */
    private Comment comment;

    /**
     * 该订单是否已统计在了教师的评分和总数上
     * {@link com.ruyuan.little.project.spring.enums.YesOrNoEnum}
     */
    private String counted;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
        if (this.size != null && this.size > 0) {
            this.startIndex = (this.page - 1) * this.size;
            this.endIndex = this.page * this.size;
        }
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
        if (this.page != null && this.page > 0) {
            this.startIndex = (this.page - 1) * this.size;
            this.endIndex = this.page * this.size;
        }
    }

    public Integer getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(Integer startIndex) {
        this.startIndex = startIndex;
    }

    public Integer getEndIndex() {
        return endIndex;
    }

    public void setEndIndex(Integer endIndex) {
        this.endIndex = endIndex;
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

    public String getConsumerName() {
        return consumerName;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getPriceOfDay() {
        return priceOfDay;
    }

    public void setPriceOfDay(BigDecimal priceOfDay) {
        this.priceOfDay = priceOfDay;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        if (!StringUtils.isEmpty(endDate)){
            this.endDate = endDate.replace(StringPoolConstant.START_TIME, StringPoolConstant.END_TIME);
        }else {
            this.endDate = endDate;
        }
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPayTime() {
        return payTime;
    }

    public void setPayTime(String payTime) {
        this.payTime = payTime;
    }

    public String getCancelTime() {
        return cancelTime;
    }

    public void setCancelTime(String cancelTime) {
        this.cancelTime = cancelTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(String finishTime) {
        this.finishTime = finishTime;
    }

    public Integer getDeductCredits() {
        return deductCredits;
    }

    public void setDeductCredits(Integer deductCredits) {
        this.deductCredits = deductCredits;
    }

    public String getPayChannel() {
        return payChannel;
    }

    public void setPayChannel(String payChannel) {
        this.payChannel = payChannel;
    }

    public Integer getReceiveCredits() {
        return receiveCredits;
    }

    public void setReceiveCredits(Integer receiveCredits) {
        this.receiveCredits = receiveCredits;
    }

    public Integer getReceiveCouponId() {
        return receiveCouponId;
    }

    public void setReceiveCouponId(Integer receiveCouponId) {
        this.receiveCouponId = receiveCouponId;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public Integer getBetweenDay() {
        return betweenDay;
    }

    public void setBetweenDay(Integer betweenDay) {
        this.betweenDay = betweenDay;
    }

    public BigDecimal getTotalPrices() {
        return totalPrices;
    }

    public void setTotalPrices(BigDecimal totalPrices) {
        this.totalPrices = totalPrices;
    }

    public Integer getConsumerCouponId() {
        return consumerCouponId;
    }

    public void setConsumerCouponId(Integer consumerCouponId) {
        this.consumerCouponId = consumerCouponId;
    }

    public BigDecimal getCouponAmount() {
        return couponAmount;
    }

    public void setCouponAmount(BigDecimal couponAmount) {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getCreditsAmount() {
        return creditsAmount;
    }

    public void setCreditsAmount(BigDecimal creditsAmount) {
        this.creditsAmount = creditsAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(BigDecimal payAmount) {
        this.payAmount = payAmount;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCounted() {
        return counted;
    }

    public void setCounted(String counted) {
        this.counted = counted;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", consumerId=" + consumerId +
                ", consumerName='" + consumerName + '\'' +
                ", teacherId=" + teacherId +
                ", teacherName='" + teacherName + '\'' +
                ", course='" + course + '\'' +
                ", status='" + status + '\'' +
                ", statusName='" + statusName + '\'' +
                ", priceOfDay=" + priceOfDay +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", createTime='" + createTime + '\'' +
                ", payTime='" + payTime + '\'' +
                ", cancelTime='" + cancelTime + '\'' +
                ", finishTime='" + finishTime + '\'' +
                ", betweenDay='" + betweenDay + '\'' +
                ", totalPrices='" + totalPrices + '\'' +
                ", deductCredits=" + deductCredits +
                ", consumerCouponId='" + consumerCouponId + '\'' +
                ", couponAmount='" + couponAmount + '\'' +
                ", creditsAmount='" + creditsAmount + '\'' +
                ", payAmount='" + payAmount + '\'' +
                ", payChannel='" + payChannel + '\'' +
                ", receiveCredits='" + receiveCredits + '\'' +
                ", receiveCouponId='" + receiveCouponId + '\'' +
                ", commentId='" + commentId + '\'' +
                ", comment=" + comment +
                '}';
    }

}