package com.forum.buge_server.controller.sys;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.forum.buge_server.entity.*;
import com.forum.buge_server.config.JwtConfig;
import com.forum.buge_server.service.*;
import com.forum.buge_server.config.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author
 * @date 2020-05-17
 **/

@RestController
@RequestMapping("/sys")
public class SysLoginController {

    @Autowired
    private JwtConfig jwtConfig;

    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private BlockService blockService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleService articleService;

    @PostMapping("/delete/user")
    public Object deleteUserById(Integer id) {
        boolean b = userService.removeById(id);
        return new JsonResult(200, "删除成功");
    }

    /**
     * 管理员登陆
     * 
     * @param email
     * @param password
     * @return
     */
    @PostMapping("/login")
    public Object SysLogin(String email, String password) {
        JSONObject json = new JSONObject();
        Admin sysEmail = adminService.getSysEmail(email);
        if (sysEmail == null) {
            return new JsonResult(403, "账号不存在");
        }
        if (!sysEmail.getPassword().equals(password)) {
            return new JsonResult(403, "密码错误");
        }
        String id = sysEmail.getId() + "";

        String token = jwtConfig.createToken(id);

        if (!StringUtils.isEmpty(token)) {
            json.put("token", token);
        }
        sysEmail.setPassword("");
        return new JsonResult(200, "登陆成功", token);
    }

    /**
     * 获取所有的用户
     * 
     * @return
     */
    @GetMapping("/user/list")
    public Object getUserAll() {
        List<User> list = userService.list();
        Collections.reverse(list);
        return new JsonResult(200, "查询成功", list);
    }

    /**
     * 获取所有的版块
     * 
     * @return
     */
    @GetMapping("/block/list")
    public Object getBlock() {
        List<Block> list = blockService.list();
        Collections.reverse(list);
        return new JsonResult(200, "", list);
    }

    /**
     * 获取所有的留言
     * 
     * @return
     */
    @GetMapping("/comment/list")
    public Object getComment() {
        List<Comment> list = commentService.list();
        for (Comment comment : list) {
            comment.setUser(userService.getById(comment.getUId()));
        }
        Collections.reverse(list);
        return new JsonResult(200, "", list);
    }

    /**
     * 获取所有的文章
     * 
     * @return
     */
    @GetMapping("/article/list")
    public Object getArticle() {
        List<Article> list = articleService.list();
        for (Article article : list) {
            article.setUser(userService.getById(article.getUId()));
            article.setBlock(blockService.getById(article.getBId()));
        }
        Collections.reverse(list);
        return new JsonResult(200, "", list);
    }

    /**
     * 根据留言主键删除
     * 
     * @param id
     * @return
     */
    @PostMapping("/delete/comment")
    public Object deleteComment(Integer id) {
        boolean b = commentService.removeById(id);
        return new JsonResult(200, "");
    }

    /**
     * 版块添加
     * 
     * @param name
     * @param introduce
     * @param head
     * @return
     */
    @PostMapping("/add/block")
    public Object addBlock(String name, String introduce, String head) {
        Block block = new Block();
        block.setBName(name);
        block.setBIntroduce(introduce);
        block.setHead(head);
        block.setCreateDate(new Date());
        block.setUpdateDate(new Date());
        block.setBScore(0);
        boolean save = blockService.save(block);
        return new JsonResult(200, "");
    }

    /**
     * 跟新版块
     * 
     * @param block
     * @return
     */
    @PostMapping("/update/block")
    public Object updateBlock(Block block) {
        System.out.println(block);
        boolean b = blockService.updateById(block);
        return new JsonResult(200, "");
    }

    /**
     * 添加用户的分值
     * 
     * @return
     */
    @PostMapping("/add/user/scope")
    public Object addUserScope(Integer id, Integer scope) {
        User byId = userService.getById(id);
        byId.setScore(byId.getScore() + scope);
        userService.updateById(byId);
        return new JsonResult(200, "");
    }

}
