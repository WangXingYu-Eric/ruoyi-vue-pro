package cn.iocoder.yudao.module.system.dal.redis;

import cn.iocoder.yudao.framework.redis.core.RedisKeyDefine;
import cn.iocoder.yudao.module.system.dal.dataobject.oauth2.OAuth2AccessTokenDO;

import java.time.Duration;

import static cn.iocoder.yudao.framework.redis.core.RedisKeyDefine.KeyTypeEnum.STRING;

/**
 * System Redis Key 枚举类
 *
 * @author 芋道源码
 */
public interface RedisKeyConstants {

    RedisKeyDefine CAPTCHA_CODE = new RedisKeyDefine("验证码的缓存",
            "captcha_code:%s", // 参数为 uuid
            STRING, String.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine OAUTH2_ACCESS_TOKEN = new RedisKeyDefine("访问令牌的缓存",
            "oauth2_access_token:%s", // 参数为访问令牌 token
            STRING, OAuth2AccessTokenDO.class, RedisKeyDefine.TimeoutTypeEnum.DYNAMIC);

    RedisKeyDefine SOCIAL_AUTH_STATE = new RedisKeyDefine("社交登陆的 state", // 注意，它是被 JustAuth 的 justauth.type.prefix 使用到
            "social_auth_state:%s", // 参数为 state
            STRING, String.class, Duration.ofHours(24)); // 值为 state

    /**
     * 指定部门的所有子部门编号数组的缓存
     *
     * KEY 格式：dept_children_ids::{id}
     * 数据类型：String 子部门编号集合
     */
    String DEPT_CHILDREN_ID_LIST = "dept_children_ids";

    /**
     * 角色的缓存
     *
     * KEY 格式：role::{id}
     * 数据类型：String 角色编号
     */
    String ROLE = "role";

    /**
     * 用户拥有的角色编号的缓存
     *
     * KEY 格式：user_role_ids::{userId}
     * 数据类型：String 角色编号集合
     */
    String USER_ROLE_ID_LIST = "user_role_ids";

    /**
     * 拥有指定菜单的角色编号的缓存
     *
     * KEY 格式：user_role_ids::{menuId}
     * 数据类型：String 角色编号集合
     */
    String MENU_ROLE_ID_LIST = "menu_role_ids";

    /**
     * 拥有权限对应的菜单编号数组的缓存
     *
     * KEY 格式：permission_menu_ids::{permission}
     * 数据类型：String 菜单编号数组
     */
    String PERMISSION_MENU_ID_LIST = "permission_menu_ids";

}
