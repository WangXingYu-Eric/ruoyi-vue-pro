package cn.iocoder.yudao.framework.mq.config;

import org.apache.rocketmq.spring.support.DefaultRocketMQListenerContainer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.util.StringUtils;

public class EnvironmentConfig implements BeanPostProcessor {

    private final RocketProperties rocketProperties;


    public EnvironmentConfig(RocketProperties rocketProperties) {
        this.rocketProperties = rocketProperties;
    }

    /**
     * 在装载Bean之前实现参数修改
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DefaultRocketMQListenerContainer) {
            DefaultRocketMQListenerContainer container = (DefaultRocketMQListenerContainer) bean;
            if (rocketProperties.isEnabledIsolation() && StringUtils.hasText(rocketProperties.getEnvironment())) {
                container.setTopic(String.join("_", container.getTopic(), rocketProperties.getEnvironment()));
            }
            return container;
        }
        return bean;
    }
}
