package com.cm.common;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SystemMethod {
    //执行cmd命令，并读取返回数据
    public static List<String> executeNewFlow(List<String> commands) {
        List<String> rspList = new ArrayList<String>();
        Runtime run = Runtime.getRuntime();
        try {
            Process proc = run.exec("/bin/bash", null, null);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(proc.getOutputStream())), true);
            for (String line : commands) {
                out.println(line);
            }
            out.println("exit");// 这个命令必须执行，否则in流不结束。
            String rspLine = "";
            while ((rspLine = in.readLine()) != null) {
                String[] tot = rspLine.split("\t");
                if(tot.length==4){
                    rspList.add(tot[0]+"\t"+tot[3]);
                }
                else{

                }
            }
            proc.waitFor();
            in.close();
            out.close();
            proc.destroy();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.gc();
        return rspList;
    }

}
