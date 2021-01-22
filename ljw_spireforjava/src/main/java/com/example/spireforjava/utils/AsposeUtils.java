package com.example.spireforjava.utils;

import java.io.InputStream;
/**
 * @program: myProject
 * @description:
 * @author: luojiwen
 * @create: 2020-10-20 15:01
 **/
public class AsposeUtils {

    /**
     * 获取aspose-excel的验证
     *
     * @return
     */
    public static boolean getExcelLicense() {
        boolean result = false;
        try {
            InputStream is = AsposeUtils.class.getResourceAsStream("/file/excel_pdf_license.xml");
            if (is == null) {
                return false;
            }
            com.aspose.cells.License aposeLic = new com.aspose.cells.License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取aspose-word的验证
     *
     * @return
     */
    public static boolean getWordLicense() {
        boolean result = false;
        try {
            InputStream is = AsposeUtils.class.getResourceAsStream("/file/word-pdf-license.xml");
            if (is == null) {
                return false;
            }
            com.aspose.words.License aposeLic = new com.aspose.words.License();
            aposeLic.setLicense(is);
            result = true;
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
