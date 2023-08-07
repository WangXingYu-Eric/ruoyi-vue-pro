package cn.iocoder.yudao.framework.sms.core.client.impl.huawei.entity;

import lombok.Data;

import java.util.List;

/**
 * 华为方响应参数
 *
 * @author 陈賝
 * @since 2023/8/7 17:55
 */
@Data
public class HuaweiResponse {

    /**
     * 请求返回的结果码
     */
    private String code;

    /**
     * 请求返回的结果码描述
     */
    private String description;

    /**
     * 短信ID列表，当目的号码存在多个时，每个号码都会返回一个SmsID。
     * 当返回异常响应时不携带此字段
     */
    private List<Results> result;
}
