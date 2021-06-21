package com.forum.buge_server.service.impl;

import com.forum.buge_server.entity.Likes;
import com.forum.buge_server.mapper.LikeMapper;
import com.forum.buge_server.service.LikeService;
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
public class LikeServiceImpl extends ServiceImpl<LikeMapper, Likes> implements LikeService {

}
