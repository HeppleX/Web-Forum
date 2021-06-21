package com.forum.buge_server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.forum.buge_server.entity.Article;
import com.forum.buge_server.entity.History;
import com.forum.buge_server.mapper.HistoryMapper;
import com.forum.buge_server.service.HistoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {

    @Autowired
    HistoryMapper historyMapper;

    @Override
    public IPage<History> getHistoryPageing(IPage<History> iPage, LambdaQueryWrapper<History> wrapper) {
        IPage<History> historyIPage = historyMapper.selectPage(iPage, wrapper);
        return historyIPage;
    }
}
