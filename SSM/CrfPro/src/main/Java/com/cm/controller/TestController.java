package com.cm.controller;

import com.cm.entity.User;
import com.cm.service.IUserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.cm.common.FilesMethod.saveToFile;
import static com.cm.common.FilesMethod.writeFile;
import static com.cm.common.SystemMethod.executeNewFlow;
import static com.cm.common.TimeString.*;

@Controller
@RequestMapping("/test")
public class TestController {

    @Resource(name="userService")
    private IUserService userService;

    @RequestMapping("/showUser")
    public void selectUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        long userId = Long.parseLong(request.getParameter("id"));
        User user = this.userService.selectUser(userId);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(user));
        response.getWriter().close();
    }

    @RequestMapping(value = "/getUser")
    @ResponseBody
    public User getUser(@RequestParam("id") int id) {

        return this.userService.selectUser(id);
    }


//    两种不同的参数请求方式

    @RequestMapping("/test")
    @ResponseBody
    public Object test(@RequestParam("id") String id){
        HashMap<String,Object> mp = new HashMap<String, Object>();
        mp.put("res","TestString");
        return mp;
    }

    @RequestMapping("/check")
    public void check(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        HashMap<String,Object>mp = new HashMap<String, Object>();
        mp.put("res","CheckString");

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(mp));
        response.getWriter().close();
    }



    @RequestMapping("/dealData")
    public void dealData(HttpServletRequest request, HttpServletResponse response) throws IOException{
        request.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");
        response.setCharacterEncoding("UTF-8");

        String str = "";

        try{
            str = request.getParameter("data");
        }catch (Exception e){
            throw e;
        }

        JiebaSegmenter segmenter = new JiebaSegmenter();

        List<String> wordList = segmenter.sentenceProcess(str);

        str = "";

        for(int i = 0;i < wordList.size();i++){
            str += wordList.get(i) + "\t" + "o" + "\t" + "O\n";
        }

        writeFile(str);


        List<String> cmdList = new ArrayList<String>(){{ add("cd /Users/lihao/Desktop/CCCRF/"); add("crf_test -m model data.txt");}};
        List<String> ansList = executeNewFlow(cmdList);

        str = String.join("\n",ansList);

        System.out.println(str);

        HashMap<String,Object>json = new HashMap<>();
        json.put("result",str);

        ObjectMapper mapper = new ObjectMapper();
        response.getWriter().write(mapper.writeValueAsString(json));
        response.getWriter().close();
    }

//    public static void main(String[] args){
//        getFileList(0,10);
//    }
}
