package com.cm.controller;

import com.cm.service.IFileService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;

import static com.cm.common.FilesMethod.delFile;

@CrossOrigin(origins = "*",maxAge = 3600)
@Controller
@RequestMapping(value = "/file")
@ResponseBody
public class FileController {

    @Resource(name = "fileService")
    private IFileService fileService;

    //存储文件
    @RequestMapping(value = "/saveText",method = RequestMethod.POST)
    public Object saveText(@RequestBody HashMap<String, Object> mp){
        String content = (String) mp.get("content");
        String filename = (String) mp.get("filename");
        return fileService.saveText(content,filename);
    }
    //获取所有文件列表
    @RequestMapping(value = "/getFileList",method = RequestMethod.POST)
    public Object getFileList(@RequestBody HashMap<String, Object> mp){
        ArrayList<String> dateList = (ArrayList<String>) mp.get("dateList");
        Integer currentPage = (Integer) mp.get("currentPage");
        Integer pageSize = (Integer) mp.get("pageSize");
        Integer sortStatus = (Integer) mp.get("sortStatus");
        Boolean isSearching = (Boolean) mp.get("isSearching");
        String searchFile = (String) mp.get("searchFile");
        return fileService.getFileList(dateList,currentPage,pageSize,sortStatus,isSearching,searchFile);
    }
    //获取不同的日期
    @RequestMapping(value = "/getDistinctDate",method = RequestMethod.GET)
    public Object getDistinctDate(){
        return fileService.getDistinctDate();
    }
    //删除文件数据
    @RequestMapping(value = "/deleteData",method = RequestMethod.DELETE)
    public Object deleteData(@RequestBody HashMap<String, Object> mp){
        Integer id  = (Integer) ((HashMap<String, Object>) mp.get("info")).get("id");
        return fileService.deleteData(id);
    }
    //点击编辑按钮后，获取相应单条数据
    @RequestMapping(value = "/getOneData",method = RequestMethod.POST)
    public Object getOneData(@RequestBody HashMap<String, Object> mp){
        Integer id  = (Integer) mp.get("id");
        return fileService.getOneData(id);
    }
    //更新数据文件名
    @RequestMapping(value = "/updateDataFilename",method = RequestMethod.PUT)
    public Object updateDataFilename(@RequestBody HashMap<String, Object>mp){
        Integer id  = (Integer) mp.get("id");
        String newFilename = (String) mp.get("newFilename");
        return fileService.updateDataFilename(id,newFilename);
    }
}
