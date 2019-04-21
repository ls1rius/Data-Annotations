package com.cm.controller;

import com.cm.entity.Label;
import com.cm.service.IConnectionCategoryService;
import com.cm.service.IDataService;
import com.cm.service.ILabelCategoryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping(value = "/data")
@ResponseBody
public class DataController {

    @Resource(name="dataService")
    private IDataService dataService;

    @Resource(name="labelDataService")
    private ILabelCategoryService labelDataService;

    @Resource(name="connectionCategoryService")
    private IConnectionCategoryService connectionCategoryService;

    @RequestMapping(value = "/getDefaultData",method = RequestMethod.GET)
    public Object getDefaultData() {
        HashMap<String, Object>ans = new HashMap<>();
        ans.put("content","");
        ans.put("labelCategories",getLabelCategory());
        ans.put("labels",new ArrayList<>());
        ans.put("connectionCategories",getConnectionCategory());
        ans.put("connections",new ArrayList<>());
        return ans;
    }

    @RequestMapping(value = "/dealData",method = RequestMethod.POST)
    public Object dealData(@RequestBody HashMap<String, Object> mp) {
        String content = (String) mp.get("content");
        HashMap<String, Object>ans = new HashMap<>();
        HashMap<String, Object>recognizedData  = (HashMap<String, Object>)dataService.recognizeData(content);
        ans.put("content",recognizedData.get("content"));
        ans.put("labelCategories",getLabelCategory());
        ans.put("labels",recognizedData.get("totalEntity"));
        ans.put("connectionCategories",getConnectionCategory());
        ans.put("connections",new ArrayList<>());
        return ans;
    }

    @RequestMapping(value = "/getLabelData",method = RequestMethod.GET)
    public Object getLabelCategory(){
        return labelDataService.getLabelCategory();
    }

    @RequestMapping(value = "/getConnectionCategory",method = RequestMethod.GET)
    public Object getConnectionCategory(){
        return connectionCategoryService.getConnectionCategory();
    }

}
