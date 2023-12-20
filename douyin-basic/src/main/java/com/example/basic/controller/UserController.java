package com.example.basic.controller;
import com.example.basic.interceptor.JwtClaimsConstant;
import com.example.basic.interceptor.JwtProperties;
import com.example.basic.po.UserPO;
import com.example.basic.eums.ResponsEums;
import com.example.basic.service.impl.UserServiceImpl;
import com.example.basic.utils.JwtUtil;
import com.example.basic.utils.aop.log.PrintLog;
import com.example.basic.utils.response.ControllerResponse;
import com.example.basic.utils.aop.paramvalid.ParameterValidation;
import com.example.basic.utils.response.ServiceResponse;
import com.example.basic.vo.UserLoginReqVO;
import com.example.basic.vo.UserLoginRespVO;
import com.example.basic.vo.UserReqVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/douyin/user")
@Slf4j
public class UserController {

    @Autowired
    UserServiceImpl userService;
    @Autowired
    private JwtProperties jwtProperties;

    @PostMapping("/userinfo")
    @ParameterValidation
    @PrintLog(methodName = "获取用户信息")
    public ControllerResponse<UserPO> getUser(@RequestBody UserReqVO userReqVO){
        ControllerResponse<UserPO> response=new ControllerResponse<>();
//        log.info("controller入参为{}", JSON.toJSON(userReqVO));
        userReqVO.setName(null);
        ServiceResponse<UserPO> serviceResponse=userService.getUser(userReqVO);
        if(ResponsEums.Success.getCode().equals(serviceResponse.getCode())){
            UserPO user=serviceResponse.getData();
            response.success();
            response.setData(user);
            return response;
        }
        response.fail();
        return response;
    }
    /**
     * 微信登录
     * @param userLoginReqVO 入参
     * @return 返参
     */
    @PostMapping("/login")
    public ControllerResponse<UserLoginRespVO> login(@RequestBody UserLoginReqVO userLoginReqVO){
        log.info("用户登录：{}",userLoginReqVO.getUserName());

        //微信登录
        UserPO user = userService.Login(userLoginReqVO);

        //为微信用户生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.EMP_ID,user.getUserId());
        String token = JwtUtil.createJWT(jwtProperties.getAdminSecretKey(), jwtProperties.getAdminTtl(), claims);

        UserLoginRespVO userLoginVO = new UserLoginRespVO();
        userLoginVO.setId(user.getUserId());
        userLoginVO.setToken(token);
        ControllerResponse<UserLoginRespVO> response=new ControllerResponse<>();
        response.success();
        response.setData(userLoginVO);
        return response;
    }
}
