/**
 * 
 */
package com.example.spireforjava.utils;

import java.awt.FontMetrics;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.swing.JLabel;

import com.aspose.cells.WorkbookDesigner;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfGState;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

/**
 * @author qiudong
 * @version 2017年12月22日 下午5:15:00
 * 类说明
 */
/**
 * @author qiudong
 *
 */
@Component
public class FileUtil {

	final static Logger logger = LoggerFactory.getLogger(FileUtil.class);
	

	private static String uploadfileURL;
	


    
	/**
	 * 重写URLEncoder的方法，处理url中带"+"号问题
	 * url地址解码
	 * @param s
	 * @param enc
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String decode(String s, String enc)
	        throws UnsupportedEncodingException{

	        boolean needToChange = false;
	        int numChars = s.length();
	        StringBuffer sb = new StringBuffer(numChars > 500 ? numChars / 2 : numChars);
	        int i = 0;

	        if (enc.length() == 0) {
	            throw new UnsupportedEncodingException ("URLDecoder: empty string enc parameter");
	        }

	        char c;
	        byte[] bytes = null;
	        while (i < numChars) {
	            c = s.charAt(i);
	            switch (c) {
//	            case '+':
//	                sb.append(' ');
//	                i++;
//	                needToChange = true;
//	                break;
	            case '%':
	                try {

	                    // (numChars-i)/3 is an upper bound for the number
	                    // of remaining bytes
	                    if (bytes == null) {
                            bytes = new byte[(numChars-i)/3];
                        }
	                    int pos = 0;

	                    while ( ((i+2) < numChars) &&
	                            (c=='%')) {
	                        int v = Integer.parseInt(s.substring(i+1,i+3),16);
	                        if (v < 0) {
                                throw new IllegalArgumentException("URLDecoder: Illegal hex characters in escape (%) pattern - negative value");
                            }
	                        bytes[pos++] = (byte) v;
	                        i+= 3;
	                        if (i < numChars) {
                                c = s.charAt(i);
                            }
	                    }

	                    // A trailing, incomplete byte encoding such as
	                    // "%x" will cause an exception to be thrown

	                    if ((i < numChars) && (c=='%')) {
                            throw new IllegalArgumentException(
                             "URLDecoder: Incomplete trailing escape (%) pattern");
                        }

	                    sb.append(new String(bytes, 0, pos, enc));
	                } catch (NumberFormatException e) {
	                    throw new IllegalArgumentException(
	                    "URLDecoder: Illegal hex characters in escape (%) pattern - "
	                    + e.getMessage());
	                }
	                needToChange = true;
	                break;
	            default:
	                sb.append(c);
	                i++;
	                break;
	            }
	        }

	        return (needToChange? sb.toString() : s);
	    }
	
	/**
	 * http文件下载 处理中文名称或路径问题
	 * @param urlPath	url地址  http://......
	 * @param fileName	下载的文件命名  xxx.pdf xxx.rtf xxx.doc
	 * @param downloadDir	下载的文件保存的地址
	 * @return
	 */
    public static String download(String urlPath, String fileName, String downloadDir) {
        File file = null;
        try {
            String[] array=urlPath.split("/");
            String name=array[array.length-1];
            String domain=array[0]+"//"+array[2]+"/";
            String dir="";
            for(int i=3;i<array.length-1;i++) {
                dir+=array[i]+"/";
            }
         // 统一资源
            URL url = null;
            if(name.contains(" ")) {
            	url = new URL(domain+dir+decode(name,"UTF-8"));
            }else {
            	url = new URL(domain+dir+URLEncoder.encode(name,"UTF-8"));
            }
            // http的连接类
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            // 设定请求的方法，默认是GET
            connection.setRequestMethod("GET");
            // 设置字符编码
            connection.setRequestProperty("Charset", "UTF-8");
            // 打开到此 URL 引用的资源的通信链接（如果尚未建立这样的连接）
            connection.connect();
            int code = connection.getResponseCode();
            if (code != HttpURLConnection.HTTP_OK) {
                logger.error("错误返回码:"+code);
                return "文件下载失败,请检查文件是否存在!";
            }
            BufferedInputStream bin = new BufferedInputStream(connection.getInputStream());
            
            //文件保存位置  
            File saveDir = new File(downloadDir);
            saveDir.setWritable(true, false); //设置写权限，windows下不用此语句
            saveDir.mkdirs();
            if(!saveDir.exists()){
            	logger.error("文件存放临时路径生成失败:"+downloadDir);
            }else{
            	logger.info("文件保存位置:"+saveDir.getAbsolutePath());
            }
            
            String path = downloadDir + File.separatorChar + fileName;
            file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            OutputStream out = new FileOutputStream(file);
            int size = 0;
            byte[] buf = new byte[1024];
            while ((size = bin.read(buf)) != -1) {
                out.write(buf, 0, size);
            }
            logger.info("文件下载结束！");
            bin.close();
            out.close();
            return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "文件下载失败,请检查文件是否存!";
        }
        
    }
	
	/** 
     * 从网络Url中下载文件 
     * @param urlStr 所要下载的http文件URL地址
     * @param fileName 所要下载的文件的名字
     * @param savePath 文件所保存的路径
     * @throws IOException 
	 * @throws InterruptedException 
     */  
    public static void downLoadFromUrl(String urlStr,String fileName,String savePath) throws IOException, InterruptedException{  
    	
    	URL url = new URL(urlStr);    
    	
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒  
        conn.setConnectTimeout(3*1000);  
        //防止屏蔽程序抓取而返回403错误  
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //
        conn.setRequestMethod("GET");

        //得到输入流
        InputStream inputStream = conn.getInputStream();    
        //获取自己数组  
        byte[] getData = readInputStream(inputStream);      
  
        //文件保存位置  
        File saveDir = new File(savePath);
        saveDir.setWritable(true, false); //设置写权限，windows下不用此语句
        saveDir.mkdirs();
        if(!saveDir.exists()){
        	logger.error("报告存放临时路径生成失败:"+savePath);
        }else{
        	logger.info("文件保存位置:"+saveDir.getAbsolutePath());
        }
        
        File file = new File(saveDir+File.separator+fileName);      
        FileOutputStream fos = new FileOutputStream(file);   
        logger.info("文件字节数组长度:"+getData.length);
        fos.write(getData);  
        fos.flush();
        if(fos!=null){  
            fos.close();    
        }
        if(inputStream!=null){  
            inputStream.close();  
        }
        logger.info("文件生成成功:"+file.getAbsolutePath());
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
    
//	public static void main(String[] args) throws Exception {
//		String url = "http://10.49.32.113:8888/group1/M00/00/10/CjEgcVzdLl6ACTysAAF2gEA4as8658.pdf";
//		download(url, "下载的.pdf", "E:\\document\\2019 job\\2019.05\\0511-0520\\供NIFTY测试使用-报告\\");
//	}   
	/**
	 * @param inputFile 输入文件本地全路径
	 * @param outputFile 输出文件本地全路径
	 * @param waterMarkName 水印字符串，若为null则不添加水印
	 * @param qrCodePath 二维码本地全路径或者网络图片路径，若为null则不添加二维码
	 */
	public static String waterMarkAndQRCode(String inputFile,String outputFile,String waterMarkName,String qrCodePath){
		PdfReader reader = null;
		PdfStamper stamper = null;
		boolean isNew = false;
		try {
			System.out.println("开始添加水印----------------------------");
			System.out.println(inputFile);
            reader = new PdfReader(inputFile); 
            stamper = new PdfStamper(reader, new FileOutputStream(outputFile));    
            if(StringUtils.isNotBlank(waterMarkName)){
            	//开始加水印
                BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   BaseFont.EMBEDDED);  
                Rectangle pageRect = null;
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.1f);//透明度 0.3f
                gs.setStrokeOpacity(0.29f);  //0.4f
                int total = reader.getNumberOfPages() + 1;//页码总数
                JLabel label = new JLabel();  
                label.setText(waterMarkName);   
                FontMetrics metrics = label.getFontMetrics(label.getFont());   
                int markH = metrics.getHeight();
                int markW = metrics.stringWidth(label.getText());  
                // 水印之间的间隔
                final int XMOVE = 200;
                // 水印之间的间隔
                final int YMOVE = 200;
                PdfContentByte under;
                String[] split = waterMarkName.split(",");
                int index = 0;
                for (int i = 1; i < total; i++) {
                    pageRect = reader.getPageSizeWithRotation(i); 
                    int height = (int) pageRect.getHeight();//pdf高
                    int width = (int) pageRect.getWidth();//pdf宽
                    int x = -width / 2;
                    int y = -height / 2;
                    under = stamper.getOverContent(i);
                    under.saveState();
                    under.setGState(gs);
                    under.beginText();
                    under.setFontAndSize(base, 26);  
                    // 循环添加水印,水印文字成45度角倾斜
                    while (x < width * 1.5) {
                        y = -height / 2;
                        while (y < height * 1.5) {
                            under.showTextAligned(Element.ALIGN_LEFT , split[index++], x , y , 40);//Element.ALIGN_LEFT
                            y += markH + YMOVE;
                            if(index >= split.length){
                            	index = 0;
                            }
                        }
                        x += markW + XMOVE;
                    }
                    // 添加水印文字    
                    under.endText();
                }   
                isNew = true;
            }
            
            if(StringUtils.isNotBlank(qrCodePath)){
            	PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.9f);//透明度 0.3f
                gs.setStrokeOpacity(0.29f);  //0.4f
              int pageIndex = 1;//二维码加到PDF文件的第几页
      		  Document document = new Document(reader.getPageSize(pageIndex)); 
      		  float width = document.getPageSize().getWidth(); 
      		  float height = document.getPageSize().getHeight();
      		  PdfContentByte over; 
      		  Image img = Image.getInstance(qrCodePath); 
      		  System.out.println(img.getWidth()+","+img.getHeight());
      		  width = 5;//width - 2*img.getWidth(); 
      		  height = 5;//height - img.getHeight();
      		  System.out.println("width:" + width); 
      		  System.out.println("height:" + height); 
      		  img.setAbsolutePosition(width, height); 
      		  img.setAlignment(Image.ALIGN_RIGHT); 
      		  over = stamper.getOverContent(pageIndex); 
      		over.setGState(gs);
      		  over.addImage(img); 
      		isNew = true;
            }
        } catch (Exception e) {    
            e.printStackTrace();    
        }  finally{
        	//一定不要忘记关闭流
        	if(stamper != null){
        		try {
					stamper.close();
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}  
        	}
        	if(reader != null){
        		reader.close();
        	}
        }
		if(!isNew){
			return inputFile;
		}else {
			return outputFile;
		}
	}
	
	/**
	 * @param inputFile 输入文件本地全路径
	 * @param outputFile 输出文件本地全路径
	 * @param waterMarkName 水印字符串，若为null则不添加水印
	 */
	public static String lightWaterMark(String inputFile,String outputFile,String waterMarkName){
		PdfReader reader = null;
		PdfStamper stamper = null;
		boolean isNew = false;
		try {
//			System.out.println("开始添加水印----------------------------");
//			System.out.println(inputFile);
            reader = new PdfReader(inputFile); 
            if(StringUtils.isNotBlank(waterMarkName)){
            	//开始加水印
            	stamper = new PdfStamper(reader, new FileOutputStream(outputFile));    
                BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   BaseFont.EMBEDDED);  
                Rectangle pageRect = null;
                PdfGState gs = new PdfGState();
                gs.setFillOpacity(0.1f);//透明度 0.3f
                gs.setStrokeOpacity(0.29f);  //0.4f
                int total = reader.getNumberOfPages() + 1;//页码总数
                JLabel label = new JLabel();  
                label.setText(waterMarkName);   
                FontMetrics metrics = label.getFontMetrics(label.getFont());   
                int markH = metrics.getHeight();
                int markW = metrics.stringWidth(label.getText());  
                // 水印之间的间隔
                final int XMOVE = 200;
                // 水印之间的间隔
                final int YMOVE = 200;
                PdfContentByte under;
                String[] split = waterMarkName.split(",");
                int index = 0;
                for (int i = 1; i < total; i++) {
                    pageRect = reader.getPageSizeWithRotation(i); 
                    int height = (int) pageRect.getHeight();//pdf高
                    int width = (int) pageRect.getWidth();//pdf宽
                    int x = -width / 2;
                    int y = -height / 2;
                    under = stamper.getOverContent(i);
                    under.saveState();
                    under.setGState(gs);
                    under.beginText();
                    under.setFontAndSize(base, 26);  
                    // 循环添加水印,水印文字成45度角倾斜
                    while (x < width * 1.5) {
                        y = -height / 2;
                        while (y < height * 1.5) {
                            under.showTextAligned(Element.ALIGN_LEFT , split[index++], x , y , 40);//Element.ALIGN_LEFT
                            y += markH + YMOVE;
                            if(index >= split.length){
                            	index = 0;
                            }
                        }
                        x += markW + XMOVE;
                    }
                    // 添加水印文字    
                    under.endText();
                }   
                isNew = true;
            }
            
        } catch (Exception e) {    
            e.printStackTrace();    
        }  finally{
        	//一定不要忘记关闭流
        	if(stamper != null){
        		try {
					stamper.close();
				} catch (DocumentException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}  
        	}
        	if(reader != null){
        		reader.close();
        	}
        }
		if(!isNew){
			return inputFile;
		}else {
			return outputFile;
		}
	}
 
    /**
     * 报告加上水印
     * @param inputFile 本地文件
     * @param outputFile 输出文件
     * @param waterMarkName 水印内容
     */
	public static void waterMark(String inputFile,String outputFile, String waterMarkName) {    
        try {
            PdfReader reader = new PdfReader(inputFile);
            PdfStamper stamper = new PdfStamper(reader, new FileOutputStream(outputFile));    
            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",   BaseFont.EMBEDDED);  
            Rectangle pageRect = null;
            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.19f);//透明度 0.3f
            gs.setStrokeOpacity(0.29f);  //0.4f
            int total = reader.getNumberOfPages() + 1;//页码总数
            JLabel label = new JLabel();  
            label.setText(waterMarkName);   
            FontMetrics metrics = label.getFontMetrics(label.getFont());   
            int markH = metrics.getHeight();
            int markW = metrics.stringWidth(label.getText());  
            // 水印之间的间隔
            final int XMOVE = 140;
            // 水印之间的间隔
            final int YMOVE = 100;
            PdfContentByte under;
            for (int i = 1; i < total; i++) {
                pageRect = reader.getPageSizeWithRotation(i); 
                int height = (int) pageRect.getHeight();//pdf高
                int width = (int) pageRect.getWidth();//pdf宽
                int x = -width / 2;
                int y = -height / 2;
                under = stamper.getOverContent(i);
                under.saveState();
                under.setGState(gs);
                under.beginText();
                under.setFontAndSize(base, 26);  
                // 循环添加水印,水印文字成45度角倾斜
                while (x < width * 1.5) {
                    y = -height / 2;
                    while (y < height * 1.5) {
                        under.showTextAligned(Element.ALIGN_LEFT , waterMarkName, x , y , 40);//Element.ALIGN_LEFT
                        y += markH + YMOVE;
                    }
                    x += markW + XMOVE;
                }
                // 添加水印文字    
                under.endText();
            }   
           //一定不要忘记关闭流
            stamper.close();  
            reader.close();
        } catch (Exception e) {    
            e.printStackTrace();    
        }    
    }
    
	/**
     * 获取图片宽度、高度
     * @param file  图片文件
     * @return {宽度,高度}
     */
    public static int[] getImgWidth(File file) {
        InputStream is = null;
        BufferedImage src = null;
        int[] result = new int[2];
        try {
            is = new FileInputStream(file);
            src = javax.imageio.ImageIO.read(is);
            result[0] = src.getWidth(); // 得到源图宽
            result[1] = src.getHeight();// 得到源图高
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    
    /** 
     * 将存放在sourceFilePath目录下的源文件，打包成fileName名称的zip文件，并存放到zipFilePath路径下 
     * @param sourceFilePath :待压缩的文件路径 
     * @param zipFilePath :压缩后存放路径 
     * @param fileName :压缩后文件的名称 
     * @return 
     */  
	public static boolean fileToZip(String sourceFilePath,String zipFilePath,String fileName){  
        boolean flag = false;  
        File sourceFile = new File(sourceFilePath);  
        FileInputStream fis = null;  
        BufferedInputStream bis = null;  
        FileOutputStream fos = null;  
        ZipOutputStream zos = null;  

        if(sourceFile.exists() == false){  
            logger.info("待压缩的文件目录："+sourceFilePath+"不存在.");  
            sourceFile.mkdir(); // 新建目录
        }  
        try {  
            File zipFile = new File(zipFilePath + "/" + fileName +".zip");  
            if(zipFile.exists()){  
            	logger.info(zipFilePath + "目录下存在名字为:" + fileName +".zip" +"打包文件.");  
            }else{  
                File[] sourceFiles = sourceFile.listFiles();  
                if(null == sourceFiles || sourceFiles.length<1){  
                	logger.info("待压缩的文件目录：" + sourceFilePath + "里面不存在文件，无需压缩.");  
                }else{  
                    fos = new FileOutputStream(zipFile);  
                    zos = new ZipOutputStream(new BufferedOutputStream(fos));  
                    byte[] bufs = new byte[1024*10];  
                    for(int i=0;i<sourceFiles.length;i++){  
                        //创建ZIP实体，并添加进压缩包  
                        ZipEntry zipEntry = new ZipEntry(sourceFiles[i].getName());  
                        zos.putNextEntry(zipEntry);  
                        //读取待压缩的文件并写进压缩包里  
                        fis = new FileInputStream(sourceFiles[i]);  
                        bis = new BufferedInputStream(fis, 1024*10);  
                        int read = 0;  
                        while((read=bis.read(bufs, 0, 1024*10)) != -1){  
                            zos.write(bufs,0,read);  
                        }  
                    }
                    flag = true;  
                }
            }
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw new RuntimeException(e);  
        } finally{  
            //关闭流  
            try {  
                if(null != bis) {
                    bis.close();
                }
                if(null != zos) {
                    zos.close();
                }
            } catch (IOException e) {  
                e.printStackTrace();  
                throw new RuntimeException(e);  
            }  
        }  
        return flag;  
    }
    
	/**
	 * 上传文件到fastdfs文件服务器
	 * @param filePath
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String uploadFileToFastdfsServer(String filePath) {
		String url="";
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
			HttpPost httpPost = new HttpPost(uploadfileURL);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			File file = new File(filePath);
			String fileName = file.getName();
			InputStream fileInputStream = new FileInputStream(file);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			byte[] buffer = new byte[1024 * 4];
			int n = 0;
			while ((n = fileInputStream.read(buffer)) != -1) {
			    out.write(buffer, 0, n);
			}
			byte[] fileData = out.toByteArray();
			fileInputStream.close();
			builder.addBinaryBody("file", fileData, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
			builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
			builder.addTextBody("path", "1");//
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);// 执行提交
			HttpEntity responseEntity = response.getEntity();
			// 将响应内容转换为字符串
			String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
			Map<String,String> map = (Map)JSONObject.parseObject(result);
			url = map.get("previewurl");
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return url;
	}
	
	/**
	  * 上传文件到fastdfs文件服务器
	  * @param fileData byte[]数组  
	  * @param fileName 带后缀的文件名称  xx.pdf
	  * @return
	  */
	public static String uploadFileToFastdfsServer(byte[] fileData, String fileName) {
		String url = "";
		try {
			CloseableHttpClient httpClient = HttpClients.createDefault();
//			HttpPost httpPost = new HttpPost(ConstantsConfig.FASTDFS_UPLOADFILE_URL);
			HttpPost httpPost = new HttpPost(uploadfileURL);
			MultipartEntityBuilder builder = MultipartEntityBuilder.create();
			builder.addBinaryBody("file", fileData, ContentType.MULTIPART_FORM_DATA, fileName);// 文件流
			builder.addTextBody("filename", fileName);// 类似浏览器表单提交，对应input的name和value
			builder.addTextBody("path", "1");//
			HttpEntity entity = builder.build();
			httpPost.setEntity(entity);
			HttpResponse response = httpClient.execute(httpPost);// 执行提交
			HttpEntity responseEntity = response.getEntity();
			// 将响应内容转换为字符串
			String result = EntityUtils.toString(responseEntity, Charset.forName("UTF-8"));
			logger.info("fastdfs result:"+result);
			Map<String, Object> map = JSONObject.parseObject(result);
			url = (String) map.get("previewurl");
		} catch (ParseException | IOException e) {
			e.printStackTrace();
		}
		return url;
	}

    /**
     * word(doc/docx)转pdf 需引入 aspose-words-16.4.0-jdk16.jar包  插件windows linux下均可用
     *
     * @param inPath  源文件路径
     * @param outPath 输出文件路径
     */
    public static void convertDocToPdf(String inPath, String outPath) {
        try {
        	logger.info("[convertDocToPdf]License验证--->"+AsposeUtils.getWordLicense());
            File file = new File(outPath); // 新建一个空白pdf文档
            FileOutputStream os = new FileOutputStream(file);
            com.aspose.words.Document doc = new com.aspose.words.Document(inPath); // Address是将要被转化的word文档
            doc.save(os, com.aspose.words.SaveFormat.PDF);// 全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF,EPUB, XPS, SWF 相互转换
        } catch (Exception e) {
        	logger.error("[convertDocToPdf]<--->"+e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * 
     * @param excelPath 源文件路径
     * @param pdfPath 输出文件路径
     */
    public static void convertExcelToPdf(String excelPath,String pdfPath) {
		try {
			logger.info("[convertExcelToPdf]License验证--->"+AsposeUtils.getExcelLicense());
			OutputStream out = new FileOutputStream(pdfPath);
			WorkbookDesigner designer = new WorkbookDesigner();
			com.aspose.cells.Workbook wb = new com.aspose.cells.Workbook();
			wb = new com.aspose.cells.Workbook(excelPath);
			designer.setWorkbook(wb);
			designer.process(true);
			wb.save(out, com.aspose.cells.SaveFormat.PDF);//有word和excel包，此处用excel包
			out.flush();
			out.close();
		} catch (Exception e) {
			logger.error("[convertExcelToPdf]<--->"+e.getMessage());
            e.printStackTrace();
		}
    }

    public static void main(String[] args) throws FileNotFoundException {
        InputStream inputStream = new FileInputStream(new File("E:\\a\\20200330\\123.docx"));
        OutputStream outputStream = new FileOutputStream(new File("E:\\a\\20200330\\result123.docx"));
        waterMark("E:\\a\\20200330\\123.docx","E:\\a\\20200330\\result123.docx","我是水印");
    }
}
