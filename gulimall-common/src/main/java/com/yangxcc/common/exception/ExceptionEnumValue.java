package com.yangxcc.common.exception;

public enum ExceptionEnumValue {
    VALID_EXCEPTION(400, "数据校验错误"),
    GENERAL_EXCEPTION(100, "未知错误");

    private int code;
    private String msg;

    ExceptionEnumValue(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
