package com.ruyuan.little.project.spring.service.impl;

import com.ruyuan.little.project.spring.dto.Consumer;
import com.ruyuan.little.project.spring.dto.ConsumerCoupon;
import com.ruyuan.little.project.spring.dto.Order;
import com.ruyuan.little.project.spring.enums.EducationBusinessErrorCodeEnum;
import com.ruyuan.little.project.spring.event.LogonEventPublisher;
import com.ruyuan.little.project.spring.expection.BusinessException;
import com.ruyuan.little.project.spring.mapper.ConsumerMapper;
import com.ruyuan.little.project.spring.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:消费者service实现类组件
 **/
@Service
public class ConsumerServiceImpl implements ConsumerService {

    /**
     * 登录事件发布管理组件
     */
    @Resource
    private LogonEventPublisher publisher;

    /**
     * 消费者mapper组件
     */
    @Resource
    private ConsumerMapper consumerMapper;

    @Override
    public List<Consumer> getPage(Consumer consumer) {
        return consumerMapper.getPage(consumer);
    }


    @Override
    public Consumer findById(int id) {
        return consumerMapper.findById(id);
    }

    @Override
    public Consumer login(Consumer consumer) {
        // 根据id查找消费者，如果存在，则直接返回，不存在则调用添加方法
        Consumer findConsumer = findById(consumer.getId());
        if (findConsumer == null) {
            // 注册消费者信息
            consumerMapper.add(consumer);
            findConsumer = consumer;
        }
        // 发布登录事件
        publisher.publishEvent(findConsumer);
        return findConsumer;
    }

    @Override
    public void updateLoginTimes(Integer id) {
        consumerMapper.updateLoginTimes(id);
    }

    @Override
    public int getPageTotal(Consumer consumer) {
        return consumerMapper.getPageTotal(consumer);
    }

    @Override
    public List<ConsumerCoupon> findCouponByConsumerId(int id) {
        return consumerMapper.findCouponByConsumerId(id);
    }

    @Override
    public List<ConsumerCoupon> findCouponByConsumerIdAndStatus(Integer id, String status) {
        return consumerMapper.findCouponByConsumerIdAndStatus(id,status);
    }

    @Override
    public int orderCreateInformDeductCredits(Order order) {
        if (order.getDeductCredits() <= 0) {
            return 0;
        }
        Consumer consumer = consumerMapper.findById(order.getConsumerId());
        if (consumer.getCredits() < order.getDeductCredits()) {
            throw new BusinessException(EducationBusinessErrorCodeEnum.CREDITS_NOT_ENOUGH.getMsg());
        }
        return consumerMapper.deductCredits(order.getDeductCredits(), order.getConsumerId());
    }



}