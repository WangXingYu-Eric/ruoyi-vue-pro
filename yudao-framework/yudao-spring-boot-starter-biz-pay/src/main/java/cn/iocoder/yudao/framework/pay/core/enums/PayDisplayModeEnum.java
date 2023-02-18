package cn.iocoder.yudao.framework.pay.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付 UI 展示模式
 *
 * @author 芋道源码
 */
@Getter
@AllArgsConstructor
public enum PayDisplayModeEnum {

    URL("url"), // Redirect 跳转链接的方式
    IFRAME("iframe"), // IFrame 内嵌链接的方式
    FORM("form"), // HTML 表单提交
    QR_CODE("qr_code"), // 二维码的文字内容
    QR_CODE_URL("qr_code_url"), // 二维码的图片链接
    ;

    /**
     * 展示模式
     */
    private final String mode;

}