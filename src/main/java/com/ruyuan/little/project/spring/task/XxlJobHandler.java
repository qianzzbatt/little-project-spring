package com.ruyuan.little.project.spring.task;

import com.ruyuan.little.project.spring.dto.Comment;
import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.dto.Teacher;
import com.ruyuan.little.project.spring.enums.CommentStatusEnum;
import com.ruyuan.little.project.spring.enums.OrderStatusEnum;
import com.ruyuan.little.project.spring.enums.YesOrNoEnum;
import com.ruyuan.little.project.spring.mapper.ConsumerMapper;
import com.ruyuan.little.project.spring.service.OrderService;
import com.ruyuan.little.project.spring.service.TeacherService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.context.XxlJobHelper;
import com.xxl.job.core.handler.annotation.XxlJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class XxlJobHandler {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private ConsumerMapper consumerMapper;

    @Autowired
    private OrderService orderService;


    /**
     * 定时任务更新教师评分信息
     * 此处更新教师信息，将teacher缓存全部清除
     */
    @XxlJob("updateTeacherScoreAndTeacherCount")
    @CacheEvict(value = "teacher", allEntries = true)
    public ReturnT<String> updateTeacherScore(String param) {
        XxlJobHelper.log("开始更新教师教学总数和评分等信息");
        List<Teacher> teacherList = teacherService.selectAll();
        List<Order> orderList = orderService.findByOrderStatusAndCounted(OrderStatusEnum.FINISHED.getStatus(),
                YesOrNoEnum.NO.getCode());
        Map<Integer, List<Order>> orderMap = new HashMap<>(16);
        // 将订单根据教师id存入map
        for (Order order : orderList) {
            List<Order> orderListByTeacherId = orderMap.get(order.getTeacherId());
            if (orderListByTeacherId == null) {
                orderListByTeacherId = new ArrayList<>();
            }
            orderListByTeacherId.add(order);
            orderMap.put(order.getTeacherId(), orderListByTeacherId);
        }
        List<Comment> updateCommentStatusList = new CopyOnWriteArrayList<>();
        List<Order> updateOrderCountedList = new CopyOnWriteArrayList<>();
        // 更新教师评分等信息
        for (Teacher teacher : teacherList) {
            List<Order> orderListByTeacherId = orderMap.get(teacher.getId());
            if (!CollectionUtils.isEmpty(orderListByTeacherId)) {
                AtomicInteger orderCount = new AtomicInteger(0);
                AtomicInteger commentCount = new AtomicInteger(0);
                AtomicInteger scoreTotal = new AtomicInteger(0);
                orderListByTeacherId.forEach(order -> {
                    orderCount.addAndGet(1);
                    Comment comment = order.getComment();
                    if (!StringUtils.isEmpty(order.getCommentId()) && CommentStatusEnum.NON_CHECKED.getCode().equals(comment.getStatus())) {
                        commentCount.addAndGet(1);
                        scoreTotal.addAndGet(comment.getScore());
                        comment.setStatus(CommentStatusEnum.CHECKED.getCode());
                        updateCommentStatusList.add(comment);
                    }
                    order.setCounted(YesOrNoEnum.YES.getCode());
                    updateOrderCountedList.add(order);
                });
                BigDecimal teacherCount = new BigDecimal(teacher.getTeachingCount());
                // 重新计算教师评分
                teacher.setScore(teacher.getScore().multiply(teacherCount).add(new BigDecimal(scoreTotal.get()))
                        .divide(teacherCount.add(new BigDecimal(commentCount.get())), 2, RoundingMode.HALF_UP));
                // 重新计算教师授课总数
                teacher.setTeachingCount(teacher.getTeachingCount() + orderCount.get());
            }
        }
        if (!teacherList.isEmpty()) {
            int updateTeacher = teacherService.updateTeacherList(teacherList);
        }
        if (!updateCommentStatusList.isEmpty()) {
            int updateComment = orderService.updateCommentStatusList(updateCommentStatusList);
        }
        if (!updateOrderCountedList.isEmpty()) {
            int updateOrderCounted = orderService.updateOrderCountedList(updateOrderCountedList);
        }
        XxlJobHelper.log("更新教师教学总数和评分等信息完成");
        return ReturnT.SUCCESS;
    }

    @XxlJob("expireCouponJobHandler")
    public  ReturnT<String> expireCouponJobHandler(String param){
        XxlJobHelper.log("开始更新用户优惠券状态");
        consumerMapper.expireCoupon();
        XxlJobHelper.log("更新用户优惠券状态完成");
        return ReturnT.SUCCESS;
    }
}