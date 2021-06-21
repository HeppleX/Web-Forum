package com.forum.buge_server.dto;

import lombok.Data;

/**
 * @author
 * @date 2020-05-16
 **/
@Data
public class Enter {
    // 是否点赞
    private Boolean like = false;
    // 是否收藏
    private Boolean collection = false;
    // 是否关注
    private Boolean follow = false;
}
