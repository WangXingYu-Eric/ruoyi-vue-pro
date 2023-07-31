package cn.iocoder.yudao.framework.mq.core;

import cn.iocoder.yudao.framework.mq.config.RocketProperties;
import cn.iocoder.yudao.framework.mq.core.interceptor.RocketMessageInterceptor;
import cn.iocoder.yudao.framework.mq.core.message.AbstractMessage;
import cn.iocoder.yudao.framework.mq.core.producer.AbstractRocketMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.RocketMQHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Redis MQ 操作模板类
 *
 * @author 芋道源码
 */
@AllArgsConstructor
public class RocketTemplate {

    private final RocketMQTemplate rocketMQTemplate;

    private RocketProperties rocketProperties;

    /**
     * 拦截器数组
     */
    @Getter
    private final List<RocketMessageInterceptor> interceptors = new ArrayList<>();

    /**
     * 根据系统上下文自动构建隔离后的topic
     * 构建目的地
     */
    public String buildDestination(String topic, String tag) {
        topic = reBuildTopic(topic);
        return topic + ":" + tag;
    }

    /**
     * 根据环境重新隔离topic
     *
     * @param topic 原始topic
     */
    private String reBuildTopic(String topic) {
        if (rocketProperties.isEnabledIsolation() && StringUtils.hasText(rocketProperties.getEnvironment())) {
            return topic + "_" + rocketProperties.getEnvironment();
        }
        return topic;
    }

    /**
     * 发送消息
     *
     * @param message 消息
     * @param <T>     {@link AbstractRocketMessage}
     * @return SendResult
     */
    public <T extends AbstractRocketMessage> SendResult send(T message) {
        // 注意分隔符
        return send(buildDestination(message.getTopic(), message.getTag()), message);
    }

    /**
     * 发送消息
     *
     * @param destination 定义
     * @param message     消息
     * @param <T>         {@link AbstractRocketMessage}
     * @return SendResult
     */
    public <T extends AbstractRocketMessage> SendResult send(String destination, T message) {
        // 设置业务键，此处根据公共的参数进行处理
        // 更多的其它基础业务处理...
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        try {
            sendMessageBefore(message);
            // 发送消息
            return rocketMQTemplate.syncSend(destination, sendMessage);
        } finally {
            sendMessageAfter(message);
        }
    }

    /**
     * 发送延迟消息
     *
     * @param message    消息
     * @param delayLevel 延迟时间
     * @param <T>        {@link AbstractRocketMessage}
     * @return SendResult
     */
    public <T extends AbstractRocketMessage> SendResult send(T message, int delayLevel) {
        return send(buildDestination(message.getTopic(), message.getTag()), message, delayLevel);
    }

    /**
     * 发送延迟消息
     *
     * @param destination 定义
     * @param message 消息
     * @param delayLevel 延迟时间
     * @return SendResult
     * @param <T> {@link AbstractRocketMessage}
     */
    public <T extends AbstractRocketMessage> SendResult send(String destination, T message, int delayLevel) {
        Message<T> sendMessage = MessageBuilder.withPayload(message).setHeader(RocketMQHeaders.KEYS, message.getKey()).build();
        try {
            sendMessageBefore(message);
            // 发送消息
            return rocketMQTemplate.syncSend(destination, sendMessage, delayLevel);
        } finally {
            sendMessageAfter(message);
        }
    }

    /**
     * 添加拦截器
     *
     * @param interceptor 拦截器
     */
    public void addInterceptor(RocketMessageInterceptor interceptor) {
        interceptors.add(interceptor);
    }

    private void sendMessageBefore(AbstractRocketMessage message) {
        // 正序
        interceptors.forEach(interceptor -> interceptor.sendMessageBefore(message));
    }

    private void sendMessageAfter(AbstractRocketMessage message) {
        // 倒序
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).sendMessageAfter(message);
        }
    }

}
