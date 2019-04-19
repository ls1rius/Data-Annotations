package com.cm.service.impl;

import com.cm.dao.IFileDao;
import com.cm.entity.FileData;
import com.cm.service.IFileService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.HashMap;

import static com.cm.common.FilesMethod.delFile;
import static com.cm.common.FilesMethod.readFile;
import static com.cm.common.FilesMethod.saveToFile;
import static com.cm.common.TimeString.getDateFormat;
import static com.cm.common.TimeString.getTimeString;

@Service("fileService")
public class FileServiceImpl implements IFileService{

    @Resource
    private IFileDao fileDao;

    @Override
    public Object saveText(String str,String filename) {

        HashMap<String, Object> res = new HashMap<>();

        String path = saveToFile(str);
        String datetime = getTimeString();
        String[] datetimeSplit = getDateFormat().split(" ");;
        String date = datetimeSplit[0];
        String time = datetimeSplit[1];

        res.put("filename",filename);
        res.put("date",date);
        res.put("time",time);

        String[] tmpFilename = filename.split("\\.");
        filename = filename.substring(0,filename.length() - tmpFilename[tmpFilename.length - 1].length() - 1) + ".json";

        fileDao.saveText(path,filename, date,time,datetime);
        String[] pathList = path.split("/");
        res.put("path",pathList[pathList.length-1]);
        res.put("id",fileDao.getInsertTextId());
        return res;
    }

    @Override
    public Object getFileList(ArrayList<String> dateList, Integer currentPage, Integer pageSize, Integer sortStatus, Boolean isSearching,String searchFile) {
        Integer startIndex = (currentPage - 1) * pageSize;
        Integer getNum = pageSize;
        HashMap<String,Object> res = new HashMap<>();
        res.put("fileList",fileDao.getFileList(dateList,startIndex,getNum,sortStatus,isSearching,searchFile));
        res.put("fileCount",fileDao.getFileCount(dateList,isSearching,searchFile));
        return res;
    }

    @Override
    public ArrayList<String> getDistinctDate() {
        return fileDao.getDistinctDate();
    }

    @Override
    public Object deleteData(Integer id) {
        boolean res = delFile(fileDao.getDataFilepath(id));
        fileDao.deleteData(id);
        HashMap<String,Object>mp = new HashMap<>();
        mp.put("res",res);
        return mp;
    }

    @Override
    public Object getOneData(Integer id) {
        String filepath = fileDao.getDataFilepath(id);
        HashMap<String,Object>mp = new HashMap<>();
        String content = readFile(filepath);
        mp.put("id",id);
        mp.put("filename",fileDao.getDataFilename(id));
        mp.put("content",content);
        return mp;
    }

    @Override
    public Object updateDataFilename(Integer id, String newFilename) {
        fileDao.updateDataFilename(id,newFilename);
        HashMap<String,Object>mp = new HashMap<>();
        mp.put("res","success");
        return mp;
    }
}
