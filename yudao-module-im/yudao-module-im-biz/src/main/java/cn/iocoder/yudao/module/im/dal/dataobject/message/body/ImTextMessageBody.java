package cn.iocoder.yudao.module.im.dal.dataobject.message.body;

import lombok.Data;

/**
 * 文本消息的 {@link ImMessageBody}
 *
 * @author 芋道源码
 */
@Data
public class ImTextMessageBody implements ImMessageBody {

    /**
     * 文本消息内容
     */
    private String content;

}
