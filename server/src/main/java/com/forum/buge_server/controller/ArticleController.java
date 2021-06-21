package com.forum.buge_server.controller;

import com.forum.buge_server.entity.Article;
import com.forum.buge_server.entity.Block;
import com.forum.buge_server.config.JwtConfig;
import com.forum.buge_server.service.ArticleService;
import com.forum.buge_server.service.BlockService;
import com.forum.buge_server.service.UserService;
import com.forum.buge_server.config.JsonResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author
 * @since 2020-05-16
 */

@RestController
@RequestMapping("/api")
public class ArticleController {

    @Autowired
    UserService userService;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    ArticleService articleService;

    @PostMapping("/save/article")
    public Object saveBlock(String title, String content, Integer bid, HttpServletRequest request) {

        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        if (title == null || title == "" || content == null || content == "") {
            return new JsonResult(403, "字段为空");
        }

        String subject = token.getSubject();
        Article article = new Article();
        if (bid == null) {
            article.setBId(0);
        } else {
            article.setBId(bid);
        }
        article.setTitle(title);
        article.setUId(Integer.valueOf(subject));
        article.setContent(content);
        article.setCreateDate(new Date());
        article.setUpdateDate(new Date());
        article.setScore(0);
        boolean save = articleService.save(article);
        return save ? new JsonResult(200, "添加成功") : new JsonResult(403, "添加失败");
    }
}
