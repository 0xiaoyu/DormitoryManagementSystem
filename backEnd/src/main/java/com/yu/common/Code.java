package com.yu.common;

/**
 * 通用返回状态码类
 */
public enum Code {
    SUCCESS(200,"成功"),
    ERROR(500,"失败"),
    NOT_FOUND(404,"未找到"),
    NOT_LOGIN(401,"未登录"),
    NOT_AUTH(403,"未授权"),
    NOT_ALLOWED(405,"不允许的请求方式"),
    PARAM_ERROR(400,"参数错误"),
    SERVER_ERROR(500,"服务器错误"),
    PASSWORD_ERROR(1001,"密码错误"),
    NOT_NULL(1002,"不能为空"),
    ;

    private final int code;
    private final String msg;

    Code(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Code get(int code){
        for (Code value : Code.values()) {
            if (value.code == code){
                return value;
            }
        }
        return null;
    }

    public int code() {
        return code;
    }

    public String msg() {
        return msg;
    }
}
