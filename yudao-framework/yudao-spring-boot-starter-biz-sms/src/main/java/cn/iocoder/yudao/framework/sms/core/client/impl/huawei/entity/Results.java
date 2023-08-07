package cn.iocoder.yudao.framework.sms.core.client.impl.huawei.entity;

import lombok.Data;

/**
 *
 *
 * @author 陈賝
 * @since 2023/8/7 17:52
 */
@Data
public class Results {

    private String smsMsgId;

    private String from;

    private String originTo;

    private String status;

    private String createTime;

}
