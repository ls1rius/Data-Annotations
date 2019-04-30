package com.cm.service.impl;

import com.cm.entity.Label;
import com.cm.service.IDataService;
import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.WordDictionary;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cm.common.CommomMethod.isStopWord;
import static com.cm.common.FilesMethod.*;
import static com.cm.common.SystemMethod.executeNewFlow;

@Service("dataService")
public class DataServiceImpl implements IDataService{

    private final Integer NONECATEGORYID = -1;
    private final Integer TIMECATEGORYID = 0;
    private final Integer EVENTCATEGORYID = 1;

    Path time_item_path = Paths.get(new File( getClass().getClassLoader().getResource("dicts/time_item.txt").getPath() ).getAbsolutePath() );
    Path event_item_path = Paths.get(new File( getClass().getClassLoader().getResource("dicts/event_item.txt").getPath() ).getAbsolutePath() );
    Path stop_item_path = Paths.get(new File( getClass().getClassLoader().getResource("dicts/stop_item.txt").getPath() ).getAbsolutePath() );

    // 词典路径为Resource/dicts/jieba.dict
    public static void  loadMyDict(Path path){
        //加载自定义的词典进词库
        WordDictionary.getInstance().loadUserDict(path);
    }


    public Object recognizeData(String content){
        StringBuffer str = new StringBuffer("");

        content = content.replaceAll("\\s*","");

        //加载字典
        loadMyDict(time_item_path);
        loadMyDict(event_item_path);

        ArrayList<String>timeDictList = readDictList(time_item_path.toString());
        ArrayList<String>eventDictList = readDictList(event_item_path.toString());
        ArrayList<String>stopDictList = readDictList(stop_item_path.toString());

//        System.out.println(timeDictList);
//        System.out.println(eventDictList);
//        System.out.println(stopDictList);


//        // 字识别
//        Integer len = content.length();
//        for(int i = 0;i<len;i++){
//            str.append(content.charAt(i) + "\to\tO\n");
//        }

        //词识别
        JiebaSegmenter segmenter = new JiebaSegmenter();
        List<String> wordsList = segmenter.sentenceProcess(content);

        Integer len = wordsList.size();
        for(int i = 0;i<len;i++){
            str.append(new StringBuffer(wordsList.get(i) + "\tn\tO\n"));
        }

        writeFile(new String(str));

//        List<String> cmdList = new ArrayList<String>(){{ add("cd " + DATA_FOLDER);
//                                                        add("crf_test -m model data.txt");}};


        List<String> cmdList = new ArrayList<String>(){{
            add("cd " + DATA_FOLDER);
            add("crf_test -m model data.txt > test.rst");
            add("cd PRE_RE");
            add("python3.6 deal_rec_test_data_cre.py");
        }};


        List<String> resList = executeNewFlow(cmdList);


        len = resList.size();
        List<String> ansList = new ArrayList<>();
        for(int i = 0;i<len;i++) {
            String[] curTag = resList.get(i).split("\t");
            if(timeDictList.contains(curTag[0])){
                curTag[1] = "TIME_S";
            }
            else if(eventDictList.contains(curTag[0])){
                curTag[1] = "EVENT_S";
            }
            ansList.add(new String(curTag[0] + "\t" + curTag[1]));
        }

        len = ansList.size();
        StringBuffer res = new StringBuffer("");
        int pre = 0;
        int charsCount = 0;
        int flag = NONECATEGORYID;
        List<Label> totalEntity = new ArrayList<>();
        int entityCount = 0;
        for(int i = 0;i<len;i++){
            String[] curTag = ansList.get(i).split("\t");
            res.append(curTag[0]);

            //字典特判
            //时间词
            if(timeDictList.contains(curTag[0])){
                if(flag == TIMECATEGORYID){
                    totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
                    entityCount++;
                }
                else if(flag == EVENTCATEGORYID){
                    totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
                    entityCount++;
                }
                pre = charsCount;
                charsCount += curTag[0].length();
                totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
                entityCount++;
                pre = -1;
                flag = NONECATEGORYID;
                continue;
            }
            //事件词
            if(eventDictList.contains(curTag[0])){
                if(flag == TIMECATEGORYID){
                    totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
                    entityCount++;
                }
                else if(flag == EVENTCATEGORYID){
                    totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
                    entityCount++;
                }
                pre = charsCount;
                charsCount += curTag[0].length();
                totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
                entityCount++;
                pre = -1;
                flag = NONECATEGORYID;
                continue;
            }

            //处理停用词
            if(isStopWord(curTag[0]) || stopDictList.contains(curTag[0])){
                if (i - 1 >= 0) {
                    String[] preTag = ansList.get(i - 1).split("\t");
                    if(preTag[1].charAt(0)!=curTag[1].charAt(0)){
                        curTag[1] = "O";
                    }
                    if(preTag[1].charAt( preTag[1].length() - 1 ) == 'S'){
                        curTag[1] = "O";
                    }
                    if(preTag[1].charAt(0) == 'E'){
                        curTag[1] = "O";
                    }
                }
                if(i + 1 < len){
                    String[] postTag = ansList.get(i + 1).split("\t");
                    if(postTag[1].charAt(0)!=curTag[1].charAt(0)){
                        curTag[1] = "O";
                    }
                    if(postTag[1].charAt( postTag[1].length() - 1 ) == 'S'){
                        curTag[1] = "O";
                    }
                    if(postTag[1].charAt(0) == 'E'){
                        curTag[1] = "O";
                    }
                }
            }

            if(curTag[1].charAt(0)=='T'){
                if(flag == TIMECATEGORYID){

                }
                else if(flag == EVENTCATEGORYID){
                    totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
                    entityCount++;
                    pre = charsCount;
                }
                else{
                    pre = charsCount;
                }
                flag = TIMECATEGORYID;
            }
            else if(curTag[1].charAt(0)=='E'){
                if(flag == TIMECATEGORYID){
                    totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
                    entityCount++;
                    pre = charsCount;
                }
                else if(flag == EVENTCATEGORYID){

                }
                else{
                    pre = charsCount;
                }
                flag = EVENTCATEGORYID;
            }
            else{
                if(flag == TIMECATEGORYID){
                    totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
                    entityCount++;
                    pre = -1;
                }
                else if(flag == EVENTCATEGORYID){
                    totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
                    entityCount++;
                    pre = -1;
                }
                else{

                }
                flag = NONECATEGORYID;
            }
            charsCount += curTag[0].length();
        }

        HashMap<String,Object> ans = new HashMap<>();

        ans.put("content",res);
        ans.put("totalEntity",totalEntity);
        System.gc();
        return ans;
    }

//    public Object recognizeData(String content){
//        StringBuffer str = new StringBuffer("");
//
//        content = content.replaceAll("\\s*","");
//
//
//        loadMyDict(time_item_path);
//        loadMyDict(event_item_path);
//
//        ArrayList<String>timeDictList = readDictList(time_item_path.toString());
//        ArrayList<String>eventDictList = readDictList(event_item_path.toString());
//        ArrayList<String>stopDictList = readDictList(stop_item_path.toString());
//
////        System.out.println(timeDictList);
////        System.out.println(eventDictList);
////        System.out.println(stopDictList);
//
//
////        // 字识别
////        Integer len = content.length();
////        for(int i = 0;i<len;i++){
////            str.append(content.charAt(i) + "\to\tO\n");
////        }
//
//        //词识别
//        JiebaSegmenter segmenter = new JiebaSegmenter();
//        List<String> wordsList = segmenter.sentenceProcess(content);
//
//        Integer len = wordsList.size();
//        for(int i = 0;i<len;i++){
//            str.append(new StringBuffer(wordsList.get(i) + "\tn\tO\n"));
//        }
//
//        writeFile(new String(str));
//
//        List<String> cmdList = new ArrayList<String>(){{ add("cd " + DATA_FOLDER);
//                                                        add("crf_test -m model data.txt");}};
//
//
//
//        List<String> resList = executeNewFlow(cmdList);
//
//
//        len = resList.size();
//        List<String> ansList = new ArrayList<>();
//        for(int i = 0;i<len;i++) {
//            String[] curTag = resList.get(i).split("\t");
//            if(timeDictList.contains(curTag[0])){
//                curTag[1] = "TIME_S";
//            }
//            else if(eventDictList.contains(curTag[0])){
//                curTag[1] = "EVENT_S";
//            }
//            ansList.add(new String(curTag[0] + "\t" + curTag[1]));
//        }
//
//        len = ansList.size();
//        StringBuffer res = new StringBuffer("");
//        int pre = 0;
//        int charsCount = 0;
//        int flag = NONECATEGORYID;
//        List<Label> totalEntity = new ArrayList<>();
//        int entityCount = 0;
//        for(int i = 0;i<len;i++){
//            String[] curTag = ansList.get(i).split("\t");
//            res.append(curTag[0]);
//
//            //字典特判
//            //时间词
//            if(timeDictList.contains(curTag[0])){
//                if(flag == TIMECATEGORYID){
//                    totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
//                    entityCount++;
//                }
//                else if(flag == EVENTCATEGORYID){
//                    totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
//                    entityCount++;
//                }
//                pre = charsCount;
//                charsCount += curTag[0].length();
//                totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
//                entityCount++;
//                pre = -1;
//                flag = NONECATEGORYID;
//                continue;
//            }
//            //事件词
//            if(eventDictList.contains(curTag[0])){
//                if(flag == TIMECATEGORYID){
//                    totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
//                    entityCount++;
//                }
//                else if(flag == EVENTCATEGORYID){
//                    totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
//                    entityCount++;
//                }
//                pre = charsCount;
//                charsCount += curTag[0].length();
//                totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
//                entityCount++;
//                pre = -1;
//                flag = NONECATEGORYID;
//                continue;
//            }
//
//            //处理停用词
//            if(isStopWord(curTag[0]) || stopDictList.contains(curTag[0])){
//                if (i - 1 >= 0) {
//                    String[] preTag = ansList.get(i - 1).split("\t");
//                    if(preTag[1].charAt(0)!=curTag[1].charAt(0)){
//                        curTag[1] = "O";
//                    }
//                    if(preTag[1].charAt( preTag[1].length() - 1 ) == 'S'){
//                        curTag[1] = "O";
//                    }
//                }
//                if(i + 1 < len){
//                    String[] postTag = ansList.get(i + 1).split("\t");
//                    if(postTag[1].charAt(0)!=curTag[1].charAt(0)){
//                        curTag[1] = "O";
//                    }
//                    if(postTag[1].charAt( postTag[1].length() - 1 ) == 'S'){
//                        curTag[1] = "O";
//                    }
//                }
//            }
//
//            if(curTag[1].charAt(0)=='T'){
//                if(flag == TIMECATEGORYID){
//
//                }
//                else if(flag == EVENTCATEGORYID){
//                    totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
//                    entityCount++;
//                    pre = charsCount;
//                }
//                else{
//                    pre = charsCount;
//                }
//                flag = TIMECATEGORYID;
//            }
//            else if(curTag[1].charAt(0)=='E'){
//                if(flag == TIMECATEGORYID){
//                    totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
//                    entityCount++;
//                    pre = charsCount;
//                }
//                else if(flag == EVENTCATEGORYID){
//
//                }
//                else{
//                    pre = charsCount;
//                }
//                flag = EVENTCATEGORYID;
//            }
//            else{
//                if(flag == TIMECATEGORYID){
//                    totalEntity.add(new Label(entityCount,TIMECATEGORYID,pre,charsCount));
//                    entityCount++;
//                    pre = -1;
//                }
//                else if(flag == EVENTCATEGORYID){
//                    totalEntity.add(new Label(entityCount,EVENTCATEGORYID,pre,charsCount));
//                    entityCount++;
//                    pre = -1;
//                }
//                else{
//
//                }
//                flag = NONECATEGORYID;
//            }
//            charsCount += curTag[0].length();
//        }
//
//        HashMap<String,Object> ans = new HashMap<>();
//
//        ans.put("content",res);
//        ans.put("totalEntity",totalEntity);
//        System.gc();
//        return ans;
//    }
//

    //训练数据预留实现方法
    public Object trainTheData(String content){
        List<String> cmdList = new ArrayList<String>(){{
            add("cd " + DATA_FOLDER);
            add("cd " + DATA_FOLDER);
            add("cd " + DATA_FOLDER);

        }};

        List<String> resList = executeNewFlow(cmdList);
        return StringUtils.join(resList,"\n");
    }

}
