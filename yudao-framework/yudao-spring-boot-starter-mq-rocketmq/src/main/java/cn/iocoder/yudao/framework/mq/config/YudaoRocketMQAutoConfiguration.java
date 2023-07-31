package cn.iocoder.yudao.framework.mq.config;

import cn.iocoder.yudao.framework.mq.core.RocketTemplate;
import cn.iocoder.yudao.framework.mq.core.consumer.AbstractRocketListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.checkerframework.checker.initialization.qual.Initialized;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Lazy;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * 消息队列配置类
 *
 * @author 芋道源码
 */
@Slf4j
@EnableConfigurationProperties(RocketProperties.class)
public class YudaoRocketMQAutoConfiguration {

    @Resource
    private RocketProperties rocketProperties;

    /**
     * 注入增强的RocketMQEnhanceTemplate
     */
    @Bean
    public RocketTemplate rocketTemplate(RocketMQTemplate rocketMQTemplate) {
        return new RocketTemplate(rocketMQTemplate, rocketProperties);
    }
    /**
     * 环境隔离配置
     */
    @Bean
    @ConditionalOnProperty(name="rocketmq.rocketmq.enabledIsolation", havingValue="true")
    public EnvironmentConfig environmentSetup(){
        return new EnvironmentConfig(rocketProperties);
    }

    /**
     * 消费者 aop
     */
    @Bean
    public ListenerConfig ListenerSetup(RocketTemplate rocketTemplate){
        return new ListenerConfig(rocketTemplate);
    }

//
//    /**
//     * 创建 Redis Pub/Sub 广播消费的容器
//     */
//    @Bean(initMethod = "start")
//    public DefaultRocketMQListenerContainer rocketMessageListenerContainer(
//            RocketTemplate rocketTemplate, List<AbstractRocketListener<?>> listeners) {
//        // 创建 RedisMessageListenerContainer 对象
//        DefaultRocketMQListenerContainer container = new DefaultRocketMQListenerContainer();
////        // 设置 RedisConnection 工厂。
//        // 添加监听器
//        listeners.forEach(listener -> {
//            listener.setRocketTemplate(rocketTemplate);
////            log.info("[redisMessageListenerContainer][注册 Channel({}) 对应的监听器({})]",
////                    listener.getChannel(), listener.getClass().getName());
//        });
//        return container;
//    }

}
