package com.Haven.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusCodeEnum {
    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 没有操作权限
     */
    AUTHORIZED(403, "没有操作权限"),
    /**
     * 没有操作权限
     */
    CODE_NO_EQUAL(523, "验证码错误"),
    /**
     * 系统异常
     */
    SYSTEM_ERROR(500, "系统异常"),
    /**
     * 失败
     */
    FAIL(510, "操作失败"),
    /**
     * 参数校验失败
     */
    VALID_ERROR(520, "参数格式不正确"),
    /**
     * 用户名已存在
     */
    USERNAME_EXIST(521, "用户名已存在"),
    /**
     * 用户名不存在
     */
    USERNAME_NOT_EXIST(522, "用户名不存在"),
    /**
     * qq登录错误
     */
    QQ_LOGIN_ERROR(531, "qq登录错误"),
    /**
     * 微博登录错误
     */
    WEIBO_LOGIN_ERROR(532, "微博登录错误");

    /**
     * 状态码
     */
    private final Integer code;

    /**
     * 描述
     */
    private final String describe;
}
