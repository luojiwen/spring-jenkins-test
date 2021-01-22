package com.example.spireforjava.controller;

import com.spire.doc.Document;
import com.spire.doc.FileFormat;
import com.spire.doc.Table;
import com.spire.doc.TableRow;
import com.spire.doc.collections.*;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.SubSuperScript;
import com.spire.doc.fields.TextRange;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: myProject
 * @description: wps和office word 的动态表格样式兼容问题
 * @author: luojiwen
 * @create: 2020-10-26 09:14
 **/
public class TEST20201026 {
    /**
     * 功能描述: 创建表格，在指定的表头下面，动态创建（可以支持科学计数法（如果扫描到前面是数字，中间是E，后面是数字，就会替换成上标））
     *
     * @param listSequence
     * @return
     * @author WenJi Luo
     * @date 2019/10/28 16:15
     */
    public static void createTableAndStaticHead(Document document,List<String[]> listSequence) throws Exception {

        SectionCollection sectionCollections = document.getSections();
        //获得节点sections
        for (int sectionItem = 0; sectionItem < sectionCollections.getCount(); sectionItem++) {
            TableCollection tableCollection = sectionCollections.get(sectionItem).getTables();
            //获得所有的表格
            for (int tableItem = 0; tableItem < tableCollection.getCount(); tableItem++) {
                Table table = tableCollection.get(tableItem);
                RowCollection rowCollection = table.getRows();
                //如果这个表格没有两行，我们就不做处理，也就是说，这个row数没有大于1
                if (table.getRows().getCount() == 1 || listSequence.size() == 1) {
                    continue;
                }
                //这里设置位1，只要拿到表格的表头
                for (int rowItem = 1; rowItem <= 1; rowItem++) {
                    TableRow tableRow = rowCollection.get(rowItem);
                    CellCollection cellCollection = tableRow.getCells();
                    String tableHead = "";
                    for (int cellItem = 0; cellItem < cellCollection.getCount(); cellItem++) {
                        ParagraphCollection paragraphCollection = table.get(rowItem, cellItem).getParagraphs();
                        System.out.print("第"+(tableItem+1)+"个表格头内容：");
                        for (int paragraphItem = 0; paragraphItem < paragraphCollection.getCount(); paragraphItem++) {
                            Paragraph paragraph = paragraphCollection.get(paragraphItem);
                            String cellString = paragraph.getText();
                            System.out.println(cellString);
                            tableHead = tableHead + cellString;
                        }
                    }
//                    System.out.println();
                    System.out.println("-->"+tableHead);
                    String header = StringUtils.join(listSequence.get(0));

                    if (tableHead.contains(header)) {

                        listSequence = listSequence.subList(1, listSequence.size());
                        for (int listSequenceItem = 0; listSequenceItem < listSequence.size(); listSequenceItem++) {
                            //创建一行。并得到这个单元
                            CellCollection cellCollection1 = table.addRow(true).getCells();
                            for (int cell1Item = 0; cell1Item < cellCollection1.getCount(); cell1Item++) {
                                //拿到表头的的字体对齐方式，对应好对每一列
                                Paragraph paragraph = cellCollection1.get(cell1Item).addParagraph();
                                String cellValue = listSequence.get(listSequenceItem)[cell1Item];
                                try {
                                    TextRange txtRange = null;
                                    //本体
                                    TextRange txtRangeBody = null;
                                    //上标
                                    TextRange txtRangeScript = null;
                                    if (cellValue.matches("((-?\\d+.?\\d*)[Ee]{1}[-\\+]{0,1}(\\d+))")) {
                                        float fontSize = table.get(1, cell1Item).getParagraphs().get(0).appendText("").getCharacterFormat().getFontSize();
                                        String PrefixStr = cellValue.substring(0, cellValue.indexOf("E")) + "×10";
                                        String SuffixStr = cellValue.substring(cellValue.indexOf("E") + 1);
                                        txtRangeBody = paragraph.appendText(PrefixStr);
                                        txtRangeBody.getCharacterFormat().setFontName("仿宋");
                                        txtRangeBody.getCharacterFormat().setFontSize(fontSize);
                                        txtRangeBody.getCharacterFormat().setBold(false);
                                        txtRangeScript = paragraph.appendText(SuffixStr);
                                        txtRangeScript.getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
                                        txtRangeScript.getCharacterFormat().setFontName("仿宋");
                                        txtRangeScript.getCharacterFormat().setFontSize(fontSize);
                                        txtRangeScript.getCharacterFormat().setBold(false);
                                        //为了设置下面字体风格和大小,所以用这个接收并设置""
                                        txtRange = paragraph.appendText("");
                                    } else {
                                        txtRange = paragraph.appendText(cellValue);
                                    }
                                    //这里写死了，只拿第二行标签的表格样式
                                    HorizontalAlignment horizontalAlignment = table.get(1, cell1Item).getParagraphs().get(0).getFormat().getHorizontalAlignment();
                                    paragraph.getFormat().setHorizontalAlignment(horizontalAlignment);
                                    float fontSize = table.get(1, cell1Item).getParagraphs().get(0).appendText("").getCharacterFormat().getFontSize();
                                    String fontName = table.get(1, cell1Item).getParagraphs().get(0).appendText("").getCharacterFormat().getFontName();
                                    //楷体,Arial,黑体,这个他们要仿宋就写死了，从表中拿不到，只能拿到Calibri
                                    txtRange.getCharacterFormat().setFontName("仿宋");
                                    txtRange.getCharacterFormat().setFontSize(fontSize);
                                    txtRange.getCharacterFormat().setBold(false);
                                } catch (Exception e) {
                                    throw new Exception("cellValue:" + cellValue + "。不符合正则表格的格式。请联系管理员");
                                }

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
                        table.getRows().removeAt(1);
                    }
                }
            }
        }
    }

    public static void main(String[] args) throws Exception {

        List<String[]> listSequence = new ArrayList<>();
        listSequence.add(new String[]{"Tag1", "Tag2", "Tag3"});
        listSequence.add(new String[]{"1", "2", "3"});
        listSequence.add(new String[]{"11", "22", "33"});
        listSequence.add(new String[]{"111", "222", "333"});
        listSequence.add(new String[]{"1111", "2222", "3333"});
        listSequence.add(new String[]{"11111", "22222", "33333"});
        listSequence.add(new String[]{"111111", "222222", "333333"});
        listSequence.add(new String[]{"1111111", "2222222", "3333333"});

        Document document = new Document();
        document.loadFromFile("D:\\test\\3.docx");
        createTableAndStaticHead(document,listSequence);
        document.saveToFile("D:\\test\\result3.docx", FileFormat.Docx);
    }
}
