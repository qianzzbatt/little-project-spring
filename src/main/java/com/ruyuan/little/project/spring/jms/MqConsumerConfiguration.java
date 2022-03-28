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
public class MqConsumerConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(MqConsumerConfiguration.class);

    /**
     * 消费者组名称
     */
    private String consumerGroupName;

    /**
     * rocketmq namesrv的地址
     */
    private String nameServerAddr;

    /**
     * topic的名称
     */
    private String topicName;

    /**
     * 消费者
     */
    private DefaultMQPushConsumer consumer;

    /**
     * 订单消息的listener组件
     */
    private OrderMessageListener orderMessageListener;

    public MqConsumerConfiguration(String nameServerAddr, String consumerGroupName, String topicName, OrderMessageListener orderMessageListener) {
        this.nameServerAddr = nameServerAddr;
        this.consumerGroupName = consumerGroupName;
        this.topicName = topicName;
        this.orderMessageListener = orderMessageListener;
    }


    /**
     * bean加载是init的方法
     *
     * @throws Exception
     */
    public void init() throws Exception {
        LOGGER.info("开始启动消息消费者服务...");

        //创建一个消息消费者，并设置一个消息消费者组
        consumer = new DefaultMQPushConsumer(consumerGroupName);
        //指定 NameServer 地址
        consumer.setNamesrvAddr(nameServerAddr);
        //设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        //订阅指定 Topic 下的所有消息
        consumer.subscribe(topicName, "*");

        //注册消息监听器
        consumer.registerMessageListener(orderMessageListener);

        // 消费者对象在使用之前必须要调用 start 初始化
        consumer.start();

        LOGGER.info("消息消费者服务启动成功.");
    }

    /**
     * bean消费方法
     */
    public void destroy() {
        LOGGER.info("开始关闭消息消费者服务...");
        if (consumer != null) {
            consumer.shutdown();
        }
        LOGGER.info("消息消费者服务已关闭.");
    }

}