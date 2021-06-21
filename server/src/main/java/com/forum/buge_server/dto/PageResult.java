package com.forum.buge_server.dto;

import lombok.Data;

import java.util.List;

@Data
public class PageResult {
    // 当前页数
    private Long current;

    // 当前页数的数据
    private List list;
}
