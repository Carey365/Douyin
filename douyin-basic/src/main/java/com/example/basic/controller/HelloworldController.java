package com.example.basic.controller;

import com.example.basic.vo.HelloReqVO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author chenlianghao
 */
@RestController
@RequestMapping("/helloworld")
public class HelloworldController {
    @Value("${jdt.month:6}")
    private Integer expireMonth;
    @PostMapping("/hello")
    public String hello(@RequestBody HelloReqVO helloReqVO){
        String name=helloReqVO.getName();
        String message=helloReqVO.getMessage();
        String response="你好"+name+"，"+expireMonth+message;
        return response;
    }

}
