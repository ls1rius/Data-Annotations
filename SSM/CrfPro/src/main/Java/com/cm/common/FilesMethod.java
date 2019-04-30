package com.cm.common;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FilesMethod {

    //上传文件的存储位置
    public final static String UPLOAD_FOLDER = "/Users/lihao/Desktop/Data/";
    //训练之后的模型存储位置
    public final static String DATA_FOLDER = UPLOAD_FOLDER + "modelData/";
    /**
     * 读入TXT文件
     */
    public static String readFile(String pathname) {
        StringBuffer res = new StringBuffer();
//        String pathname = "data.txt"; // 绝对路径或相对路径都可以，写入文件时演示相对路径,读取以上路径的input.txt文件
        //防止文件建立或读取失败，用catch捕捉错误并打印，也可以throw;
        //不关闭文件会导致资源的泄露，读写文件都同理
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
//                System.out.println(line);
                res.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(res);
    }
    public static ArrayList<String> readDictList(String pathname) {

        ArrayList<String> dictList = new ArrayList<>();
        try (FileReader reader = new FileReader(pathname);
             BufferedReader br = new BufferedReader(reader) // 建立一个对象，它把文件内容转成计算机能读懂的语言
        ) {
            String line;
            //网友推荐更加简洁的写法
            while ((line = br.readLine()) != null) {
                // 一次读入一行数据
//                System.out.println(line);
                dictList.add(line.split("\t")[0]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dictList;
    }

    /**
     * 写入TXT文件
     */
    public static void writeFile(String str) {
        try {
            //如果没有files文件夹，则创建
            Path path = Paths.get(DATA_FOLDER + "data.txt");
            if (!Files.isWritable(path)) {
                try {
                    Files.createDirectories(Paths.get(DATA_FOLDER));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            File writeName = new File(DATA_FOLDER + "data.txt"); // 相对路径，如果没有则要建立一个新的output.txt文件
            writeName.createNewFile(); // 创建新文件,有同名的文件的话直接覆盖
            try (FileWriter writer = new FileWriter(writeName);
                 BufferedWriter out = new BufferedWriter(writer)
            ) {
                out.write(str); // \r\n即为换行
                out.flush(); // 把缓存区内容压入文件
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    存储数据至json文件
    public static String saveToFile(String str){

        String pathname = new TimeString().getTimeString();
        String filePath = UPLOAD_FOLDER + pathname+".json";
        File file = new File(filePath);
        //如果文件已经存在
        if(file.exists()){
            pathname = new TimeString().getTimeString();
            filePath = UPLOAD_FOLDER + pathname+".json";
        }
        byte[] bytes = str.getBytes();
        Path path = Paths.get(filePath);
        //如果没有files文件夹，则创建
        if (!Files.isWritable(path)) {
            try {
                Files.createDirectories(Paths.get(UPLOAD_FOLDER));
                //文件写入指定路径
                Files.write(path, bytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return filePath;
    }

//    删除文件
    public static boolean delFile(String pathname) {
        File file = new File(pathname);
        if (!file.exists()) {
            return false;
        }
        else {
            file.delete();
            return true;
        }
    }
}
