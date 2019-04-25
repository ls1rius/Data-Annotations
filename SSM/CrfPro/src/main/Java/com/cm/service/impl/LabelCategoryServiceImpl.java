package com.cm.service.impl;

import com.cm.dao.ILabelCategoryDao;
import com.cm.entity.Label;
import com.cm.entity.LabelCategory;
import com.cm.service.ILabelCategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("labelDataService")
public class LabelCategoryServiceImpl implements ILabelCategoryService {

    @Resource
    private ILabelCategoryDao labelData;

    @Override
    public List<LabelCategory> getLabelCategory() {
        ArrayList<LabelCategory> labelCategoryList = (ArrayList<LabelCategory>)labelData.getLabelCategory();
        int len = (int)labelCategoryList.size();
        for(int i =0;i<len;i++){
            LabelCategory tmpCategory = labelCategoryList.get(i);
            tmpCategory.setId(i);
            labelCategoryList.set(i,tmpCategory);
        }
        return labelCategoryList;
    }
}
