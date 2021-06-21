package com.forum.buge_server.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.forum.buge_server.dto.UserToken;
import com.forum.buge_server.entity.User;
import com.forum.buge_server.config.JwtConfig;
import com.forum.buge_server.service.UserService;
import com.forum.buge_server.config.JsonResult;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    UserService userService;

    /**
     * 用户登录接口
     * 
     * @param email
     * @param password
     * @return
     */
    @ApiOperation("用户登陆接口")
    @PostMapping("/login")
    public Object login(String email, String password, HttpServletRequest request) {
        System.out.println(request.getHeader("token"));
        JSONObject json = new JSONObject();

        User user = userService.getEmail(email);
        if (user == null) {
            return new JsonResult(403, "无此用户");
        }

        if (!user.getPassword().equals(password)) {
            return new JsonResult(403, "密码不正确");
        }
        String userId = user.getUId() + "";

        String token = jwtConfig.createToken(userId);

        if (!StringUtils.isEmpty(token)) {
            json.put("token", token);
        }
        UserToken userToken = new UserToken();
        userToken.setHead(user.getHead());
        userToken.setNickname(user.getNickname());
        userToken.setToken(json.get("token"));
        return new JsonResult(200, "登陆成功", userToken);
    }

    @ApiOperation("用户注册接口")
    @PostMapping("/reg")
    public Object regUser(String nickname, String email, String password, String password2) {
        if (nickname == null || email == null || password == null || password2 == null || nickname == "" || email == ""
                || password == "" || password2 == "") {
            return new JsonResult(403, "字段为空");
        }
        User user = userService.getEmail(email);
        if (user != null) {
            return new JsonResult(403, "该用户已存在");
        }
        if (!password.equals(password2)) {
            return new JsonResult(403, "密码不一致");
        }
        boolean common = userService.save(new User().setEmail(email).setPassword(password).setRole("common")
                .setNickname(nickname).setScore(0).setCreateDate(new Date()).setUpdateDate(new Date()));
        return common ? new JsonResult(200, "注册成功") : new JsonResult(403, "注册失败");
    }

    /**
     * 测试的一个controller
     * 
     * @param request
     * @return
     */
    @GetMapping("/hello")
    public String hello(HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        System.out.println(token);
        String subject = token.getSubject();
        System.out.println(subject);
        return "hello";
    }

}
