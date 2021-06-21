package com.forum.buge_server.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.forum.buge_server.entity.Article;
import com.forum.buge_server.entity.Comment;
import com.forum.buge_server.config.JwtConfig;
import com.forum.buge_server.service.CommentService;
import com.forum.buge_server.service.UserService;
import com.forum.buge_server.config.JsonResult;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

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
public class CommentController {

    @Autowired
    JwtConfig jwtConfig;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    /**
     * 留言
     * 
     * @param comment
     * @return
     */
    @PostMapping("/add/comment")
    public Object addComment(Comment comment, HttpServletRequest request) {
        Claims token = jwtConfig.getTokenClaim(request.getHeader("token"));
        String subject = token.getSubject();
        comment.setCreateDate(new Date());
        comment.setUId(Integer.valueOf(subject));
        boolean save = commentService.save(comment);
        comment.setUser(userService.getById(Integer.valueOf(subject)));
        return new JsonResult(200, "评论成功!", comment);
    }

    /**
     * 根据文章的id获取文章的所有评论
     * 
     * @return
     */
    @GetMapping("/get/comment/{id}")
    public Object getCommentAll(@PathVariable("id") Integer id) {
        LambdaQueryWrapper<Comment> commentWrapper = new LambdaQueryWrapper<>();
        commentWrapper.eq(Comment::getAId, id);
        List<Comment> list = commentService.list(commentWrapper);
        for (Comment comment : list) {
            comment.setUser(userService.getById(comment.getUId()));
        }
        return new JsonResult(200, "", list);
    }

}
