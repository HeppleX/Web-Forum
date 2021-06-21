package com.forum.buge_server.service.impl;

import com.forum.buge_server.entity.Comment;
import com.forum.buge_server.mapper.CommentMapper;
import com.forum.buge_server.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
