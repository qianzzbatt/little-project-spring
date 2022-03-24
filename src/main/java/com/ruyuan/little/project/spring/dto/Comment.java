package com.ruyuan.little.project.spring.dto;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:评价表
 **/
public class Comment {

    /**
     * 主键id
     */
    private Integer id;

    /**
     * 订单id
     */
    private Integer orderId;

    /**
     * 评分
     */
    private Integer score;

    /**
     * 评价
     */
    private String commentContent;

    /**
     * 状态 {@link CommentStatusEnum}
     */
    private String status;

    /**
     * 消费者id
     */
    private Integer consumerId;

    /**
     * 教师id
     */
    private Integer teacherId;

    /**
     * 评价时间
     */
    private String createTime;

    /**
     * 采纳时间
     */
    private String adoptTime;

    /**
     * 驳回时间
     */
    private String rejectTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getConsumerId() {
        return consumerId;
    }

    public void setConsumerId(Integer consumerId) {
        this.consumerId = consumerId;
    }

    public Integer getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Integer teacherId) {
        this.teacherId = teacherId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getAdoptTime() {
        return adoptTime;
    }

    public void setAdoptTime(String adoptTime) {
        this.adoptTime = adoptTime;
    }

    public String getRejectTime() {
        return rejectTime;
    }

    public void setRejectTime(String rejectTime) {
        this.rejectTime = rejectTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", orderId=" + orderId +
                ", score=" + score +
                ", comment='" + commentContent + '\'' +
                ", status='" + status + '\'' +
                ", consumerId=" + consumerId +
                ", teacherId=" + teacherId +
                ", createTime='" + createTime + '\'' +
                ", adoptTime='" + adoptTime + '\'' +
                ", rejectTime='" + rejectTime + '\'' +
                '}';
    }
}