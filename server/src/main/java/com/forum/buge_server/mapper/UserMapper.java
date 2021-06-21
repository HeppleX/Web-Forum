package com.forum.buge_server.mapper;

import com.forum.buge_server.entity.User;
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
public interface UserMapper extends BaseMapper<User> {

}
