package com.forum.buge_server.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.forum.buge_server.entity.Article;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 * @since 2020-05-16
 */
public interface ArticleService extends IService<Article> {

    /**
     * 分页查询文章
     * 
     * @param iPage
     * @param wrapper
     * @return
     */
    IPage<Article> getArticlePageing(IPage<Article> iPage, LambdaQueryWrapper<Article> wrapper);

}
