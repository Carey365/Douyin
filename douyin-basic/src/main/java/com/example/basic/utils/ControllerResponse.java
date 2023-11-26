package com.example.basic.utils;

import com.example.basic.eums.ResponsEums;
import lombok.Data;
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
