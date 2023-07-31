package cn.iocoder.yudao.framework.mq.core.message;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * Rocket 消息抽象基类
 *
 * @author 芋道源码
 */
@Data
public abstract class AbstractMessage {


    /**
     * 头部信息 业务键，用于RocketMQ控制台查看消费情况
     */
    protected String key;

    /**
     * 发送消息来源
     */
    protected String source;

    /**
     * 发送时间
     */
    protected LocalDateTime sendTime = LocalDateTime.now();

}
