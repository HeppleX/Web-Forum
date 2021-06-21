package com.forum.buge_server.service;

import com.forum.buge_server.entity.Admin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author
 * @since 2020-05-16
 */
public interface AdminService extends IService<Admin> {

    /**
     * 根据管理员邮箱查询
     * 
     * @param email
     * @return
     */
    Admin getSysEmail(String email);

}
