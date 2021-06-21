package com.forum.buge_server.mapper;

import com.forum.buge_server.entity.Block;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author
 * @since 2020-05-16
 */
@Mapper
@Component
public interface BlockMapper extends BaseMapper<Block> {

}
