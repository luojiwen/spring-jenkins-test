package com.example.spireforjava.utils;

import com.spire.doc.*;
import com.spire.doc.collections.*;
import com.spire.doc.documents.*;
import com.spire.doc.fields.DocPicture;
import com.spire.doc.fields.TextRange;

import java.awt.*;
import java.io.*;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 仅支持对docx文件的文本及表格中的内容进行替换
 * 模板仅支持 ${key} 标签
 *
 * @author
 */
public class WordTemplate {

    private Document document;

    /**
     * 初始化模板内容
     *
     * @param document1 模板的读取流(docx文件)
     * @throws IOException
     */
    public WordTemplate(Document document1) throws IOException {
        document = document1;
    }

    /**
     * 功能描述: 替换模板中的标签为实际的内容
     *
     * @param map,mapImg,
     * @return
     * @author WenJi Luo
     * @date 2019/10/28 16:16
     */
    public void replaceTag(String createTable, Map<String, String> map, Map<String, String> mapImg, String[] header, List<String[]> listSequence) throws IOException {
        if (map != null && !map.isEmpty()) {
            //用文本替换指定位置标签
//            replaceTxt(map);
        }

        if (mapImg != null && !mapImg.isEmpty()) {
            //用图片替换指定位置标签
            replaceImages(mapImg);
        }

        if (header != null && listSequence != null) {
            if (!listSequence.isEmpty()) {
                createTable(createTable, header, listSequence);
            }
        }
    }

    /**
     * 功能描述: 文本替换标签
     *
     * @param TxTMap
     * @return void
     * @author WenJi Luo
     * @date 2019/10/28 16:13
     */
    public void replaceTxt(Map<String, String> TxTMap, boolean isRegEx) {
        isRegEx =true;
        for (Map.Entry<String, String> entry : TxTMap.entrySet()) {
            //自定义一个标签替换，默认是开启
            if (isRegEx) {
                String scienceStr = entry.getValue();
                TextSelection[] textSelections = document.findAllString("${" + entry.getKey() + "}", true, true);
                boolean flag = true;
                for (TextSelection selection : textSelections) {
                    String scienceStrElastic = scienceStr;
                    String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
                    Pattern pattern = Pattern.compile(regx);
                    Matcher matcher = pattern.matcher(scienceStrElastic);
                    Paragraph paragraph = selection.getAsOneRange().getOwnerParagraph();
                    if (flag) {
                        //为了将标签赋值为空
                        paragraph.setText("");
                    }
                    //处理数据和spire组装
                    for (int matcherItem = 0; matcherItem < matcher.groupCount(); matcherItem++) {
                        //如果字符串中能符合正则的匹配
                        if (matcher.find()) {
                            //拿到科学计数法的字符串
                            String groupRex = matcher.group();
                            //只分割成两个数组，如果他还能分割，留到下一次
                            String[] splitStr = scienceStrElastic.split(groupRex, 2);
                            String[] scienceSplit = groupRex.split("E");
                            //科学计数法乘法前面的部分，如3.3195E-23，即3.3195
                            String prefixStr = scienceSplit[0] + "×10";
                            //上标，即科学计数法的左上角
                            String suffixStr = scienceSplit[1];
                            paragraph.appendText(splitStr[0] + prefixStr);
                            paragraph.appendText(suffixStr).getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
                            scienceStrElastic = splitStr[1];
                            matcher = pattern.matcher(scienceStrElastic);
                            //拼接上，由于限制了只分割成两个数组遗留下来的问题，拼上最后的尾巴
                            if(matcherItem==matcher.groupCount()-1){
                                paragraph.appendText(scienceStrElastic);
                            }
                        } else {
                            System.out.println("不符合正则表达式的内容");
                        }
                    }
                }
            } else {
                document.replace(entry.getKey(), entry.getValue(), true, true);
            }
        }
    }


    /**
     * 功能描述: 文本替换标签
     *
     * @param TxTMap
     * @return void
     * @author WenJi Luo
     * @date 2019/10/28 16:13
     */
    public void replaceTagKXJS(Map<String, String> TxTMap, boolean isRegEx) {
        for (Map.Entry<String, String> entry : TxTMap.entrySet()) {
            //自定义一个标签替换，默认是开启
            if (isRegEx) {
                String scienceStr = entry.getValue();
                TextSelection[] textSelections = document.findAllString("${" + entry.getKey() + "}", true, true);
                boolean flag = true;
                for (TextSelection selection : textSelections) {
                    String scienceStrElastic = scienceStr;
                    String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
                    Pattern pattern = Pattern.compile(regx);
                    Matcher matcher = pattern.matcher(scienceStrElastic);
                    Paragraph paragraph = selection.getAsOneRange().getOwnerParagraph();
                    if (flag) {
                        //为了将标签赋值为空
                        paragraph.setText("");
                    }
                    //处理数据和spire组装
                    for (int matcherItem = 0; matcherItem < matcher.groupCount(); matcherItem++) {
                        //如果字符串中能符合正则的匹配
                        if (matcher.find()) {
                            //拿到科学计数法的字符串
                            String groupRex = matcher.group();
                            //只分割成两个数组，如果他还能分割，留到下一次
                            String[] splitStr = scienceStrElastic.split(groupRex, 2);
                            String[] scienceSplit = groupRex.split("[Ee]");
                            //科学计数法乘法前面的部分，如3.3195E-23，即3.3195
                            String prefixStr = scienceSplit[0] + "×10";
                            //上标，即科学计数法的左上角
                            String suffixStr = scienceSplit[1];
                            TextRange txtRange1 = paragraph.appendText(splitStr[0] + prefixStr);
                            txtRange1.getCharacterFormat().setFontName("仿宋");
                            txtRange1.getCharacterFormat().setFontSize(10f);
                            TextRange txtRange2 = paragraph.appendText(suffixStr);
                            txtRange2.getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
                            txtRange2.getCharacterFormat().setFontName("仿宋");
                            txtRange2.getCharacterFormat().setFontSize(10f);
                            scienceStrElastic = splitStr[1];
                            matcher = pattern.matcher(scienceStrElastic);

                        } else {
                            System.out.println("不符合正则表达式的内容");
                        }
                        //拼接上，由于限制了只分割成两个数组遗留下来的问题，拼上最后的尾巴
                        if(matcherItem==matcher.groupCount()-1){
                            TextRange txtRange3  = paragraph.appendText(scienceStrElastic);
                            txtRange3.getCharacterFormat().setFontName("仿宋");
                            txtRange3.getCharacterFormat().setFontSize(10f);
                            txtRange3.getCharacterFormat().setTextColor(Color.red);
                        }
                    }

//					System.out.println(paragraph.getText());
//					range1.getCharacterFormat().setFontName("仿宋");
//					range1.getCharacterFormat().setFontSize(14f);
//					range1.getCharacterFormat().setTextColor(Color.orange);
                }
            } else {
                document.replace("${"+entry.getKey()+"}", entry.getValue(), true, true);
            }
        }
    }
    /**
     * 功能描述: 动态生成表格替换指定位置标签,只能生成一个表格,可以支持科学计数法（如果扫描到前面是数字，中间是E，后面是数字）
     *
     * @param header,listSequence
     * @return
     * @author WenJi Luo
     * @date 2019/10/28 16:15
     */
    public void createTable(String matchString, String[] header, List<String[]> listSequence) {

        SectionCollection sectionCollection = document.getSections();
        //查找关键字符串文本
        TextSelection[] selections = document.findAllString(matchString, true, true);

        //遍历文档的所有section
        for (TextSelection selection : selections) {
            //获取关键字符串所在段落的索引
            TextRange range = selection.getAsOneRange();
            Paragraph paragraph = range.getOwnerParagraph();

            Body body = paragraph.ownerTextBody();
            int index = body.getChildObjects().indexOf(paragraph);

            if (listSequence == null || listSequence.size() == 0) {
                body.getChildObjects().remove(paragraph);
                break;
            }
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
//            row.getRowFormat().setBackColor(Color.gray);
            for (int i = 0; i < header.length; i++) {
                row.getCells().get(i).getCellFormat().setVerticalAlignment(VerticalAlignment.Middle);
                row.getCells().get(i).getCellFormat().getBorders().getLeft().setLineWidth(0f);
                row.getCells().get(i).getCellFormat().getBorders().getRight().setLineWidth(0f);
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

                    TextRange range1 = null;
                    Paragraph scienceParagraph = dataRow.getCells().get(c).addParagraph();
                    if (listSequence.get(r)[c].matches("(-?[0-9]+\\.?[0-9]*[e,E]?[-,+]?-?[0-9]+\\.?[0-9]*)")) {
                        String PrefixStr = listSequence.get(r)[c].substring(0, listSequence.get(r)[c].indexOf("E"));
                        String SuffixStr = listSequence.get(r)[c].substring(listSequence.get(r)[c].indexOf("E") + 1);
                        scienceParagraph.appendText(PrefixStr);
                        scienceParagraph.appendText(SuffixStr).getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
                        //为了设置下面字体风格和大小,所以用这个接收并设置""
                        range1 = scienceParagraph.appendText("");
                    } else {
                        range1 = dataRow.getCells().get(c).addParagraph().appendText(listSequence.get(r)[c]);
                    }
                    range1.getOwnerParagraph().getFormat().setHorizontalAlignment(HorizontalAlignment.Center);
                    //楷体,Arial,黑体
                    range1.getCharacterFormat().setFontName(range.getCharacterFormat().getFontName());
                    range1.getCharacterFormat().setFontSize(range.getCharacterFormat().getFontSize());
                }
            }
            //Set background color for cells
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

//				if (j % 2 == 0) {
//					TableRow row2 = table.getRows().get(j);
//					for (int f = 0; f < row2.getCells().getCount(); f++) {
//						row2.getCells().get(f).getCellFormat().setBackColor(new Color(173, 216, 230));
//						row2.getCells().get(f).getCellFormat().getBorders().getLeft().setLineWidth(0f);
//						row2.getCells().get(f).getCellFormat().getBorders().getRight().setLineWidth(0f);
//					}
//				}

            }
            //移除段落，插入表格
            body.getChildObjects().remove(paragraph);
            body.getChildObjects().insert(index, table);
            //每次表格生成，在表格的上方加一个换行
//            paragraph.setText("");
//            body.getChildObjects().insert(index,paragraph);
            Paragraph pgraph = new Paragraph(document);
            pgraph.appendBreak(BreakType.Line_Break);
            body.getChildObjects().insert(index, pgraph);
        }
    }

    /**
     * 功能描述: 用图片替换标签
     *
     * @param mapImg
     * @return
     * @author WenJi Luo
     * @date 2019/10/28 16:15
     */
    private void replaceImages(Map<String, String> mapImg) throws IOException {
        for (Map.Entry<String, String> entry : mapImg.entrySet()) {
            //查找文档中的指定文本内容(params1:要查找的文本字符串。param2：是否不区分大小写，true区分大小写。param3：整个字符串，是否开启模糊查找，true不开启)
            TextSelection[] selections = document.findAllString(entry.getKey(), true, true);
            int index = 0;
            TextRange range = null;
            //遍历文档的所有section
            for (TextSelection selection : selections) {
                DocPicture pic = new DocPicture(document);
                pic.setHeightScale(50f);
                pic.setWidthScale(50f);
                pic.setHeight(50f);
                pic.setWidth(50f);

//				InputStream inputStream = new FileInputStream(entry.getValue());
                URL url = new URL(entry.getValue());
                pic.loadImage(url.openConnection().getInputStream());
                range = selection.getAsOneRange();
                index = range.getOwnerParagraph().getChildObjects().indexOf(range);
                range.getOwnerParagraph().getChildObjects().insert(index, pic);
                range.getOwnerParagraph().getChildObjects().remove(range);
            }
        }

    }

    /**
     * 功能描述: 创建表格，在指定的表头下面，动态创建
     *
     * @param header,listSequence
     * @return
     * @author WenJi Luo
     * @date 2019/10/28 16:15
     */
    public void createTableAndStaticHead(String header, List<String[]> listSequence) {
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
                            CellCollection cellCollection1 = table.addRow(true).getCells();
                            for (int cell1Item = 0; cell1Item < cellCollection1.getCount(); cell1Item++) {
                                Paragraph paragraph = cellCollection1.get(cell1Item).addParagraph();
                                String cellValue = listSequence.get(listSequenceItem)[cell1Item];
                                TextRange txtRange = null;
                                if (cellValue.matches("(-?[0-9]+\\.?[0-9]*[e,E]?[-,+]?-?[0-9]+\\.?[0-9]*)")) {
                                    String prefixStr = cellValue.substring(0, cellValue.indexOf("E"));
                                    String suffixStr = cellValue.substring(cellValue.indexOf("E") + 1);
                                    paragraph.appendText(prefixStr);
                                    paragraph.appendText(suffixStr).getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
                                    //为了设置下面字体风格和大小,所以用这个接收并设置""
                                    txtRange = paragraph.appendText("");
                                } else {
                                    txtRange = paragraph.appendText(cellValue);
                                }
                                //楷体,Arial,黑体
                                txtRange.getCharacterFormat().setFontName("Arial");
                                txtRange.getCharacterFormat().setFontSize(10f);
                            }
                        }
                        //设置表格的样式
                        for (int j = 0; j < table.getRows().getCount(); j++) {
                            TableRow row2 = table.getRows().get(j);
                            for (int f = 0; f < row2.getCells().getCount(); f++) {
                                row2.getCells().get(f).getCellFormat().getBorders().getLeft().setLineWidth(0f);
                                row2.getCells().get(f).getCellFormat().getBorders().getRight().setLineWidth(0f);
                                row2.getCells().get(f).getCellFormat().getBorders().getBottom().setLineWidth(0f);
                                row2.getCells().get(f).getCellFormat().getBorders().getTop().setLineWidth(0f);

                                if (j == table.getRows().getCount() - 1) {
                                    row2.getCells().get(f).getCellFormat().getBorders().getBottom().setLineWidth(1f);
                                }
                                if (j == 0) {
                                    row2.getCells().get(f).getCellFormat().getBorders().getTop().setLineWidth(1f);
                                    row2.getCells().get(f).getCellFormat().getBorders().getBottom().setLineWidth(1f);
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    /**
     * 将处理后的内容写入到输出流中
     *
     * @param filePath 输出流
     * @throws IOException
     */
    public void write(String filePath) throws IOException {
        //保存文档
        document.saveToFile(filePath, FileFormat.Docx_2013);
    }







    /**
     * @Author luojiwen
     * @Description 测试用的，用完会删除
     * @Date 11:27 2019/12/9
     * @Param
     * @return
     */
    public void replaceTagTEST(Map<String, String> TxTMap, boolean isRegEx) {
        for (Map.Entry<String, String> entry : TxTMap.entrySet()) {
            //自定义一个标签替换，默认是开启
            if (isRegEx) {
                String scienceStr = entry.getValue();
                TextSelection[] textSelections = document.findAllString("${" + entry.getKey() + "}", true, true);
                boolean flag = true;
                for (TextSelection selection : textSelections) {
                    String scienceStrElastic = scienceStr;
                    String regx = "((-?\\d+.?\\d*)[Ee]{1}(-?\\d+))";
                    Pattern pattern = Pattern.compile(regx);
                    Matcher matcher = pattern.matcher(scienceStrElastic);
                    Paragraph paragraph = selection.getAsOneRange().getOwnerParagraph();
                    if (flag) {
                        //为了将标签赋值为空
                        paragraph.setText("");
                    }
                    //处理数据和spire组装
                    for (int matcherItem = 0; matcherItem < matcher.groupCount(); matcherItem++) {
                        //如果字符串中能符合正则的匹配
                        if (matcher.find()) {
                            //拿到科学计数法的字符串
                            String groupRex = matcher.group();
                            //只分割成两个数组，如果他还能分割，留到下一次
                            String[] splitStr = scienceStrElastic.split(groupRex, 2);
                            String[] scienceSplit = groupRex.split("[Ee]");
                            //科学计数法乘法前面的部分，如3.3195E-23，即3.3195
                            String prefixStr = scienceSplit[0] + "×10";
                            //上标，即科学计数法的左上角
                            String suffixStr = scienceSplit[1];
                            TextRange txtRange1 = paragraph.appendText(splitStr[0] + prefixStr);
                            TextRange txtRange2 = paragraph.appendText(suffixStr);
                            txtRange2.getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
                            scienceStrElastic = splitStr[1];
                            matcher = pattern.matcher(scienceStrElastic);

                        } else {
                            System.out.println("不符合正则表达式的内容");
                        }
                        //拼接上，由于限制了只分割成两个数组遗留下来的问题，拼上最后的尾巴
                        if(matcherItem==matcher.groupCount()-1){
                            TextRange txtRange3  = paragraph.appendText(scienceStrElastic);
                        }
                    }

//					System.out.println(paragraph.getText());
//					range1.getCharacterFormat().setFontName("仿宋");
//					range1.getCharacterFormat().setFontSize(14f);
//					range1.getCharacterFormat().setTextColor(Color.orange);
                }
            } else {
                document.replace("${"+entry.getKey()+"}", entry.getValue(), true, true);
            }
        }
    }
}
