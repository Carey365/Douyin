package com.example.basic.controller;
import com.example.basic.entity.User;
import com.example.basic.eums.ResponsEums;
import com.example.basic.service.impl.UserServiceImpl;
import com.example.basic.utils.aop.log.PrintLog;
import com.example.basic.utils.response.ControllerResponse;
import com.example.basic.utils.aop.paramvalid.ParameterValidation;
import com.example.basic.utils.response.ServiceResponse;
import com.example.basic.vo.UserReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/douyin/user")
@Slf4j
public class UserController {

    @Autowired
    UserServiceImpl userService;

    @PostMapping("/userinfo")
    @ParameterValidation
    @PrintLog(methodName = "获取用户信息")
    public ControllerResponse<User> getUser(@RequestBody UserReqVO userReqVO){
        ControllerResponse<User> response=new ControllerResponse<>();
//        log.info("controller入参为{}", JSON.toJSON(userReqVO));
        userReqVO.setName(null);
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
