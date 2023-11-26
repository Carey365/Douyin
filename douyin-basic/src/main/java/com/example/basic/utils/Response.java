package com.example.basic.utils;

import com.example.basic.eums.ResponsEums;
import lombok.Data;

/**
 * @author carey
 */
@Data
public class Response {
    public String code;
    public String message;
    public void fail(){
        this.code=ResponsEums.Faliure.getCode();
        this.message=ResponsEums.Faliure.getMsg();
    }
    public void success(){
        this.code=ResponsEums.Success.getCode();
        this.message=ResponsEums.Success.getMsg();
    }

}
