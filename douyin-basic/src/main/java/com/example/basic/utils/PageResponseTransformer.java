package com.example.basic.utils;

import com.example.basic.utils.response.ServiceResponse;
import com.example.basic.vo.PageResponse;

import java.util.Map;
import java.util.function.Function;

public class PageResponseTransformer {
    private static final String FAIL_CODE = "999999";
    private static final String SUCCESS_CODE = "000000";
    private static final String SUCCESS_MSG = "成功";

    public PageResponseTransformer() {
    }

    public static <T> PageResponse<T> buildResponse(String code, String msg, T data) {
        return buildResponse(code, msg, data, null);
    }

    public static <T> PageResponse<T> buildResponse(String code, String msg, T data, Map<String, String> filedError) {
        PageResponse<T> response = new PageResponse();
        response.setCode(code);
        response.setMsg(msg);
        response.setData(data);
        response.setFieldError(filedError);
        return response;
    }

    public static <T> PageResponse<T> buildResponseWithFiledError(String code, String msg, Map<String, String> fieldError) {
        return buildResponse(code, msg, null, fieldError);
    }

    public static <T> PageResponse<T> buildResponseNodata(String code, String msg) {
        return buildResponse(code, msg, null);
    }

    public static <T> PageResponse<T> buildResponseNodata(String msg) {
        return buildResponse("999999", msg, null);
    }

    public static <T> PageResponse<T> buildResponseSuccess(T data) {
        PageResponse<T> response = new PageResponse();
        response.setCode("000000");
        response.setMsg("成功");
        response.setData(data);
        return response;
    }

    public static <T> PageResponse<T> convert(ServiceResponse<T> serviceResponse) {
        return convert(serviceResponse, (t) -> {
            return t;
        });
    }

    public static <T, R> PageResponse<R> convert(ServiceResponse<T> serviceResponse, Function<T, R> function) {
        PageResponse<R> pageResponse = new PageResponse();
        pageResponse.setCode(serviceResponse.getCode());
        pageResponse.setMsg(serviceResponse.getMessage());
        pageResponse.setData(function.apply(serviceResponse.getData()));
        return pageResponse;
    }

    public static PageResponse convertCodeAndMsg(ServiceResponse serviceResponse) {
        PageResponse pageResponse = new PageResponse();
        pageResponse.setCode(serviceResponse.getCode());
        pageResponse.setMsg(serviceResponse.getMessage());
        return pageResponse;
    }
}
