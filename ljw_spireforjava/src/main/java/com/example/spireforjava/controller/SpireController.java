package com.example.spireforjava.controller;

import com.example.spireforjava.utils.WordTemplate;
import com.spire.doc.*;
import com.spire.doc.collections.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.fields.TextRange;
import com.spire.doc.formatting.CellFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.*;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Documented;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author WenJi Luo
 * @date 2019/10/15 17:52
 * @Description
 */
@RestController
public class SpireController {


//    public static void main(String[] args) throws FileNotFoundException {
//
//        try {
//            createTableAndStaticHeadTag(null, null);
////            createTableAndStaticHead(null, null);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    //创建表格(表格动态创建)
    public static void createTable() throws IOException {
        Document document = new Document();
        document.loadFromFile("D:\\test\\testUtils.docx");
        TextSelection[] selections = document.findAllString("Tables1", false, true);
        //Add a section
        Section section = document.addSection();
        //Data used to create table
        String[] header = {"Name", "Capital", "Continent", "Area", "Population", "sdf", "zmy"};
        List<String[]> listSequence = new ArrayList<>();
        listSequence.add(new String[]{"Argentina", "123E10", "South America", "2777815", "32300003"});
        listSequence.add(new String[]{"Argentina", "123E-10", "South America", "2777815", "32300003"});
        new WordTemplate(document).createTable("Tables1", header, listSequence);

        new WordTemplate(document).createTable("Tables2", header, listSequence);
        TextSelection[] selections1 = document.findAllString("Tables2", false, true);
        //Add a section
        Section section1 = document.addSection();
        //Data used to create table
        String[] header1 = {"Name", "Capital", "Continent", "Area", "Population", "sdf", "zmy"};
        List<String[]> listSequence1 = new ArrayList<>();
        listSequence1.add(new String[]{"Argentina", "123E10", "South America", "2777815", "32300003"});
        listSequence1.add(new String[]{"Argentina", "123E-10", "South America", "2777815", "32300003"});
        new WordTemplate(document).createTable("Tables3", header1, listSequence1);

        //save the document
        document.saveToFile("D:\\test\\CreateTable.docx", FileFormat.Docx_2013);
    }

    //创建表格，备份
    public static void createTableBeiFen() {
        //Create a Document object
        Document document = new Document();
        document.loadFromFile("D:\\test\\testUtils.docx");
        //Add a section
        Section section = document.addSection();
        //Data used to create table
        String[] header = {"Name", "Capital", "Continent", "Area", "Population", "sdf", "zmy"};
        List<String[]> listSequence = new ArrayList<>();
        String[][] data =
                {
                        new String[]{"Argentina", "Buenos Aires", "South America", "2777815", "32300003"},
                        new String[]{"Bolivia", "La Paz", "South America", "1098575", "7300000"},
                        new String[]{"Brazil", "Brasilia", "South America", "8511196", "150400000"},
                        new String[]{"Canada", "Ottawa", "North America", "9976147", "26500000"},
                        new String[]{"Chile", "Santiago", "South America", "756943", "13200000"},
                        new String[]{"Colombia", "Bogota", "South America", "1138907", "33000000"},
                        new String[]{"Cuba", "Havana", "North America", "114524", "10600000"},
                        new String[]{"Ecuador", "Quito", "South America", "455502", "10600000"},
                        new String[]{"El Salvador", "San Salvador", "North America", "20865", "5300000"},
                        new String[]{"Guyana", "Georgetown", "South America", "214969", "800000"},
                        new String[]{"Jamaica", "Kingston", "North America", "11424", "2500000"},
                        new String[]{"Mexico", "Mexico City", "North America", "1967180", "88600000"},
                        new String[]{"Nicaragua", "Managua", "North America", "139000", "3900000"},
                        new String[]{"Paraguay", "Asuncion", "South America", "406576", "4660000"},
                        new String[]{"Peru", "Lima", "South America", "1285215", "21600000"},
                        new String[]{"America", "Washington", "North America", "9363130", "249200000"},
                        new String[]{"Uruguay", "Montevideo", "South America", "176140", "3002000"},
                        new String[]{"Venezuela", "Caracas", "South America", "912047", "19700000"}
                };

        //Add table
        Table table = section.addTable(true);
        table.resetCells(data.length + 1, header.length);
        //Set the first row as table header and add data
        TableRow row = table.getRows().get(0);
        row.isHeader(true);
        row.setHeight(20);
        row.setHeightType(TableRowHeightType.Exactly);
        row.getRowFormat().setBackColor(Color.gray);
        for (int i = 0; i < header.length; i++) {
            row.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
            Paragraph p = row.getCells().get(i).addParagraph();
            p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
            TextRange txtRange = p.appendText(header[i]);
            txtRange.getCharacterFormat().setBold(true);
            //楷体,Arial,黑体
            txtRange.getCharacterFormat().setFontName("Arial");
            txtRange.getCharacterFormat().setFontSize(14f);
        }
        //Add data to the rest of rows
        for (int r = 0; r < data.length; r++) {
            TableRow dataRow = table.getRows().get(r + 1);
            dataRow.setHeight(25);
            dataRow.setHeightType(TableRowHeightType.Exactly);
            dataRow.getRowFormat().setBackColor(Color.white);
            for (int c = 0; c < data[r].length; c++) {
                dataRow.getCells().get(c).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
                TextRange range = dataRow.getCells().get(c).addParagraph().appendText(data[r][c]);
                range.getOwnerParagraph().getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
                //楷体,Arial,黑体
                range.getCharacterFormat().setFontName("Arial");
                range.getCharacterFormat().setFontSize(14f);
            }
        }
        //Set background color for cells
        for (int j = 1; j < table.getRows().getCount(); j++) {
            if (j % 2 == 0) {
                TableRow row2 = table.getRows().get(j);
                for (int f = 0; f < row2.getCells().getCount(); f++) {
                    row2.getCells().get(f).getCellFormat().setBackColor(new Color(173, 216, 230));
                }
            }
        }
        //save the document
        document.saveToFile("D:\\test\\CreateTable.docx", FileFormat.Docx_2013);
    }

    //替换
//    public static void main(String[] args) {
//        //Load the Word document<font></font>
//        Document document = new Document("D:\\test\\CreateTable.docx");
//        //Replace the specified text with new text <font></font>
//        document.replace("Argentina", "修改的", true, true);
//        //Save the document<font></font>
//        document.saveToFile("D:\\test\\ReplaceAllMatchedText.docx", FileFormat.Docx_2013);
//    }

    //测试工具类
    public static void testUtils() throws IOException {
        long startTime = System.currentTimeMillis();
        //Load the Word document
        Document document = new Document("D:\\test\\testUtils.docx");
        Map<String, String> DataMap = new HashMap<String, String>();
        Map<String, String> imageMap = new HashMap<String, String>();
        imageMap.put("Name1", "E:\\images\\image\\99.jpg");
        String imgPath = "E:\\images\\image\\99.jpg";
        for (int i = 0; i < 5; i++) {
            DataMap.put("Name" + i, "value" + i);

        }
        //  new WordTemplate(document).replaceTag(DataMap,null,null,null);
        long endTime = System.currentTimeMillis();
        //Save the document
        document.saveToFile("D:\\test\\ReplaceUtils.docx", FileFormat.Docx_2013);
        float excTime = (float) (endTime - startTime) / 1000;
        System.out.println("执行时间为：" + excTime + "s");
    }

    //替换图片
    public static void replaceImages() throws IOException {
        String imgPath = "http://10.49.32.112/engine_fastdfs/group1/M00/00/2A/CjEgcF22SAiAWla8AAKGdvDwBAA639.jpg";
        //实例化Document类的对象，并加载测试文档
        Document doc = new Document();
        doc.loadFromFile("D:\\test\\testUtils.docx");
        //获取文档的所有section
        SectionCollection sectionCollection = doc.getSections();

        Map map = new HashMap();
        map.put("Google", "http://10.49.32.112/engine_fastdfs/group1/M00/00/2A/CjEgcF22U6qAQ4ZzAAAuIA3Zr0s043.jpg");
        WordTemplate wordTemplate = new WordTemplate(doc);
        //wordTemplate.replaceTag(null,map,null,null);
//        //查找文档中的指定文本内容
//        TextSelection[] selections = doc.findAllString("Google", true, true);
//        int index = 0;
//        TextRange range = null;
//
//        //遍历文档的所有section
//       for (TextSelection selection:selections){
//           DocPicture pic = new DocPicture(doc);
//           InputStream inputStream = new FileInputStream(imgPath);
//           pic.loadImage(inputStream);
//           range = selection.getAsOneRange();
//           index = range.getOwnerParagraph().getChildObjects().indexOf(range);
//           range.getOwnerParagraph().getChildObjects().insert(index, pic);
//           range.getOwnerParagraph().getChildObjects().remove(range);
//       }

        //保存文档
        doc.saveToFile("D:\\test\\result.docx", FileFormat.Docx);
    }

    //指定标签创建表格
    public static void createTableZhiDing(String[] header, List<String[]> listSequence) {
        Document document = new Document();
        document.loadFromFile("D:\\test\\testUtils.docx");
        //Data used to create table
        //查找关键字符串文本
        SectionCollection sectionCollection = document.getSections();
        TextSelection[] selections = document.findAllString("Tables", true, true);

        //遍历文档的所有section
        for (TextSelection selection : selections) {
            //获取关键字符串所在段落的索引
            TextRange range = selection.getAsOneRange();
            Paragraph paragraph = range.getOwnerParagraph();
            Body body = paragraph.ownerTextBody();
            int index = body.getChildObjects().indexOf(paragraph);

            //Add a section
            Section section = document.addSection();
            //Data used to create table
            //Add table
            Table table = section.addTable(true);
            table.resetCells(listSequence.size() + 1, header.length);
            //Set the first row as table header and add data
            TableRow row = table.getRows().get(0);
            row.isHeader(true);
            row.setHeightType(TableRowHeightType.Auto);
            row.setHeightType(TableRowHeightType.Exactly);
            row.getRowFormat().setBackColor(Color.gray);
            for (int i = 0; i < header.length; i++) {
                row.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
                Paragraph p = row.getCells().get(i).addParagraph();
                p.getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
                TextRange txtRange = p.appendText(header[i]);
                txtRange.getCharacterFormat().setBold(true);
                //楷体,Arial,黑体
                txtRange.getCharacterFormat().setFontName("Arial");
                txtRange.getCharacterFormat().setFontSize(10f);
            }
            //Add data to the rest of rows
            for (int r = 0; r < listSequence.size(); r++) {
                TableRow dataRow = table.getRows().get(r + 1);
                dataRow.setHeightType(TableRowHeightType.Auto);
                dataRow.getRowFormat().setBackColor(Color.white);
                for (int c = 0; c < listSequence.get(r).length; c++) {
                    dataRow.getCells().get(c).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
                    dataRow.setHeightType(TableRowHeightType.Auto);
                    TextRange range1 = dataRow.getCells().get(c).addParagraph().appendText(listSequence.get(r)[c]);
                    range1.getOwnerParagraph().getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
                    //楷体,Arial,黑体
                    range1.getCharacterFormat().setFontName("Arial");
                    range1.getCharacterFormat().setFontSize(10f);
                }
            }
            //Set background color for cells
            for (int j = 1; j < table.getRows().getCount(); j++) {
                if (j % 2 == 0) {
                    TableRow row2 = table.getRows().get(j);
                    for (int f = 0; f < row2.getCells().getCount(); f++) {
                        row2.getCells().get(f).getCellFormat().setBackColor(new Color(173, 216, 230));
                    }
                }
            }
            //移除段落，插入表格
            body.getChildObjects().remove(paragraph);
            body.getChildObjects().insert(index, table);

        }
        //save the document
        document.saveToFile("D:\\test\\CreateTable.docx", FileFormat.Docx_2013);

    }

    //创建表格，在指定的表头下面，动态创建
    public static void createTableAndStaticHead(String header, List<String[]> listSequence) {
        header = "LuojiwenChenghaifeng";

        listSequence = new ArrayList<String[]>();
        listSequence.add(new String[]{"Argentina", "123E10"});
        listSequence.add(new String[]{"Argentina", "123E-10"});
        //createTableAndStaticHead
        Document document = new Document("D:\\test\\ReplaceAllMatchedText.docx");
        SectionCollection sectionCollections = document.getSections();

        //获得节点sections
        for (int sectionItem = 0; sectionItem < sectionCollections.getCount(); sectionItem++) {
            TableCollection tableCollection = sectionCollections.get(sectionItem).getTables();
            //获得所有的表格
            for (int tableItem = 0; tableItem < tableCollection.getCount(); tableItem++) {
                Table table = tableCollection.get(tableItem);
                RowCollection rowCollection = table.getRows();
                //这里设置位1，只要拿到表格的表头
                for (int rowItem = 0; rowItem < 1; rowItem++) {
                    TableRow tableRow = rowCollection.get(rowItem);
                    CellCollection cellCollection = tableRow.getCells();
                    String tableHead = "";
                    for (int cellItem = 0; cellItem < cellCollection.getCount(); cellItem++) {
                        ParagraphCollection paragraphCollection = table.get(rowItem, cellItem).getParagraphs();
//                        System.out.print("第"+(tableItem+1)+"个表格头内容：");
                        for (int paragraphItem = 0; paragraphItem < paragraphCollection.getCount(); paragraphItem++) {
                            Paragraph paragraph = paragraphCollection.get(paragraphItem);
                            String cellString = paragraph.getText();
//                            System.out.println(cellString);
                            tableHead = tableHead + cellString;
                        }
                    }
//                    System.out.println();
//                    System.out.println("-->"+tableHead);
                    if (tableHead.contains(header)) {
                        for (int listSequenceItem = 0; listSequenceItem < listSequence.size(); listSequenceItem++) {
                            //创建一行。并得到这个单元
                            CellCollection cellCollection1 = table.addRow(true).getCells();
                            for (int cell1Item = 0; cell1Item < cellCollection1.getCount(); cell1Item++) {
                                Paragraph paragraph = cellCollection1.get(cell1Item).addParagraph();
                                String CellValue = listSequence.get(listSequenceItem)[cell1Item];
                                TextRange txtRange = paragraph.appendText(CellValue);
                                //楷体,Arial,黑体
                                txtRange.getCharacterFormat().setFontName("Arial");
                                txtRange.getCharacterFormat().setFontSize(10f);
                            }
                        }
                    }
                }
            }
        }

        //保存文档
        document.saveToFile("D:\\test\\staticTables.docx", FileFormat.Docx_2013);

    }

    //创建表格，在指定的表头下面的第二行，动态创建
    public static void createTableAndStaticHeadTag(String header, List<String[]> listSequence) {
        header = "${luojiwen}${chenhaifeng}";

        listSequence = new ArrayList<String[]>();
        listSequence.add(new String[]{"Argentina", "123E10"});
        listSequence.add(new String[]{"Argentina", "123E-10"});
        //createTableAndStaticHead
        Document document = new Document("D:\\test\\ReplaceAllMatchedText.docx");
        SectionCollection sectionCollections = document.getSections();

        //获得节点sections
        for (int sectionItem = 0; sectionItem < sectionCollections.getCount(); sectionItem++) {
            TableCollection tableCollection = sectionCollections.get(sectionItem).getTables();
            //获得所有的表格
            for (int tableItem = 0; tableItem < tableCollection.getCount(); tableItem++) {
                Table table = tableCollection.get(tableItem);
                RowCollection rowCollection = table.getRows();
                //这里设置位1，只要拿到表格的表头
                for (int rowItem = 1; rowItem <= 1; rowItem++) {
                    TableRow tableRow = rowCollection.get(rowItem);

                    CellCollection cellCollection = tableRow.getCells();
                    String tableHead = "";
                    for (int cellItem = 0; cellItem < cellCollection.getCount(); cellItem++) {
                        ParagraphCollection paragraphCollection = table.get(rowItem, cellItem).getParagraphs();
//                        System.out.print("第"+(tableItem+1)+"个表格头内容：");
                        for (int paragraphItem = 0; paragraphItem < paragraphCollection.getCount(); paragraphItem++) {
                            Paragraph paragraph = paragraphCollection.get(paragraphItem);
                            String cellString = paragraph.getText();
//                            System.out.println(cellString);
                            tableHead = tableHead + cellString;
                        }
                    }
//                    System.out.println();
//                    System.out.println("-->"+tableHead);
                    if (tableHead.contains(header)) {
                        table.getRows().removeAt(1);
                        for (int listSequenceItem = 0; listSequenceItem < listSequence.size(); listSequenceItem++) {
                            //创建一行。并得到这个单元
                            TableRow tableRowCreate = table.addRow(true);
                            tableRowCreate.getRowFormat().setHorizontalAlignment(table.getRows().get(0).getRowFormat().getHorizontalAlignment());
                            CellCollection cellCollection1 = tableRowCreate.getCells();

                            for (int cell1Item = 0; cell1Item < cellCollection1.getCount(); cell1Item++) {
                                Paragraph paragraph = cellCollection1.get(cell1Item).addParagraph();
                                String CellValue = listSequence.get(listSequenceItem)[cell1Item];
                                TextRange txtRange = paragraph.appendText(CellValue);

                                paragraph.getFormat().setHorizontalAlignment(table.getRows().get(0).getCells().get(0).getParagraphs().get(0).getFormat().getHorizontalAlignment());
                                //楷体,Arial,黑体
                                txtRange.getCharacterFormat().setFontName("Arial");
                                txtRange.getCharacterFormat().setFontSize(10f);
                            }
                        }
                        //设置表格的样式
                        for (int j = 1; j < table.getRows().getCount(); j++) {
                            TableRow row2 = table.getRows().get(j);
                            for (int f = 0; f < row2.getCells().getCount(); f++) {
                                row2.getCells().get(f).getCellFormat().getBorders().getLeft().setLineWidth(0f);
                                row2.getCells().get(f).getCellFormat().getBorders().getRight().setLineWidth(0f);
                                row2.getCells().get(f).getCellFormat().getBorders().getBottom().setLineWidth(0f);
                                row2.getCells().get(f).getCellFormat().getBorders().getTop().setLineWidth(0f);

                                if (j == table.getRows().getCount() - 1) {
                                    row2.getCells().get(f).getCellFormat().getBorders().getBottom().setLineWidth(1f);
                                }
                            }
                        }
                    }
                }
            }
        }

        //保存文档
        document.saveToFile("D:\\test\\staticTables.docx", FileFormat.Docx_2013);

    }


    public void testss() {
        //实例化Document类的对象，并加载测试文档
        Document doc = new Document();
        doc.loadFromFile("test.docx");

        //查找关键字符串文本
        Section section = doc.getSections().get(0);
        TextSelection selection = doc.findString("参考附录", true, true);

        //获取关键字符串所在段落的索引
        TextRange range = selection.getAsOneRange();
        Paragraph paragraph = range.getOwnerParagraph();
        Body body = paragraph.ownerTextBody();
        int index = body.getChildObjects().indexOf(paragraph);

        //添加一个两行三列的表格
        Table table = section.addTable(true);
        table.resetCells(2, 3);


        //移除段落，插入表格
        body.getChildObjects().remove(paragraph);
        body.getChildObjects().insert(index, table);

        //保存文档
        doc.saveToFile("result.doc", FileFormat.Doc);
    }

    @RequestMapping("/test")
    public String testController() throws Exception {
        return test.readZipToString();
    }

    @RequestMapping("/tttt")
    public String ttt() throws Exception {

        return "qwe哈哈";
    }

    public static void getScientific(String input) {
        //科学计数法正则表达式
        String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
        Pattern pattern = Pattern.compile(regx);
        Matcher m = pattern.matcher(input);
        while (m.find()) {
            System.out.println(m.groupCount());
//            System.out.println(m.());
//            System.out.println(m.group(2));
            System.out.println(m.find());
        }
    }


//    public static void main(String[] args) {
//
//        //科学计数法正则表达式"^((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))$"
//        String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
//        String scienceStr = "综上检验结果分析，罗继文 F 在 D7S820、 D21S11、 D1S1656、 FGA、 TH01、 D19S433、 D6S1043、 D2S1338 等基因座不能提供给罗继文 S 必需的等位基因。依据 GB/T 37223-2018《亲权鉴定技术规范》进行计算，累积亲权数为 3.3195E-23，小于 1.0E-4。本文内容由互联网用户自发贡献，版权归作者所有，本社区不拥有所有权，也不承担相关法律责任。如果您发现本社区中有涉嫌抄袭的内容";
//
//        Pattern pattern = Pattern.compile(regx);
//        Matcher matcher = pattern.matcher(scienceStr);
//        String[] strings = scienceStr.split(regx);
////        int count=0;
////        while (matcher.find()) {
////            System.out.println(matcher.group());
////            count++;
////            scienceStr = matcher.replaceFirst("123");
////            matcher = pattern.matcher(scienceStr);
////            System.out.println(scienceStr);
////            if(count ==matcher.groupCount()){
////                break;
////            }
////        }
//    }


    /*
    split分隔
     */
//    public static void main(String[] args) {
//
//        String aa = "a,a,a,a,a,a";
//        System.out.println("正常分隔==》" + aa.split(",").length);
//        System.out.println("只分隔成两个==》" + aa.split(",", 0).length);
//
//        Document document = new Document();
//        document.loadFromFile("D:\\test\\1.docx");
//        String scienceStr = "综上检验结果分析，罗继文 F 在 D7S820、 D21S11、 D1S1656、 FGA、 TH01、 D19S433、 D6S1043、 D2S1338 等基因座不能提供给罗继文 S 必需的等位基因。依据 GB/T 37223-2018《亲权鉴定技术规范》进行计算，累积亲权数为 3.3195E-23，小于 1.0E-4。小于 1.0E-4。wohenhao ";
//
//        TextSelection[] textSelections = document.findAllString("${test}", true, true);
//        boolean flag = true;
//
//        for (TextSelection selection : textSelections) {
//            String scienceStrElastic = scienceStr;
//            String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
//            Pattern pattern = Pattern.compile(regx);
//            Matcher matcher = pattern.matcher(scienceStrElastic);
//            Paragraph paragraph = selection.getAsOneRange().getOwnerParagraph();
//            if (flag) {
//                //为了将标签赋值为空
//                paragraph.setText("");
//            }
//            //处理数据和spire组装
//            for (int matcherItem = 0; matcherItem < matcher.groupCount(); matcherItem++) {
//                //如果字符串中能符合正则的匹配
//                if (matcher.find()) {
//                    //拿到科学计数法的字符串
//                    String groupRex = matcher.group();
//                    //只分割成两个数组，如果他还能分割，留到下一次
//                    String[] splitStr = scienceStrElastic.split(groupRex, 2);
//                    String[] scienceSplit = groupRex.split("E");
//                    //科学计数法乘法前面的部分，如3.3195E-23，即3.3195
//                    String PrefixStr = scienceSplit[0] + "×10";
//                    //上标，即科学计数法的左上角
//                    String SuffixStr = scienceSplit[1];
//                    paragraph.appendText(splitStr[0] + PrefixStr);
//                    paragraph.appendText(SuffixStr).getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
//                    scienceStrElastic = splitStr[1];
//                    matcher = pattern.matcher(scienceStrElastic);
//                    //拼接上，由于限制了只分割成两个数组遗留下来的问题，拼上最后的尾巴
//                    if (matcherItem == matcher.groupCount() - 1) {
//                        paragraph.appendText(scienceStrElastic);
//                    }
//                } else {
//                    System.out.println("不符合正则表达式的内容");
//                }
//
//            }
//        }
//
//
//        document.saveToFile("D:\\test\\2.docx", FileFormat.Docx);
//
//        //科学计数法正则表达式
//        String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
//
//    }

    /*
substring
 */
//    public static void main(String[] args) {
//
//        Document document = new Document();
//        document.loadFromFile("D:\\test\\1.docx");
//        String scienceStr = "综上检验结果分析，罗继文 F 在 D7S820、 D21S11、 D1S1656、 FGA、 TH01、 D19S433、 D6S1043、 D2S1338 等基因座不能提供给罗继文 S 必需的等位基因。依据 GB/T 37223-2018《亲权鉴定技术规范》进行计算，累积亲权数为 3.3195E-23，小于 1.0E-4。";
//
//        TextSelection[] textSelections = document.findAllString("${test}", true, true);
//        boolean flag = true;
//
//        for (TextSelection selection : textSelections) {
//            String scienceStrElastic = scienceStr;
//            String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
//            Pattern pattern = Pattern.compile(regx);
//            Matcher matcher = pattern.matcher(scienceStrElastic);
//            Paragraph paragraph = selection.getAsOneRange().getOwnerParagraph();
//            if (flag) {
//                //为了将标签赋值为空
//                paragraph.setText("");
//            }
//            //处理数据和spire组装
//            for (int matcherItem = 0; matcherItem < matcher.groupCount(); matcherItem++) {
//                //如果字符串中能符合正则的匹配
//                if (matcher.find()) {
//                    //拿到科学计数法的字符串
//                    String groupRex = matcher.group();
//                    //只分割成两个数组，如果他还能分割，留到下一次
//                    String[] splitStr = scienceStrElastic.split(groupRex, 2);
//                    String[] scienceSplit = groupRex.split("E");
//                    //科学计数法乘法前面的部分，如3.3195E-23，即3.3195
//                    String PrefixStr = scienceSplit[0] + "×10";
//                    //上标，即科学计数法的左上角
//                    String SuffixStr = scienceSplit[1];
//                    paragraph.appendText(splitStr[0] + PrefixStr);
//                    paragraph.appendText(SuffixStr).getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
//                    scienceStrElastic = splitStr[1];
//                    matcher = pattern.matcher(scienceStrElastic);
//                    //拼接上，由于限制了只分割成两个数组遗留下来的问题，拼上最后的尾巴
//                    if (matcherItem == matcher.groupCount() - 1) {
//                        paragraph.appendText(scienceStrElastic);
//                    }
//                } else {
//                    System.out.println("不符合正则表达式的内容");
//                }
//
//            }
//        }
//
//
//        document.saveToFile("D:\\test\\2.docx", FileFormat.Docx);
//
//        //科学计数法正则表达式
//        String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
//
//    }
    public static void main(String[] args) throws IOException {
        wordToPad();
    }

    public static void wordToPad() throws IOException {
        String imgPath = "http://10.49.32.112/engine_fastdfs/group1/M00/00/2A/CjEgcF22SAiAWla8AAKGdvDwBAA639.jpg";
        String wordUrl = "http://10.49.32.112/engine_fastdfs/group1/M00/00/4C/CjEgcF4YFXOAXI2QAAFGhM3L2eI98.docx";
        //实例化Document类的对象，并加载测试文档
        URL url = new  URL(wordUrl);
        Document doc = new Document(url.openStream());

        doc.saveToFile("D:\\test\\1.pdf",FileFormat.PDF);
    }
}
