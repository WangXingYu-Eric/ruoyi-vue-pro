package cn.iocoder.yudao.framework.mq.core.interceptor;

import cn.iocoder.yudao.framework.mq.core.message.AbstractMessage;
import cn.iocoder.yudao.framework.mq.core.producer.AbstractRocketMessage;

/**
 * {@link AbstractMessage} 消息拦截器
 * 通过拦截器，作为插件机制，实现拓展。
 * 例如说，多租户场景下的 MQ 消息处理
 *
 * @author 芋道源码
 */
public interface RocketMessageInterceptor {

    default void sendMessageBefore(AbstractRocketMessage message) {
    }

    default void sendMessageAfter(AbstractRocketMessage message) {
    }

    default void consumeMessageBefore(AbstractRocketMessage message) {
    }

    default void consumeMessageAfter(AbstractRocketMessage message) {
    }

}
