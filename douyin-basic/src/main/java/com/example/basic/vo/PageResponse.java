package com.example.basic.vo;


import com.example.basic.utils.PageResponseTransformer;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PageResponse<T> implements Serializable {
    private String traceId;
    private String code;
    private String msg;
    private T data;
    private Map<String, String> fieldError = new HashMap();

    public static <T> PageResponse<T> success(T data) {
        return PageResponseTransformer.buildResponseSuccess(data);
    }

    public static <T> PageResponse<T> fail(String msg) {
        return PageResponseTransformer.buildResponseNodata(msg);
    }

    public String getTraceId() {
        return this.traceId;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public Map<String, String> getFieldError() {
        return this.fieldError;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setFieldError(Map<String, String> fieldError) {
        this.fieldError = fieldError;
    }

    public PageResponse(String traceId, String code, String msg, T data, Map<String, String> fieldError) {
        this.traceId = traceId;
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.fieldError = fieldError;
    }

    public PageResponse() {
    }
}
