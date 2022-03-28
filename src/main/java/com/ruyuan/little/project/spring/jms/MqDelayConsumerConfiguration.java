package com.ruyuan.little.project.spring.jms;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:little@163.com">little</a>
 * version: 1.0
 * Description:mq的消费者配置类组件
 **/
public class MqDelayConsumerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqDelayConsumerConfiguration.class);

    /**
     * 消费者组名称
     */
    private String delayConsumerGroup;

    /**
     * rocketmq namesrv的地址
     */
    private String nameServerAddr;

    /**
     * delayTopic的名称
     */
    private String delayTopicName;

    /**
     * 延时消息消费者
     */
    private DefaultMQPushConsumer delayConsumer;

    /**
     * 订单延时消息的listener组件
     */
    private OrderDelayMessageListener orderDelayMessageListener;

    public MqDelayConsumerConfiguration(String nameServerAddr, String delayConsumerGroup, String delayTopicName, OrderDelayMessageListener orderDelayMessageListener) {
        this.nameServerAddr = nameServerAddr;
        this.delayConsumerGroup = delayConsumerGroup;
        this.delayTopicName = delayTopicName;
        this.orderDelayMessageListener = orderDelayMessageListener;
    }


    /**
     * bean加载是init的方法
     *
     * @throws Exception
     */
    public void init() throws Exception {
        LOGGER.info("开始启动延时消息消费者服务...");

        //创建一个消息消费者，并设置一个消息消费者组
        delayConsumer = new DefaultMQPushConsumer(delayConsumerGroup);
        //指定 NameServer 地址
        delayConsumer.setNamesrvAddr(nameServerAddr);
        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        delayConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //订阅指定 Topic 下的所有消息
        delayConsumer.subscribe(delayTopicName, "*");

        //注册消息监听器
        delayConsumer.registerMessageListener(orderDelayMessageListener);

        // 消费者对象在使用之前必须要调用 start 初始化
        delayConsumer.start();

        LOGGER.info("延时消息消费者服务启动成功.");
    }

    /**
     * bean消费方法
     */
    public void destroy() {
        LOGGER.info("开始关闭延时消息消费者服务...");
        if (delayConsumer != null) {
            delayConsumer.shutdown();
        }
        LOGGER.info("延时消息消费者服务已关闭.");
    }

}