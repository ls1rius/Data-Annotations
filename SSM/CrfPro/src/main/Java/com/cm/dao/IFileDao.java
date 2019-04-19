package com.cm.dao;

import com.cm.entity.FileData;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;

public interface IFileDao {
    void saveText(@Param("str") String str,
                  @Param("filename") String filename,
                  @Param("date") String date,
                  @Param("time") String time,
                  @Param("datetime") String datetime);

    Integer getInsertTextId();

    ArrayList<FileData>getFileList(@Param("dateList") ArrayList<String> dateList,
                                   @Param("startIndex") Integer startIndex,
                                   @Param("getNum") Integer getNum,
                                   @Param("sortStatus") Integer sortStatus,
                                   @Param("isSearching") Boolean isSearching,
                                   @Param("searchFile") String searchFile);

    Integer getFileCount(@Param("dateList")ArrayList<String> dateList,
                         @Param("isSearching") Boolean isSearching,
                         @Param("searchFile") String searchFile);

    ArrayList<String> getDistinctDate();

    String getDataFilepath(@Param("id") Integer id);

    void deleteData(@Param("id") Integer id);

    String getDataFilename(@Param("id") Integer id);

    void updateDataFilename(@Param("id") Integer id,
                            @Param("newFilename") String newFilename);

}
