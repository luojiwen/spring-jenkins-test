package com.example.spireforjava.controller;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TextSelection;
import com.spire.doc.fields.TextRange;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

/**
 * @program: ljw_spireforjava
 * @description:
 * @author: luojiwen
 * @create: 2019-12-09 11:21
 **/
public class TEST20191210 {

    public static void main(String[] args) throws IOException {
        Date startTime=new Date();
//D:\test\20191210\\鉴-二联体P1.docx
//        ？C:\Users\c-luojiwen\Desktop\\rSZ19M7C.F.txt
//        Document document = new Document();
//        document.loadFromFile("D:\\test\\20191210\\222.docx");
//        document.saveToFile("D:\\test\\20191210\\鉴-二联体P1.pdf",FileFormat.PDF);
//
//        System.out.println("COST TIME===="+(System.currentTimeMillis()-startTime.getTime()));
        readFileByLines("http://10.49.32.112/engine_fastdfs/group1/M00/00/3A/CjEgcF3x7AiAZZrIAAAhjtREbgo675.txt");
    }


    // 读取文件
    public static String readFileByLines(String fileName) throws IOException {
        String result = "";

        BufferedReader reader = null;
        InputStream inputStream = null;
        URL myurl=new URL(fileName);//创建URL对象
        URLConnection uc=myurl.openConnection();
        try {
            // 判断的文件输入流
            inputStream = uc.getInputStream();
            byte[] head = new byte[3];
            inputStream.read(head);
            //判断TXT文件编码格式
            if (head[0] == -1 && head[1] == -2 ){
                //Unicode              -1,-2,84
                reader = new BufferedReader(new InputStreamReader(uc.getInputStream(),"Unicode"));
            }else if (head[0] == -2 && head[1] == -1 ){
                //Unicode big endian   -2,-1,0,84
                reader = new BufferedReader(new InputStreamReader(uc.getInputStream(),"UTF-16"));
            }else if(head[0]==-17 && head[1]==-69 && head[2] ==-65) {
                //UTF-8                -17,-69,-65,84
                reader = new BufferedReader(new InputStreamReader(uc.getInputStream(),"UTF-8"));
            }else{
                //ANSI                  84 = T
                reader = new BufferedReader(new InputStreamReader(uc.getInputStream(),"gb2312"));
            }
            String tempString = null;
            int line = 1;
            while ((tempString = reader.readLine()) != null) {
                result = result + "\n" + tempString;
                line++;
            }
            inputStream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e1) {
                }
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                }
            }
        }
        System.out.println(result);
        return result;
    }

}
