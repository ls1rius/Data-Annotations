package com.cm.service.impl;

import com.cm.dao.IConnectionCategoryDao;
import com.cm.entity.ConnectionCategory;
import com.cm.entity.LabelCategory;
import com.cm.service.IConnectionCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("connectionCategoryService")
public class ConnectionCategoryServiceImpl implements IConnectionCategoryService {

    @Resource
    private IConnectionCategoryDao connectionCategoryDao;
    @Override
    public List<ConnectionCategory> getConnectionCategory() {
        ArrayList<ConnectionCategory> connectionCategoryList = (ArrayList<ConnectionCategory>)connectionCategoryDao.getConnectionCategory();
        int len = (int)connectionCategoryList.size();
        for(int i =0;i<len;i++){
            ConnectionCategory tmpCategory = connectionCategoryList.get(i);
            tmpCategory.setId(i);
            connectionCategoryList.set(i,tmpCategory);
        }
        return connectionCategoryList;
    }
}
