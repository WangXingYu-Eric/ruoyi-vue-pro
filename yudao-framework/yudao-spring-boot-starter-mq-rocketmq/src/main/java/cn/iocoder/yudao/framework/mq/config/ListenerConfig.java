package cn.iocoder.yudao.framework.mq.config;

import cn.iocoder.yudao.framework.mq.core.RocketTemplate;
import cn.iocoder.yudao.framework.mq.core.consumer.AbstractRocketListener;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class ListenerConfig implements BeanPostProcessor {

    private final RocketTemplate rocketTemplate;

    public ListenerConfig(RocketTemplate rocketTemplate) {
        this.rocketTemplate = rocketTemplate;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractRocketListener) {
            AbstractRocketListener<?> container = (AbstractRocketListener<?>) bean;
            container.setRocketTemplate(rocketTemplate);
            return container;
        }
        return bean;
    }
}
