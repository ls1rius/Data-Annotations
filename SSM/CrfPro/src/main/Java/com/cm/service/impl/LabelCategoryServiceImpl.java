package com.cm.service.impl;

import com.cm.dao.ILabelCategoryDao;
import com.cm.entity.LabelCategory;
import com.cm.service.ILabelCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("labelDataService")
public class LabelCategoryServiceImpl implements ILabelCategoryService {

    @Resource
    private ILabelCategoryDao labelData;

    @Override
    public List<LabelCategory> getLabelCategory() {
        return labelData.getLabelCategory();
    }
}
