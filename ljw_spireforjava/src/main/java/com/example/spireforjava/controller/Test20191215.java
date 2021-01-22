package com.example.spireforjava.controller;

import com.spire.doc.*;
import com.spire.doc.collections.*;
import com.spire.doc.documents.HorizontalAlignment;
import com.spire.doc.documents.Paragraph;
import com.spire.doc.documents.SubSuperScript;
import com.spire.doc.fields.TextRange;
import com.spire.doc.formatting.ParagraphFormat;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: ljw_spireforjava
 * @description:
 * @author: luojiwen
 * @create: 2019-12-15 21:44
 **/
public class Test20191215 {

    public static void main(String[] args) throws Exception {

        Document document = new Document();
        document.loadFromFile("D:\\test\\20191210\\twoTableThreeColTag.docx");
        List<String[]> listSequence = new ArrayList<>();
//        listSequence.add(new String[]{"Tag1","Tag2","Tag3","Tag4","Tag5","Tag6"});
//        listSequence.add(new String[]{"123","234","123","234","123","234"});
//        listSequence.add(new String[]{"123","234","123","234","123","234"});
//        listSequence.add(new String[]{"123","234","123","234","123","234"});
//        listSequence.add(new String[]{"123","234","123","234","123","234"});
//        listSequence.add(new String[]{"123","234","123","234","123","234"});
//        listSequence.add(new String[]{"123","234","123","234","123","234"});
//
        listSequence.add(new String[]{"Tag1", "Tag2", "Tag3"});
        listSequence.add(new String[]{"1", "2", "3"});
        listSequence.add(new String[]{"11", "22", "33"});
        listSequence.add(new String[]{"111", "222", "333"});
        listSequence.add(new String[]{"1111", "2222", "3333"});
        listSequence.add(new String[]{"11111", "22222", "33333"});
        listSequence.add(new String[]{"111111", "222222", "333333"});
        listSequence.add(new String[]{"1111111", "2222222", "3333333"});
//        createTableAndStaticHead(document, listSequence);
        createTableAverage(document, listSequence);

        document.saveToFile("D:\\test\\20191210\\tableTagFilsh.docx", FileFormat.Docx);

    }

    public static void createTableAndStaticHead(Document document, List<String[]> listSequence) throws Exception {

        SectionCollection sectionCollections = document.getSections();
        //获得节点sections
        for (int sectionItem = 0; sectionItem < sectionCollections.getCount(); sectionItem++) {
            TableCollection tableCollection = sectionCollections.get(sectionItem).getTables();
            //获得所有的表格
            for (int tableItem = 0; tableItem < tableCollection.getCount(); tableItem++) {
                Table table = tableCollection.get(tableItem);
                RowCollection rowCollection = table.getRows();
                //如果这个表格没有两行，我们就不做处理，也就是说，这个row数没有大于1
                if (table.getRows().getCount() == 1) {
                    continue;
                }
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
                                    if (cellValue.matches("(-?[0-9]+\\.?[0-9]*[E][-+]?-?[0-9]+\\.?[0-9]*)")) {
                                        String PrefixStr = cellValue.substring(0, cellValue.indexOf("E")) + "×10";
                                        String SuffixStr = cellValue.substring(cellValue.indexOf("E") + 1);
                                        paragraph.appendText(PrefixStr);
                                        paragraph.appendText(SuffixStr).getCharacterFormat().setSubSuperScript(SubSuperScript.Super_Script);
                                        //为了设置下面字体风格和大小,所以用这个接收并设置""
                                        txtRange = paragraph.appendText("");
                                    } else {
                                        txtRange = paragraph.appendText(cellValue);
                                    }
                                    //拿到表头的样式，
                                    ParagraphFormat paragraphFormat = table.get(1, cell1Item).getParagraphs().get(0).getFormat();
                                    HorizontalAlignment horizontalAlignment = table.get(1, cell1Item).getParagraphs().get(0).getFormat().getHorizontalAlignment();
                                    paragraph.getFormat().setHorizontalAlignment(horizontalAlignment);

                                    //楷体,Arial,黑体
                                    txtRange.getCharacterFormat().setFontName("Arial");
                                    txtRange.getCharacterFormat().setFontSize(10f);
                                } catch (Exception e) {

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
                    }
//                    table.getRows().removeAt(1);
                }
            }
        }
    }

    public static void createTableAverage(Document document, List<String[]> listSequence) {
        SectionCollection sectionCollections = document.getSections();
        //获得节点sections
        for (int sectionItem = 0; sectionItem < sectionCollections.getCount(); sectionItem++) {
            TableCollection tableCollection = sectionCollections.get(sectionItem).getTables();
            //获得所有的表格
            for (int tableItem = 0; tableItem < tableCollection.getCount(); tableItem++) {
                Table table = tableCollection.get(tableItem);
                RowCollection rowCollection = table.getRows();
                //如果这个表格没有两行，我们就不做处理，也就是说，这个row数没有大于1
                if (table.getRows().getCount() == 1) {
                    continue;
                }
                //这里设置位1，只要拿到表格的表头的标签
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
                            tableHead = tableHead + cellString;
                        }
                    }
                    String header = StringUtils.join(listSequence.get(0));
                    if (tableHead.contains(header)) {
                        listSequence = listSequence.subList(1, listSequence.size());
                        int createRowNumM = listSequence.size() % 2;
                        int createRowNumC = listSequence.size() / 2;
                        int createRowNum = createRowNumM == 0 ? createRowNumC : createRowNumC + 1;
                        //填充第一个表格的
                        for (int listSequenceItem = 0; listSequenceItem < createRowNum; listSequenceItem++) {
                            //创建一行。并得到这个单元
                            CellCollection cellCollectionRow1 = table.addRow(true).getCells();
                            //填充第一个表格要填充的数据
                            for (int cell1Item = 0; cell1Item < listSequence.get(0).length; cell1Item++) {
                                TableCell tableCell = cellCollectionRow1.get(cell1Item);
                                //拿到表头的的字体对齐方式，对应好对每一列
                                Paragraph paragraph = tableCell.addParagraph();
                                String cellValue = listSequence.get(listSequenceItem)[cell1Item];
                                paragraph.appendText(cellValue);
                            }

                            //填充第二个表格要填充的数据
                            for (int cell1Item = 0; cell1Item < listSequence.get(0).length; cell1Item++) {
                                TableCell tableCell = cellCollectionRow1.get(cell1Item + listSequence.get(0).length);
                                //拿到表头的的字体对齐方式，对应好对每一列
                                Paragraph paragraph = tableCell.addParagraph();
                                //第二个表格所需填充数据的下标
                                int twoIndex = listSequenceItem + createRowNum;
                                //两个表格总共的行数（也就是说一个表格的行数X2）
                                if (createRowNum*2 != listSequence.size() && createRowNum*2==twoIndex+1) {
                                    //如果填充的数据，没有等于表格的行数X2，就将最后一个设置为空
                                    paragraph.appendText("");
                                    continue;
                                }
                                String cellValue = listSequence.get(twoIndex)[cell1Item];
                                paragraph.appendText(cellValue);
                            }
                        }
                    }
                }

                table.getRows().removeAt(1);
            }
        }
    }
}
