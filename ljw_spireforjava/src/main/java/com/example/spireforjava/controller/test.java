package com.example.spireforjava.controller;

import com.example.spireforjava.utils.WordTemplate;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

/**
 * @author WenJi Luo
 * @date 2019/10/17 13:32
 * @Description
 */
public class test {


    public static void main(String[] args) throws Exception {
//        readZipToString();
//        System.out.println(Optional.ofNullable(null));
        Map map = new HashMap();
//        map.get("gg");

        map.put("KXJSF","本次共检测遗传位点(SNP位点)基因型6000-16500个，筛出有效位点基因型1416个，累积非父排除率CPE>99.999999%。 孕妇姓名胎儿和疑父二之间的遗传标记遵循孟德尔遗传定律，累计父权指数CPI=1.08e75。");
        Document document = new Document();
        document.loadFromFile("D:\\test\\testUtils.docx");

//        new WordTemplate(document).replaceTxt(map,true);
        new WordTemplate(document).replaceTagKXJS(map,true);

        //save the document
        document.saveToFile("D:\\test\\CreateTable.docx", FileFormat.Docx_2013);
    }
    @SuppressWarnings("unchecked")
    public static  String readZipToString() throws Exception {
        String path="D:\\test\\csvExcel2.zip";
        File file= new File("D:\\test\\20191017.zip");
//        URL url = new URL(path);
        String connet = "";
        try {
            //获得输入流，文件为zip格式，
            //支付宝提供
            //20886126836996110156_20160906.csv.zip内包含
            //20886126836996110156_20160906_业务明细.csv
            //20886126836996110156_20160906_业务明细(汇总).csv
            ZipInputStream in = new ZipInputStream(new FileInputStream(file));
            ZipEntry zipFile;
            ZipFile zip = new ZipFile(file, Charset.forName("GBK"));
            Enumeration<? extends ZipEntry> entrys = zip.entries();
            //循环读取zip中的cvs文件，无法使用jdk自带，因为文件名中有中文
            while (entrys.hasMoreElements()) {
                zipFile = entrys.nextElement();
                long size = zipFile.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zip.getInputStream(zipFile),"gbk"));
                    String line;
                    String file_connet = "";
                    /*
                     * 1.每一行用 | 隔开
                     * 2.每一个文件用 ; 隔开
                     */
                    while ((line = br.readLine()) != null) {
                        file_connet = file_connet + "|" + line;
                        System.out.println("内容:" + line);
                    }
                    connet = connet + file_connet + ";";
                    br.close();
                }
            }
            in.close();
        } catch (Exception e) {
            System.out.println("zip文件读取失敗" + e);
            return "false";
        }
        System.out.println(connet);
        return connet;
    }
//    public static void main(String[] args) throws Exception {
//        String path="D:\\test\\csvExcel2.zip";
//        ZipFile zf = new ZipFile(path);
//        Charset gbk = Charset.forName("GBK");
//        InputStream in = new BufferedInputStream(new FileInputStream(path));
//        ZipInputStream zin = new ZipInputStream(in,gbk);
//        ZipEntry ze;
//        while((ze = zin.getNextEntry()) != null){
//            if(ze.toString().endsWith("csv")){
//                BufferedReader br = new BufferedReader(
//                        new InputStreamReader(zf.getInputStream(ze),"GBK"));
//                String line;
//                while((line = br.readLine()) != null){
//                    System.out.println(line.toString());
//                }
//                br.close();
//            }
//            System.out.println();
//        }
//        zin.closeEntry();
//    }

    public static void readZipFile(String file) throws Exception {
        ZipFile zf = new ZipFile(file);
        InputStream in = new BufferedInputStream(new FileInputStream(file));
        Charset gbk = Charset.forName("GBK");
        ZipInputStream zin = new ZipInputStream(in,gbk);
        ZipEntry ze;
        while ((ze = zin.getNextEntry()) != null) {
            if (ze.isDirectory()) {
            } else {
                System.err.println("file - " + ze.getName() + " : "
                        + ze.getSize() + " bytes");
                long size = ze.getSize();
                if (size > 0) {
                    BufferedReader br = new BufferedReader(
                            new InputStreamReader(zf.getInputStream(ze),"gbk"));
                    String line;
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                    br.close();
                }
                System.out.println();
            }
        }
        zin.closeEntry();
    }

}
