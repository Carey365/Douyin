package com.example.basic.eums;


/**
 * 响应枚举类
 * @author carey
 */
public enum ResponsEums {
    Success("0000","成功"),
    Faliure("1111","失败");

    private final String code;
    private final String msg;
    ResponsEums(String code,String msg){
        this.code=code;
        this.msg=msg;
    }
    public String getCode(){
        return this.code;
    }
    public String getMsg(){
        return this.msg;
    }
}
