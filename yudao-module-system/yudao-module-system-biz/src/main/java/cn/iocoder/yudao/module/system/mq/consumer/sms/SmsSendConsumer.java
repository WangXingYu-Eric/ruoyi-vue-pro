package cn.iocoder.yudao.module.system.mq.consumer.sms;

import cn.iocoder.yudao.framework.mq.core.consumer.AbstractRocketListener;
import cn.iocoder.yudao.module.system.mq.message.sms.SmsSendMessage;
import cn.iocoder.yudao.module.system.service.sms.SmsSendService;
import cn.iocoder.yudao.framework.mq.core.stream.AbstractStreamMessageListener;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 针对 {@link SmsSendMessage} 的消费者
 *
 * @author zzf
 */
@Component
@Slf4j
@RocketMQMessageListener(consumerGroup = "springboot_consumer_group", topic = "system.sms")
public class SmsSendConsumer extends AbstractRocketListener<SmsSendMessage> {

    @Resource
    private SmsSendService smsSendService;

    @Override
    public void onConsumerMessage(SmsSendMessage message) {
        log.info("[onMessage][消息内容({})]", message);
        smsSendService.doSendSms(message);
    }

}
