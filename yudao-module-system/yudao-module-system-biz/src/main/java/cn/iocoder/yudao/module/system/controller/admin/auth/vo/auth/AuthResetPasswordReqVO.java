package cn.iocoder.yudao.module.system.controller.admin.auth.vo.auth;

import cn.iocoder.yudao.framework.common.validation.Mobile;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author kemengkai
 * @create 2022-05-19 09:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResetPasswordReqVO {
    @ApiModelProperty(value = "新密码", required = true, example = "buzhidao")
    @NotEmpty(message = "新密码不能为空")
    @Length(min = 4, max = 16, message = "密码长度为 4-16 位")
    private String password;

    @ApiModelProperty(value = "手机验证码", required = true, example = "1024")
    @NotEmpty(message = "手机验证码不能为空")
    @Length(min = 4, max = 6, message = "手机验证码长度为 4-6 位")
    @Pattern(regexp = "^[0-9]+$", message = "手机验证码必须都是数字")
    private String code;

    @ApiModelProperty(value = "手机号",required = true,example = "15878962356")
    @NotBlank(message = "手机号不能为空")
    @Mobile
    private String mobile;
}
