package com.example.spireforjava.controller;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: ljw_spireforjava
 * @description:
 * @author: luojiwen
 * @create: 2019-12-12 21:28
 **/
public class TEST20191212 {


    public static void main(String[] args){
String file ="http://10.49.32.112/engine_fastdfs/group1/M00/00/3B/CjEgcF3yQdmAM0d3AAADoOUwvGQ367.txt";
////高级流读取
//        String result = "";
//        try {
//            URL myurl=new URL(file);//创建URL对象
//            URLConnection uc=myurl.openConnection();
//            // 判断的文件输入流
//            BufferedReader br = new BufferedReader(new InputStreamReader(uc.getInputStream(), "GBK"));// 构造一个BufferedReader类来读取文件
//            String s = null;
//            while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
//                result = result + "\n" + s;
//            }
//            br.close();
//            System.out.println(result);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }


        String clientIdCard ="";
        System.out.println("".equals(clientIdCard)?"无":clientIdCard);
        System.out.println(5%5);
//        readDefinition(file);
    }






    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if (!targetFile.exists()) {
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath + fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    /**
     * 功能描述: 读取指定文件的里面的内容
     *
     * @param fwqPath
     * @return
     * @auther WenJi Luo
     * @date 2019/6/19 14:43
     */
    public static String readDefinition(String fwqPath) {
        String result = "";
        String encodeType=EncodeUtils.getEncode(fwqPath);
        BufferedReader reader = null;
        InputStream inputStream = null;
        try {
            URL myurl=new URL(fwqPath);//创建URL对象
            URLConnection uc=myurl.openConnection();
            // 判断的文件输入流
            inputStream = uc.getInputStream();
            byte[] head = new byte[3];
            inputStream.read(head);
            reader = new BufferedReader(new InputStreamReader(uc.getInputStream(),encodeType));
            String tempString = null;
            while ((tempString = reader.readLine()) != null) {
                result = result + "\n" + tempString;
            }
            inputStream.close();
            reader.close();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            // TODO Auto-generated catch block
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
        return result;
    }

    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param fileName
     * @param savePath
     * @throws IOException
     */
    public static void downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException{
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");

        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdirs();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }

        System.out.println("info:"+url+" download success");

    }


    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }
}
