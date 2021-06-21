package com.forum.buge_server.config;

import com.alibaba.druid.util.StringUtils;
import com.forum.buge_server.config.JwtConfig;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.SignatureException;

@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    JwtConfig jwtConfig = new JwtConfig();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        /** 地址过滤 */
        String uri = request.getRequestURI();
        if (uri.contains("/api/login") || uri.contains("/api/reg") || uri.contains("/sys/login")
                || uri.contains("/api/get")) {
            return true;
        }

        /** Token 验证是否存在 */
        String token = request.getHeader(jwtConfig.getHeader());

        if (StringUtils.isEmpty(token)) {
            token = request.getParameter(jwtConfig.getHeader());
        }
        if (StringUtils.isEmpty(token)) {
            throw new SignatureException(jwtConfig.getHeader() + "不能为空");
        }

        Claims claims = null;
        // 判断是否有效
        try {
            claims = jwtConfig.getTokenClaim(token);
            if (claims == null || jwtConfig.isTokenExpired(claims.getExpiration())) {
                throw new SignatureException(jwtConfig.getHeader() + "失效，请重新登录。");
            }
        } catch (Exception e) {
            throw new SignatureException(jwtConfig.getHeader() + "失效，请重新登录。");
        }

        /** 设置 identityId 用户身份ID */
        request.setAttribute("identityId", claims.getSubject());
        return true;

    }
}
