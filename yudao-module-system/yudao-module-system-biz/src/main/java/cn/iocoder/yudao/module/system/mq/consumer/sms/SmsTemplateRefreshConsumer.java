package cn.iocoder.yudao.module.system.mq.consumer.sms;

import cn.iocoder.yudao.framework.mq.core.consumer.AbstractRocketListener;
import cn.iocoder.yudao.module.system.mq.message.sms.SmsTemplateRefreshMessage;
import cn.iocoder.yudao.module.system.service.sms.SmsTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SmsTemplateRefreshMessage} 的消费者
 *
 * @author 芋道源码
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "springboot_consumer_group", topic = "order")
public class SmsTemplateRefreshConsumer extends AbstractRocketListener<SmsTemplateRefreshMessage> {

    @Resource
    private SmsTemplateService smsTemplateService;

    @Override
    public void onConsumerMessage(SmsTemplateRefreshMessage message) {
        log.info("[onMessage][收到 SmsTemplate 刷新消息]");
        smsTemplateService.initLocalCache();
    }

}
