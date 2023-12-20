package com.example.basic.interceptor;

import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author chenlianghao5
 * 身份验证拦截器
 */
@Component
@Slf4j
public class IdentityInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            Long userId = BaseContext.getCurrentId();
            log.info("身份拦截器拦截用户:{}", userId);
            HandlerMethod ignoreAnnotation = (HandlerMethod) handler;
            IgnoreIdentity ignoreIdentity = ignoreAnnotation.getMethod().getAnnotation(IgnoreIdentity.class);
            if (null != ignoreIdentity) {
                //忽略拦截用户
                if (ignoreIdentity.user()) {
                    log.info("身份拦截器拦截用户合作方:{}", userId);
                    if (ObjectUtils.isEmpty(userId)) {
                        throw new Exception("用户非法");
                    }
                    return true;
                }
                return true;
            }
            if (ObjectUtils.isEmpty(userId)) {
                throw new Exception("用户非法");
            }
            return true;
        } catch (Exception e) {
            log.error("身份拦截器异常", e);
        }
        return false;
    }

}
