package com.cm.service;

import com.cm.entity.FileData;

import java.util.ArrayList;

public interface IFileService {
    Object saveText(String str,String filename);

    Object getFileList(ArrayList<String> dateList,
                       Integer currentPage,
                       Integer pageSize,
                       Integer sortStatus,
                       Boolean isSearching,
                       String searchFile);

    ArrayList<String> getDistinctDate();

    Object deleteData(Integer id);

    Object getOneData(Integer id);

    Object updateDataFilename(Integer id,String newFilename);

}
