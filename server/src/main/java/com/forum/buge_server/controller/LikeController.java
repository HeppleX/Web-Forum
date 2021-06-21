package com.forum.buge_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.forum.buge_server.entity.Article;
import com.forum.buge_server.entity.Collection;
import com.forum.buge_server.entity.Follow;
import com.forum.buge_server.entity.Likes;
import com.forum.buge_server.config.JwtConfig;
import com.forum.buge_server.service.ArticleService;
import com.forum.buge_server.service.CollectionService;
import com.forum.buge_server.service.FollowService;
import com.forum.buge_server.service.LikeService;
import com.forum.buge_server.config.JsonResult;
import io.jsonwebtoken.Claims;
//import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author
 * @date 2020-05-16
 **/
@RestController
@RequestMapping("/api")
public class LikeController {

    @Autowired
    LikeService likeService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    FollowService followService;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    ArticleService articleService;

    /**
     * 先查询是否存在记录，如果存在，则执行取消点赞，如果不存在则执行点赞操作
     * 
     * @param aid
     * @return
     */
    @PostMapping("/add/like")
    public Object setLike(Integer aid, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        LambdaQueryWrapper<Likes> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Likes::getUid, Integer.valueOf(subject));
        queryWrapper.eq(Likes::getAId, aid);
        Likes one = likeService.getOne(queryWrapper);
        if (one == null) {
            boolean save = likeService.save(new Likes().setAId(aid).setUid(Integer.valueOf(subject)));
            return new JsonResult(200, "点赞成功!");
        } else {
            boolean b = likeService.removeById(one.getId());
            return new JsonResult(200, "已取消点赞!");
        }

    }

    /**
     * 先查询是否存在记录，如果存在，则执行取消收藏，如果不存在则执行收藏操作
     * 
     * @param aid
     * @param request
     * @return
     */
    @PostMapping("/add/collection")
    public Object setCollection(Integer aid, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        LambdaQueryWrapper<Collection> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Collection::getUid, Integer.valueOf(subject));
        queryWrapper.eq(Collection::getAId, aid);
        Collection one = collectionService.getOne(queryWrapper);

        if (one == null) {
            boolean save = collectionService.save(new Collection().setAId(aid).setUid(Integer.valueOf(subject)));
            return new JsonResult(200, "收藏成功!");
        } else {
            boolean b = collectionService.removeById(one.getId());
            return new JsonResult(200, "已取消收藏!");
        }
    }

    /**
     * 先查询是否存在记录，如果存在，则执行取消关注，如果不存在则执行关注操作
     * 
     * @param aid
     * @param request
     * @return
     */
    @PostMapping("/add/follow")
    public Object setFollow(Integer aid, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        Article byId = articleService.getById(aid);
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getUid, Integer.valueOf(subject));
        queryWrapper.eq(Follow::getByUid, byId.getUId());

        Follow one = followService.getOne(queryWrapper);
        Article byId1 = articleService.getById(aid);
        Integer integer = Integer.valueOf(subject);
        if (byId1.getUId() == integer) {
            return new JsonResult(201, "无法关注自己");
        }
        if (one == null) {
            followService.save(new Follow().setUid(Integer.valueOf(subject)).setByUid(byId.getUId()));
            return new JsonResult(200, "关注成功!");
        } else {
            followService.removeById(one.getId());
            return new JsonResult(200, "已取消关注!");
        }
    }
}
