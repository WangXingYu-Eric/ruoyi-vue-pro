package cn.iocoder.yudao.framework.mq.core.consumer;

import cn.hutool.core.util.TypeUtil;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.mq.core.RocketTemplate;
import cn.iocoder.yudao.framework.mq.core.interceptor.RocketMessageInterceptor;
import cn.iocoder.yudao.framework.mq.core.producer.AbstractRocketMessage;
import lombok.Setter;
import lombok.SneakyThrows;
import org.apache.rocketmq.spring.core.RocketMQListener;

import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractRocketListener<T extends AbstractRocketMessage> implements RocketMQListener<Object> {

    /**
     * 消息类型
     */
    private final Class<T> messageType;

    @Setter
    private RocketTemplate rocketTemplate;

    @SneakyThrows
    protected AbstractRocketListener() {
        this.messageType = getMessageClass();
    }

    @Override
    public void onMessage(Object message) {
        // 消费消息
        T messageObj = JsonUtils.parseObject(String.valueOf(message), messageType);
        try {
            consumeMessageBefore(messageObj);
            // 消费消息
            this.onConsumerMessage(messageObj);
        } finally {
            consumeMessageAfter(messageObj);
        }
    }

    /**
     * 处理消息
     *
     * @param message 消息
     */
    public abstract void onConsumerMessage(T message);

    private void consumeMessageBefore(AbstractRocketMessage message) {
        assert rocketTemplate != null;
        List<RocketMessageInterceptor> interceptors = rocketTemplate.getInterceptors();
        // 正序
        interceptors.forEach(interceptor -> interceptor.consumeMessageBefore(message));
    }

    private void consumeMessageAfter(AbstractRocketMessage message) {
        assert rocketTemplate != null;
        List<RocketMessageInterceptor> interceptors = rocketTemplate.getInterceptors();
        // 倒序
        for (int i = interceptors.size() - 1; i >= 0; i--) {
            interceptors.get(i).consumeMessageAfter(message);
        }
    }

    /**
     * 通过解析类上的泛型，获得消息类型
     *
     * @return 消息类型
     */
    @SuppressWarnings("unchecked")
    private Class<T> getMessageClass() {
        Type type = TypeUtil.getTypeArgument(getClass(), 0);
        if (type == null) {
            throw new IllegalStateException(String.format("类型(%s) 需要设置消息类型", getClass().getName()));
        }
        return (Class<T>) type;
    }

}
