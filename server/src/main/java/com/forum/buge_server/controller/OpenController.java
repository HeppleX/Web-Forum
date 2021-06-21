package com.forum.buge_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.forum.buge_server.dto.Enter;
import com.forum.buge_server.dto.PageResult;
import com.forum.buge_server.entity.*;
import com.forum.buge_server.config.JwtConfig;
import com.forum.buge_server.service.*;
import com.forum.buge_server.config.JsonResult;
import io.jsonwebtoken.Claims;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/open")
public class OpenController {

    @Autowired
    BlockService blockService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleService articleService;

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    LikeService likeService;

    @Autowired
    CollectionService collectionService;

    @Autowired
    FollowService followService;

    @Autowired
    HistoryService historyService;

    /**
     * 获取所有版块，根据分值排序
     * 
     * @return
     */
    @GetMapping("/block")
    public Object SelectBlockAll() {
        LambdaQueryWrapper<Block> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderBy(true, false, Block::getBScore);
        List<Block> list = blockService.list(queryWrapper);
        return new JsonResult(200, "查询成功", list);
    }

    /**
     * 根据版块id获取版块信息
     * 
     * @param id
     * @return
     */
    @GetMapping("/select/block/{id}")
    public Object getBlockById(@PathVariable("id") Integer id) {
        Block byId = blockService.getById(id);
        return new JsonResult(200, "", byId);
    }

    /**
     * 根据分值前三位作者
     * 
     * @return
     */
    @GetMapping("/select/user/score")
    public Object getUserThereScore() {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderBy(true, false, User::getScore);
        queryWrapper.last("limit 3");
        List<User> list = userService.list(queryWrapper);
        for (User user : list) {
            user.setPassword("");
        }
        return new JsonResult(200, "查询成功", list);
    }

    /**
     * 根据热度获取前三个版块
     * 
     * @return
     */
    @GetMapping("/select/block/score")
    public Object getBlockThereScore() {
        LambdaQueryWrapper<Block> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.orderBy(true, false, Block::getBScore);
        queryWrapper.last("limit 3");
        List<Block> list = blockService.list(queryWrapper);
        return new JsonResult(200, "", list);
    }

    /**
     * 根据文章主键查询文章，点击文章加入history表
     * 
     * @param id
     * @return
     */
    @GetMapping("/article/{id}")
    public Object getArticleById(@PathVariable("id") Integer id, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        // 在登陆状态下保存到历史表中
        if (token != null) {
            String subject = token.getSubject();
            LambdaQueryWrapper<History> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(History::getAid, id);
            queryWrapper.eq(History::getUid, Integer.valueOf(subject));
            History one = historyService.getOne(queryWrapper);
            // 如果已存在记录，则更新时间
            History history = new History().setAid(id).setTime(new Date()).setUid(Integer.valueOf(subject));
            if (one == null) {
                historyService.save(history);
            } else {
                one.setTime(new Date());
                historyService.updateById(one);
            }
        }

        Article byId = articleService.getById(id);
        byId.setUser(userService.getById(byId.getUId()));
        byId.setBlock(blockService.getById(byId.getBId()));

        // 每次访问热度加1
        articleService.updateById(new Article().setId(id).setScore(byId.getScore() + 1));

        return new JsonResult(200, "查询成功", byId);
    }

    /**
     * 根据文章的发布时间查询
     * 
     * @return
     */
    @ApiOperation("分页查询文章，时间排序")
    @PostMapping("/article/new")
    public Object getArticleNew(Integer current, Integer size) {
        // 分页条件，当前页码，每页个数
        IPage<Article> page = new Page<>(current, size);
        // 查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 根据时间顺序查询
        wrapper.orderByDesc(Article::getCreateDate);
        IPage<Article> articlePageing = articleService.getArticlePageing(page, wrapper);
        // 当前页数
        long atCurrent = articlePageing.getCurrent();
        // 当前页的数据
        List<Article> records = articlePageing.getRecords();
        for (Article article : records) {
            article.setContent("");
            article.setUser(userService.getById(article.getUId()));
            article.setBlock(blockService.getById(article.getBId()));
        }
        // 组装Dto
        PageResult pageResult = new PageResult();
        pageResult.setCurrent(atCurrent);
        pageResult.setList(records);

        return new JsonResult(200, "查询成功", pageResult);
    }

    /**
     * 根据文章的热度查询
     * 
     * @return
     */
    @ApiOperation("分页查询文章，热度排序-时间排序")
    @PostMapping("/article/hot")
    public Object getArticleHot(Integer current, Integer size) {
        // 分页条件
        IPage<Article> page = new Page<>(current, size);
        // 查询条件
        LambdaQueryWrapper<Article> wrapper = new LambdaQueryWrapper<>();
        // 根据分值，时间顺序查询
        wrapper.orderByDesc(Article::getScore);
        wrapper.orderByDesc(Article::getCreateDate);
        IPage<Article> articlePageing = articleService.getArticlePageing(page, wrapper);

        // 当前页数
        long atCurrent = articlePageing.getCurrent();
        // 当前页的数据
        List<Article> records = articlePageing.getRecords();
        for (Article article : records) {
            article.setContent("");
            article.setUser(userService.getById(article.getUId()));
            article.setBlock(blockService.getById(article.getBId()));
        }
        // 组装Dto
        PageResult pageResult = new PageResult();
        pageResult.setCurrent(atCurrent);
        pageResult.setList(records);

        return new JsonResult(200, "查询成功", pageResult);
    }

    /**
     * 根据关注内容查询数据
     * 
     * @param current
     * @param size
     * @return
     */
    @PostMapping("/article/follow")
    public Object getFollow(Integer current, Integer size, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        // 查询所有关注数据
        LambdaQueryWrapper<Follow> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Follow::getUid, Integer.valueOf(subject));
        List<Follow> list = followService.list(queryWrapper);
        List<Article> li = new ArrayList<>();
        for (Follow follow : list) {
            LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
            articleWrapper.eq(Article::getUId, follow.getByUid());
            List<Article> articles = articleService.list(articleWrapper);

            for (Article article : articles) {
                User byId = userService.getById(article.getUId());
                article.setUser(byId);
                li.add(article);
            }
        }
        Collections.reverse(li);
        // 组装Dto

        return new JsonResult(200, "查询成功", li);
    }

    /**
     * 判断是点赞/收藏/关注
     * 
     * @return
     */
    @GetMapping("/get/enter/{id}")
    public Object enter(@PathVariable("id") Integer aid, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        if (token == null) {
            return new JsonResult(501, "未登录，无需提示");
        }
        Enter enter = new Enter();
        String subject = token.getSubject();
        LambdaQueryWrapper<Likes> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Likes::getUid, Integer.valueOf(subject));
        queryWrapper1.eq(Likes::getAId, aid);
        Likes one1 = likeService.getOne(queryWrapper1);

        LambdaQueryWrapper<Collection> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(Collection::getUid, Integer.valueOf(subject));
        queryWrapper2.eq(Collection::getAId, aid);
        Collection one2 = collectionService.getOne(queryWrapper2);

        Article byId = articleService.getById(aid);
        LambdaQueryWrapper<Follow> queryWrapper3 = new LambdaQueryWrapper<>();
        queryWrapper3.eq(Follow::getUid, Integer.valueOf(subject));
        queryWrapper3.eq(Follow::getByUid, byId.getUId());
        Follow one3 = followService.getOne(queryWrapper3);

        enter.setLike(one1 != null);
        enter.setCollection(one2 != null);
        enter.setFollow(one3 != null);
        return new JsonResult(200, "", enter);
    }

    /**
     * 根据版块id查询所有文章
     * 
     * @param id
     * @return
     */
    @GetMapping("/get/block/{id}")
    public Object getBlockAll(@PathVariable("id") Integer id) {
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getBId, id);
        List<Article> list = articleService.list(articleWrapper);
        for (Article article : list) {
            article.setContent("");
            article.setUser(userService.getById(article.getUId()));
            article.setBlock(blockService.getById(article.getBId()));
        }
        Collections.reverse(list);
        return new JsonResult(200, "查询成功", list);
    }

    /**
     * 根据文章标题模糊查询文章
     * 
     * @param title
     * @return
     */
    @PostMapping("/search/article")
    public Object searchArticle(String title) {
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.like(Article::getTitle, title);
        List<Article> list = articleService.list(articleWrapper);
        for (Article article : list) {
            article.setContent("");
            article.setUser(userService.getById(article.getUId()));
            article.setBlock(blockService.getById(article.getBId()));
        }
        return new JsonResult(200, "", list);
    }

    /**
     * 根据uid获取用户信息
     * 
     * @param id
     * @return
     */
    @GetMapping("/get/user/info/{id}")
    public Object getUserByUid(@PathVariable("id") Integer id) {
        User byId = userService.getById(id);
        return new JsonResult(200, "", byId);
    }

    /**
     * 根据uid获取自己的文章
     * 
     * @param id
     * @return
     */
    @GetMapping("/select/article/user/{id}")
    public Object getArticleMeAll(@PathVariable("id") Integer id) {
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getUId, id);
        List<Article> list = articleService.list(articleWrapper);
        for (Article article : list) {
            article.setContent("");
            article.setUser(userService.getById(article.getUId()));
            article.setBlock(blockService.getById(article.getBId()));
        }
        return new JsonResult(200, "", list);
    }
}
