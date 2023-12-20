package com.example.basic.interceptor;

import com.example.basic.eums.ResponsEums;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.util.Strings;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * HttpServletRequest 通用类
 */
@Slf4j
public class HttpCommon {
    public static void writeLoginResponse(HttpServletResponse response, ResponsEums code, String remark) {
        if (code == null) {
            return;
        }
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        try (PrintWriter writer = response.getWriter()) {
            String resp;
            if (Strings.isNullOrEmpty(remark)) {
                resp = "{\"code\":\"" + code.getCode() + "\",\"msg\":\"" + code.getMsg() + "\"}";
            } else {
                resp = "{\"code\":\"" + code.getCode() + "\",\"msg\":\"" + remark + "\"}";
            }
            writer.write(resp);
            writer.flush();
        } catch (IOException e) {
            log.error("--ajaxResponse error--", e);
        }
    }

    public static void writeBizResponse(HttpServletResponse response, ResponsEums code) {
        if (code == null) {
            return;
        }
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        try (PrintWriter writer = response.getWriter()) {
            writer.write("{\"code\":\"" + code.getCode() + "\",\"msg\":\"" + code.getMsg() + "\"}");
            writer.flush();
        } catch (IOException e) {
            log.error("--ajaxResponse error--", e);
        }
    }

}
