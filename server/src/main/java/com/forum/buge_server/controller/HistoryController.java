package com.forum.buge_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.buge_server.entity.Article;
import com.forum.buge_server.entity.History;
import com.forum.buge_server.entity.User;
import com.forum.buge_server.config.JwtConfig;
import com.forum.buge_server.service.ArticleService;
import com.forum.buge_server.service.BlockService;
import com.forum.buge_server.service.HistoryService;
import com.forum.buge_server.service.UserService;
import com.forum.buge_server.config.JsonResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020-05-16
 **/
@RestController
@RequestMapping("/api")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    BlockService blockService;

    /**
     * 分页查询历史表获取文章内容
     * 
     * @param current
     * @param size
     * @param request
     * @return
     */
    @PostMapping("/select/history")
    public Object historyPage(Integer current, Integer size, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        // 分页条件
        IPage<History> page = new Page<>(current, size);
        // 查询条件
        LambdaQueryWrapper<History> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(History::getUid, Integer.valueOf(subject));
        wrapper.orderByDesc(History::getTime);
        IPage<History> historyPageing = historyService.getHistoryPageing(page, wrapper);
        List<History> records = historyPageing.getRecords();
        List<Article> list = new ArrayList();
        for (History history : records) {
            Article article = articleService.getById(history.getAid());
            if (article != null) {
                article.setUser(userService.getById(article.getUId()));
                article.setContent("");
                article.setBlock(blockService.getById(article.getBId()));
                article.setUser(userService.getById(article.getUId()));
                list.add(article);
            }
        }
        if (list.size() > 0) {
            return new JsonResult(200, "查询成功", list);
        } else {
            return new JsonResult(501, "无数据");
        }
    }
}
