package com.forum.buge_server.dto;

import lombok.Data;

@Data
public class UserToken {
    // 用户头像
    private String head;
    // 用户昵称
    private String nickname;
    // 用户简介
    private String uIntroduce;
    // 用户token
    private Object token;
}
