package cn.iocoder.yudao.framework.mq.core.producer;

import cn.iocoder.yudao.framework.mq.core.message.AbstractMessage;
import com.fasterxml.jackson.annotation.JsonIgnore;

public abstract class AbstractRocketMessage extends AbstractMessage {

    /**
     * 获得 topic
     *
     * @return topic
     */
    @JsonIgnore // 避免序列化
    public abstract String getTopic();

    /**
     * 获得 tag
     *
     * @return tag
     */
    @JsonIgnore // 避免序列化
    public abstract String getTag();

}
