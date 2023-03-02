package cn.iocoder.yudao.module.system.api.tenant.dto;

import lombok.Data;

/**
 * 多租户的数据源配置 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class TenantDataSourceConfigRespDTO {

    /**
     * 连接名
     */
    private String name;

    /**
     * 数据源连接
     */
    private String url;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

}