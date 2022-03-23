package com.ruyuan.little.project.spring.event;

import com.ruyuan.little.project.spring.dto.Consumer;
import com.ruyuan.little.project.spring.dto.ConsumerCoupon;
import com.ruyuan.little.project.spring.dto.Coupon;
import com.ruyuan.little.project.spring.mapper.CouponMapper;
import com.ruyuan.little.project.spring.service.ConsumerService;
import com.ruyuan.little.project.spring.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:spring实战  首次登录事件监听
 **/

@Service
public class LogonListener {
    /**
     * 日志管理组件
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(LogonListener.class);

    /**
     * 第一次登陆优惠券id
     */
    @Value("${first_login_coupon_id}")
    private Integer loginCouponId;

    /**
     * 生日登录优惠券id
     */
    @Value("${birthday_login_coupon_id}")
    private Integer birthdayLoginCouponId;

    /**
     * 优惠券mapper组件
     */
    @Resource
    private CouponMapper couponMapper;

    /**
     * 优惠券service组件
     */
    @Resource
    private ConsumerService consumerService;

    /**
     * 异步执行，async注解
     * 登录事件监听器
     */
    @EventListener(LogonEvent.class)
    @Async
    public void dispatch(LogonEvent event) {
        LOGGER.info("监听到登录事件event:{}", event);
        // 模拟异步场景
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Consumer consumer = event.getConsumer();

        // 首次登录事件，分发优惠券
        if (consumer.getLoginTimes() == null || consumer.getLoginTimes() == 0) {
            LOGGER.info("消费者首次登录，发放优惠券，消费者信息{}",consumer);
            this.distributeCoupon(consumer, loginCouponId);
        }

        // 生日事件
        if (!StringUtils.isEmpty(consumer.getBirthday())) {
            // 生日日期
            LocalDate birthday = DateUtils.toLocalDate(consumer.getBirthday(),null);
            if (birthday != null && birthday.getMonthValue() == LocalDate.now().getMonthValue()) {
                // 生日当月登录 查询今年这个月有没有发放生日券
                int count = couponMapper.countByIdAndLikeTime(birthdayLoginCouponId,
                        LocalDate.now().format(DateTimeFormatter.ofPattern(DateUtils.Y_M)));
                if (count == 0) {
                    // 没有则发放生日券
                    LOGGER.info("消费者生日当月登录，发放优惠券，消费者信息{}",consumer);
                    this.distributeCoupon(consumer, birthdayLoginCouponId);
                }
            }
        }

        // 更新登录次数
        consumerService.updateLoginTimes(consumer.getId());
        LOGGER.info("登录事件监听完成");
    }

    /**
     * 分发优惠券
     *
     * @param consumer 消费者
     * @param couponId 优惠券id
     */
    private void distributeCoupon(Consumer consumer, Integer couponId) {
        Coupon coupon = couponMapper.findById(couponId);
        ConsumerCoupon consumerCoupon = new ConsumerCoupon(consumer, coupon);
        LOGGER.info("发放优惠券{}",consumerCoupon);
        couponMapper.receiveCoupon(consumerCoupon);
    }

}