package cn.iocoder.yudao.framework.sms.core.client.impl.huawei;

import lombok.extern.slf4j.Slf4j;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Slf4j
public class HuaweiUtils {

    //无需修改,用于格式化鉴权头域,给"X-WSSE"参数赋值
    public static final String WSSE_HEADER_FORMAT = "UsernameToken Username=\"%s\",PasswordDigest=\"%s\",Nonce=\"%s\",Created=\"%s\"";
    //无需修改,用于格式化鉴权头域,给"Authorization"参数赋值
    public static final String AUTH_HEADER_VALUE = "WSSE realm=\"SDP\",profile=\"UsernameToken\",type=\"Appkey\"";


    /**
     * 构造X-WSSE参数值
     *
     * @param appKey
     * @param appSecret
     * @return
     */
    public static String buildWsseHeader(String appKey, String appSecret) {
        if (null == appKey || null == appSecret || appKey.isEmpty() || appSecret.isEmpty()) {
            log.info("buildWsseHeader(): appKey or appSecret is null.");
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        String time = sdf.format(new Date());
        String nonce = UUID.randomUUID().toString().replace("-", "");

        MessageDigest md;
        byte[] passwordDigest = null;

        try {
            md = MessageDigest.getInstance("SHA-256");
            md.update((nonce + time + appSecret).getBytes());
            passwordDigest = md.digest();
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalArgumentException("短信编码错误");
        }

        //如果JDK版本是1.8,请加载原生Base64类,并使用如下代码
        String passwordDigestBase64Str = Base64.getEncoder().encodeToString(passwordDigest); //PasswordDigest
        //如果JDK版本低于1.8,请加载三方库提供Base64类,并使用如下代码
        //String passwordDigestBase64Str = Base64.encodeBase64String(passwordDigest); //PasswordDigest
        //若passwordDigestBase64Str中包含换行符,请执行如下代码进行修正
        //passwordDigestBase64Str = passwordDigestBase64Str.replaceAll("[\\s*\t\n\r]", "");
        return String.format(WSSE_HEADER_FORMAT, appKey, passwordDigestBase64Str, nonce, time);
    }

    /**
     * 构造请求Body体
     *
     * @param sender         国内短信签名通道号
     * @param receiver       短信接收者
     * @param templateId     短信模板id
     * @param templateParas  模板参数
     * @param statusCallBack 短信状态报告接收地
     * @param signature      签名名称,使用国内短信通用模板时填写
     * @return java.lang.String 请求Body体
     * @author 陈賝
     * @since 2023/8/7 16:58
     */
    public static String buildRequestBody(String sender, String receiver, String templateId, String templateParas,
                                          String statusCallBack, String signature) {
        if (null == sender || null == receiver || null == templateId || sender.isEmpty() || receiver.isEmpty()
                || templateId.isEmpty()) {
            log.info("buildRequestBody(): sender, receiver or templateId is null.");
            return null;
        }

        Map<String, String> map = new HashMap<String, String>();

        map.put("from", sender);
        map.put("to", receiver);
        map.put("templateId", templateId);
        if (null != templateParas && !templateParas.isEmpty()) {
            map.put("templateParas", templateParas);
        }
        if (null != statusCallBack && !statusCallBack.isEmpty()) {
            map.put("statusCallback", statusCallBack);
        }
        if (null != signature && !signature.isEmpty()) {
            map.put("signature", signature);
        }

        StringBuilder sb = new StringBuilder();
        String temp = "";

        for (String s : map.keySet()) {
            try {
                temp = URLEncoder.encode(map.get(s), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                throw new IllegalArgumentException("短信编码错误");
            }
            sb.append(s).append("=").append(temp).append("&");
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

}
