package cn.iocoder.yudao.framework.sms.core.client.impl.huawei;

import cn.hutool.core.lang.Assert;
import cn.hutool.http.HttpRequest;
import cn.iocoder.yudao.framework.common.core.KeyValue;
import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import cn.iocoder.yudao.framework.sms.core.client.SmsCommonResult;
import cn.iocoder.yudao.framework.sms.core.client.dto.SmsReceiveRespDTO;
import cn.iocoder.yudao.framework.sms.core.client.dto.SmsSendRespDTO;
import cn.iocoder.yudao.framework.sms.core.client.dto.SmsTemplateRespDTO;
import cn.iocoder.yudao.framework.sms.core.client.impl.AbstractSmsClient;
import cn.iocoder.yudao.framework.sms.core.client.impl.huawei.entity.HuaweiResponse;
import cn.iocoder.yudao.framework.sms.core.property.SmsChannelProperties;
import lombok.extern.slf4j.Slf4j;

import java.util.List;


/**
 * 华为短信客户端的实现类
 *
 * @author 陈賝
 * @since 2023/7/28 18:04
 */
@Slf4j
public class HuaweiSmsClient extends AbstractSmsClient {

    public HuaweiSmsClient(SmsChannelProperties properties) {
        super(properties, null);
        // TODO 特殊字段 后期加入数据库
        properties.setSender("8822041331985");
        // 移除错误码
        Assert.notEmpty(properties.getApiKey(), "apiKey 不能为空");
        Assert.notEmpty(properties.getApiSecret(), "apiSecret 不能为空");
        Assert.notEmpty(properties.getSender(), "sender 不能为空");
    }

    /**
     * 自定义初始化
     */
    @Override
    protected void doInit() {

    }

    /**
     * TODO 请求构建、字符串格式化待封装
     *
     * @author 陈賝
     * @since 2023/8/7 19:32
     */
    @Override
    protected SmsCommonResult<SmsSendRespDTO> doSendSms(Long logId, String mobile,
                                                        String apiTemplateId, List<KeyValue<String, Object>> templateParams) throws Throwable {

        //必填,请参考"开发准备"获取如下数据,替换为实际值
        String url = "https://smsapi.cn-north-4.myhuaweicloud.com:443/sms/batchSendSms/v1"; //APP接入地址(在控制台"应用管理"页面获取)+接口访问URI
        String appKey = properties.getApiKey(); //APP_Key
        String appSecret = properties.getApiSecret(); //APP_Secret
        String sender = "8822041331985"; //国内短信签名通道号或国际/港澳台短信通道号
        String templateId = "e1e72249e82d4949bfd58833deebff94"; //模板ID

        //条件必填,国内短信关注,当templateId指定的模板类型为通用模板时生效且必填,必须是已审核通过的,与模板类型一致的签名名称
        //国际/港澳台短信不用关注该参数
        String signature = properties.getSignature(); //签名名称

        //必填,全局号码格式(包含国家码),示例:+8615123456789,多个号码之间用英文逗号分隔
        String receiver = "+86" + mobile; //短信接收人号码

        //选填,短信状态报告接收地址,推荐使用域名,为空或者不填表示不接收状态报告
        String statusCallBack = "";

        //模板变量，此处以单变量验证码短信为例，请客户自行生成6位验证码，并定义为字符串类型，以杜绝首位0丢失的问题（例如：002569变成了2569）。
        String templateParas = String.format("[\"%s\"]", templateParams.get(0).getValue().toString());
        //请求Body,不携带签名名称时,signature请填null
        String body = HuaweiUtils.buildRequestBody(sender, receiver, templateId, templateParas, statusCallBack, signature);
        if (null == body || body.isEmpty()) {
            throw new IllegalArgumentException("body is null.");
        }

        //请求Headers中的X-WSSE参数值
        String wsseHeader = HuaweiUtils.buildWsseHeader(appKey, appSecret);
        if (null == wsseHeader || wsseHeader.isEmpty()) {
            throw new IllegalArgumentException("wsse header is null.");
        }

        String authorization = HttpRequest.post(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Authorization", HuaweiUtils.AUTH_HEADER_VALUE)
                .header("X-WSSE", wsseHeader)
                .body(body).execute().body();
        HuaweiResponse huaweiResponse = JsonUtils.parseObject(authorization, HuaweiResponse.class);
        return SmsCommonResult.build(huaweiResponse.getCode(), huaweiResponse.getDescription(), huaweiResponse.getResult().get(0).getSmsMsgId(), null, codeMapping);
    }

    /**
     * TODO 解析
     *
     * @param text
     * @return java.util.List<cn.iocoder.yudao.framework.sms.core.client.dto.SmsReceiveRespDTO>
     * @author 陈賝
     * @since 2023/8/7 17:46
     */
    @Override
    protected List<SmsReceiveRespDTO> doParseSmsReceiveStatus(String text) {
        return null;
    }

    /**
     * TODO 检验模板是否存在
     *
     * @param apiTemplateId
     * @return cn.iocoder.yudao.framework.sms.core.client.SmsCommonResult<cn.iocoder.yudao.framework.sms.core.client.dto.SmsTemplateRespDTO>
     * @author 陈賝
     * @since 2023/8/7 17:39
     */
    @Override
    protected SmsCommonResult<SmsTemplateRespDTO> doGetSmsTemplate(String apiTemplateId) {
//        String url = "/v2/{project_id}/msgsms/templates/{id}";
//        String authorization = HttpRequest.post(url)
//                .header("Content-Type", "application/x-www-form-urlencoded")
//                .header("Authorization", AUTH_HEADER_VALUE)
//                .header("X-WSSE", wsseHeader)
//                .body(body).execute().body();
        SmsTemplateRespDTO data = new SmsTemplateRespDTO();
        return SmsCommonResult.build("0", "", "", data, codeMapping);
    }


}
