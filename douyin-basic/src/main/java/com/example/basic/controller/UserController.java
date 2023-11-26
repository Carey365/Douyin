package com.example.basic.controller;

import com.example.basic.entity.User;
import com.example.basic.eums.ResponsEums;
import com.example.basic.service.impl.UserServiceImpl;
import com.example.basic.utils.ControllerResponse;
import com.example.basic.utils.ServiceResponse;
import com.example.basic.vo.UserReqVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/douyin/user")
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/userinfo")
    public ControllerResponse<User> getUser(@RequestBody UserReqVO userReqVO){
        ControllerResponse<User> response=new ControllerResponse<>();
        ServiceResponse<User> serviceResponse=userService.getUser(userReqVO);
        if(ResponsEums.Success.getCode().equals(serviceResponse.getCode())){
            User user=serviceResponse.getData();
            response.success();
            response.setData(user);
            return response;
        }
        response.fail();
        return response;
    }
}
