package com.forum.buge_server.service.impl;

import com.forum.buge_server.entity.Block;
import com.forum.buge_server.mapper.BlockMapper;
import com.forum.buge_server.service.BlockService;
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
public class BlockServiceImpl extends ServiceImpl<BlockMapper, Block> implements BlockService {

}
