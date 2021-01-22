package com.example.spireforjava.controller;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.HeaderFooter;
import com.spire.doc.Section;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.ShapeLineStyle;
import com.spire.doc.documents.ShapeType;
import com.spire.doc.fields.ShapeObject;
import com.spire.doc.*;
import com.spire.doc.documents.WatermarkLayout;
import java.awt.*;

/**
 * @program: myProject
 * @description: 水印
 * @author: luojiwen
 * @create: 2020-09-30 09:40
 **/
public class TEST20200930 {
    public static void main(String[] args) {
        demo3();
    }


    public static  void demo1(){
        Document document = new Document();
        document.loadFromFile("E:\\a\\20200330\\aaa.docx");
        Section section = document.getSections().get(0);
        TextWatermark txtWatermark = new TextWatermark();
        txtWatermark.setText("内部使用");
        txtWatermark.setFontSize(40);
        txtWatermark.setColor(Color.red);
        txtWatermark.setLayout(WatermarkLayout.Diagonal);
        section.getDocument().setWatermark(txtWatermark);
        document.saveToFile("E:\\a\\20200330\\result2.docx",FileFormat.Docx );
    }
    /**
     * @param
     * @return
     * @Author luojiwen
     * @Description Java 添加多行文字水印到 Word 文档
     * @Date 9:46 2020/9/30
     */
    public static void demo2() {
        //加载示例文档
        Document doc = new Document();
        doc.loadFromFile("E:\\a\\20200330\\DNA档案.docx");
        //添加艺术字并设置大小
        ShapeObject shape = new ShapeObject(doc, ShapeType.Text_Plain_Text);
        shape.setWidth(60);
        shape.setHeight(20);
        //设置艺术字文本内容、位置及样式
        shape.setVerticalPosition(30);
        shape.setHorizontalPosition(20);
        //旋转
        shape.setRotation(315);
        shape.getWordArt().setText("内部使用");
        shape.setFillColor(Color.red);
        shape.setLineStyle(ShapeLineStyle.Single);
        shape.setStrokeColor(new Color(192, 192, 192, 255));
        shape.setStrokeWeight(1);

        Section section;
        HeaderFooter header;
        for (int n = 0; n < doc.getSections().getCount(); n++) {
            section = doc.getSections().get(n);
            //获取section的页眉
            header = section.getHeadersFooters().getHeader();
            Paragraph paragraph1 = header.addParagraph();
            for (int i = 0; i < 4; i++) {
                //添加段落到页眉
                for (int j = 0; j < 3; j++) {
                    //复制艺术字并设置多行多列位置
                    shape = (ShapeObject) shape.deepClone();
                    shape.setVerticalPosition(50 + 150 * i);
                    shape.setHorizontalPosition(20 + 160 * j);
                    paragraph1.getChildObjects().add(shape);
                }
            }
        }
        //保存文档
        doc.saveToFile("E:\\a\\20200330\\result123.docx", FileFormat.Docx_2013);
    }

    /**
     * @param
     * @return
     * @Author luojiwen
     * @Description Java 添加多行文字水印到 Word 文档
     * @Date 9:46 2020/9/30
     */
    public static void demo3() {
        //加载示例文档
        Document doc = new Document();
        doc.loadFromFile("E:\\a\\20200330\\aaa.docx");
        //添加艺术字并设置大小
        ShapeObject shape = new ShapeObject(doc, ShapeType.Text_Plain_Text);
        shape.setWidth(60);
        shape.setHeight(20);
        //设置艺术字文本内容、位置及样式
        shape.setVerticalPosition(30);
        shape.setHorizontalPosition(20);
        shape.setRotation(315);
        shape.getWordArt().setText("mengyuan xiaohanhan");
        shape.setFillColor(Color.LIGHT_GRAY);
        shape.setLineStyle(ShapeLineStyle.Single);
        shape.setStrokeColor(new Color(192, 192, 192, 255));
        shape.setStrokeWeight(1);

        Section section;
        HeaderFooter header;
        for (int n = 0; n < doc.getSections().getCount(); n++) {
            section = doc.getSections().get(n);
            //获取section的页眉
            header = section.getHeadersFooters().getHeader();
            Paragraph paragraph1 = (Paragraph)header.getLastParagraph();
            for (int i = 0; i < 4; i++) {
                //添加段落到页眉
                for (int j = 0; j < 3; j++) {
                    //复制艺术字并设置多行多列位置
                    shape = (ShapeObject) shape.deepClone();
                    shape.setVerticalPosition(50 + 150 * i);
                    shape.setHorizontalPosition(20 + 160 * j);
                    paragraph1.getChildObjects().add(shape);
                }
            }
        }
        //保存文档
        doc.saveToFile("E:\\a\\20200330\\reqwe.docx", FileFormat.Docx_2013);
    }



}
