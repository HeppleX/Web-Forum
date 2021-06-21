package com.forum.buge_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.forum.buge_server.entity.Article;
import com.forum.buge_server.mapper.ArticleMapper;
import com.forum.buge_server.service.ArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author
 * @since 2020-05-16
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public IPage<Article> getArticlePageing(IPage<Article> iPage, LambdaQueryWrapper<Article> wrapper) {
        IPage<Article> articleIPage = articleMapper.selectPage(iPage, wrapper);
        return articleIPage;
    }
}
