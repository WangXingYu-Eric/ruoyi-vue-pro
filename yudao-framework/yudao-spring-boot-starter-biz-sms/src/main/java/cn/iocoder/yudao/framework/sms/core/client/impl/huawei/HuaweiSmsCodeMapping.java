package cn.iocoder.yudao.framework.sms.core.client.impl.huawei;

import cn.iocoder.yudao.framework.common.exception.ErrorCode;
import cn.iocoder.yudao.framework.common.exception.enums.GlobalErrorCodeConstants;
import cn.iocoder.yudao.framework.sms.core.client.SmsCodeMapping;
import cn.iocoder.yudao.framework.sms.core.enums.SmsFrameworkErrorCodeConstants;

import java.util.Objects;

/**
 * TODO 删除翻译错误码模块
 *
 * @author 陈賝
 * @since 2023/7/28 18:08
 */
public class HuaweiSmsCodeMapping implements SmsCodeMapping {

    @Override
    public ErrorCode apply(String apiCode) {
        return Objects.equals(apiCode, "0") ? GlobalErrorCodeConstants.SUCCESS : SmsFrameworkErrorCodeConstants.SMS_UNKNOWN;
    }
}
