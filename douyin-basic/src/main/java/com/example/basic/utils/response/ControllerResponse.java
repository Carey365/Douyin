package com.example.basic.utils.response;

import com.example.basic.utils.response.Response;
import lombok.Getter;
import lombok.Setter;

/**
 * @author carey
 */
@Setter
@Getter
public class ControllerResponse<T> extends Response {
    private T data;
}
