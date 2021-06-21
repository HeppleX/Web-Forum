package com.forum.buge_server.service;

import com.forum.buge_server.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.forum.buge_server.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 * @since 2020-05-16
 */
public interface UserService extends IService<User> {

    User getEmail(String email);

}
