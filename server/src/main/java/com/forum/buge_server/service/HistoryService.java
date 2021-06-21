package com.forum.buge_server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.forum.buge_server.entity.Article;
import com.forum.buge_server.entity.History;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 * @since 2020-05-16
 */
public interface HistoryService extends IService<History> {

    /**
     * 分页历史表
     * 
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<History> getHistoryPageing(IPage<History> iPage, LambdaQueryWrapper<History> wrapper);
}
