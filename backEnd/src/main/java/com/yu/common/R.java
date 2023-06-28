package com.yu.common;

/**
 * 通用返回类
 *
 * @param code 200成功 500失败
 * @param data 返回的数据
 * @param msg  返回的信息
 */
public record R(Integer code, Object data, String msg) {
    public static R ok(Object data) {
        return new R(Code.SUCCESS.code(), data, "");
    }

    public static R ok() {
        return new R(Code.SUCCESS.code(), null, "");
    }
    public static R ok(Code code){
        return new R(code.code(),null,code.msg());
    }

    public static R error() {
        return new R(Code.ERROR.code(), null, "");
    }

    public static R error(Code code) {
        return new R(code.code(), null, code.msg());
    }

}
