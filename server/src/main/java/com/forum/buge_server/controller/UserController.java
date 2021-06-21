package com.forum.buge_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.forum.buge_server.entity.Article;
import com.forum.buge_server.entity.Collection;
import com.forum.buge_server.entity.Follow;
import com.forum.buge_server.entity.User;
import com.forum.buge_server.config.JwtConfig;
import com.forum.buge_server.service.*;
import com.forum.buge_server.config.JsonResult;
import io.jsonwebtoken.Claims;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author
 * @date 2020-05-16
 **/
@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    ArticleService articleService;

    @Autowired
    UserService userService;

    @Autowired
    BlockService blockService;

    @Autowired
    FollowService followService;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    CollectionService collectionService;

    /**
     * 获取自己的所有文章
     * 
     * @return
     */
    @GetMapping("/select/article/user/me")
    public Object getArticleMeAll(HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getUId, Integer.valueOf(subject));
        List<Article> list = articleService.list(articleWrapper);
        for (Article article : list) {
            article.setContent("");
            article.setUser(userService.getById(article.getUId()));
            article.setBlock(blockService.getById(article.getBId()));
        }
        return new JsonResult(200, "", list);
    }

    /**
     * 获取自己收藏的文章
     * 
     * @return
     */
    @GetMapping("/select/article/collection/all")
    public Object getCollectionMeAll(HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        LambdaQueryWrapper<Collection> collectionWrapper = new LambdaQueryWrapper<>();
        collectionWrapper.eq(Collection::getUid, Integer.valueOf(subject));
        List<Collection> list = collectionService.list(collectionWrapper);
        List listArticle = new ArrayList();
        for (Collection collection : list) {
            Article byId = articleService.getById(collection.getAId());
            if (byId != null) {
                byId.setUser(userService.getById(byId.getUId()));
                byId.setBlock(blockService.getById(byId.getBId()));
                listArticle.add(byId);
            }
        }
        Collections.reverse(listArticle);
        return new JsonResult(200, "", listArticle);
    }

    /**
     * 获取个人信息
     * 
     * @return
     */
    @GetMapping("/get/user/info")
    public Object getUserInfo(HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        User byId = userService.getById(Integer.valueOf(subject));
        return new JsonResult(200, "", byId);
    }

    /**
     * 获取关注的用户数据
     * 
     * @return
     */
    @GetMapping("/get/follow/all")
    public Object getFollowUser(HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        LambdaQueryWrapper<Follow> followWrapper = new LambdaQueryWrapper<>();
        followWrapper.eq(Follow::getUid, Integer.valueOf(subject));
        List<Follow> list = followService.list(followWrapper);
        List userList = new ArrayList();
        for (Follow follow : list) {
            User byId = userService.getById(follow.getByUid());
            if (byId != null) {
                userList.add(byId);
            }
        }
        Collections.reverse(userList);
        return new JsonResult(200, "", userList);
    }

    /**
     * 更新用户信息
     * 
     * @return
     */
    @PostMapping("/update/user/info")
    public Object updateUser(User user, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        user.setUId(Integer.valueOf(subject));
        boolean b = userService.updateById(user);
        User byId = userService.getById(user.getUId());
        return new JsonResult(200, "更新成功!", byId);

    }
}
