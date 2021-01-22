package com.example.spireforjava.controller;

import com.example.spireforjava.utils.WordTemplate;
import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.TextSelection;
import com.spire.doc.fields.TextRange;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: ljw_spireforjava
 * @description:
 * @author: luojiwen
 * @create: 2019-12-09 11:21
 **/
public class TEST20191209 {

    public static void main(String[] args) throws IOException {
        getProperty();
    }

    /**
     * 拿到这个标签的值，然后替换
     */
    public static void getProperty() throws IOException {
        Map map = new HashMap();
//        map.get("gg");
        String ValueTest = "本次共检测遗传位点(SNP位点)基因型6000-16500个，筛出有效位点基因型1416个，累积非父排除率CPE>99.999999%。 孕妇姓名胎儿和疑父二之间的遗传标记遵循孟德尔遗传定律，累计父权指数CPI=1.08e75。";
        map.put("getProperty","本次共检测遗传位点(SNP位点)基因型6000-16500个，筛出有效位点基因型1416个，累积非父排除率CPE>99.999999%。 孕妇姓名胎儿和疑父二之间的遗传标记遵循孟德尔遗传定律，累计父权指数CPI=1.08e75。");
        Document document = new Document();
        document.loadFromFile("D:\\test\\20191209\\20191209.docx");

//        new WordTemplate(document).replaceTxt(map,true);
//        new WordTemplate(document).replaceTagTEST(map,true);
        TextSelection[] textSelections = document.findAllString("${getProperty", true, true);
        for (TextSelection selection : textSelections) {
            Paragraph paragraph = selection.getAsOneRange().getOwnerParagraph();
            TextRange range = selection.getAsOneRange();


            paragraph.setText(ValueTest);
//            TextRange txtRange3 = paragraph.appendText("我师傅加上");
//            txtRange3.getCharacterFormat().setFontSize(range.getCharacterFormat().getFontSize());
//            txtRange3.getCharacterFormat().setTextColor(range.getCharacterFormat().getTextColor());
//            txtRange3.getCharacterFormat().setFontSize(range.getCharacterFormat().getFontSize());
//            txtRange3.getCharacterFormat().setFontName(range.getCharacterFormat().getFontName());





        }
        //save the document
        document.saveToFile("D:\\test\\20191209\\20191209Gen.docx", FileFormat.Docx_2013);
    }
}
