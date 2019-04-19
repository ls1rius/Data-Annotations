package com.cm.service.impl;

import com.cm.dao.IConnectionCategoryDao;
import com.cm.service.IConnectionCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("connectionCategoryService")
public class ConnectionCategoryServiceImpl implements IConnectionCategoryService {

    @Resource
    private IConnectionCategoryDao connectionCategoryDao;
    @Override
    public Object getConnectionCategory() {
        return connectionCategoryDao.getConnectionCategory();
    }
}
