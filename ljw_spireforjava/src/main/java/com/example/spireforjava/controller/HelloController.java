package com.example.spireforjava.controller;

import ch.qos.logback.core.util.FileUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class HelloController {
	
	@RequestMapping(value="/xixi",produces = { "text/html;charset=UTF-8;", "application/json;charset=UTF-8;" })
	public String hello() throws UnsupportedEncodingException {
		return "hello, huadapay华大";
	}

	public static void main(String args[]) {
		String str = "-0.123E12";
		String pattern = "(-?[0-9]+\\.?[0-9]*[e,E]?[-,+]?-?[0-9]+\\.?[0-9]*)";
		System.out.println(str.matches(pattern));
	}

	@RequestMapping(value="/lijieyaode")
	public String lijieyaode() throws UnsupportedEncodingException {
		return "hello, huadapay华大";
	}


	/**
	 * 上传单个文件
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/uploadimp")
	public static String uploadimp(@RequestParam("file") MultipartFile file) throws IOException {

		String fileName = String.valueOf(System.currentTimeMillis());
		getGeneFrequencyMarkers(file.getInputStream());
		return "success";
	}


	/**
	 * 功能描述: 读取指定文件的里面的内容
	 *
	 * @param fwqPath
	 * @return
	 * @auther WenJi Luo
	 * @date 2019/6/19 14:43
	 */
	public static String readDefinition(InputStream inputStream) {

		String result = "";
		try {


//          InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(fwqPath));
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream,"GBK"));// 构造一个BufferedReader类来读取文件
//             BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fwqPath),"UTF-8"));// 构造一个BufferedReader类来读取文件
			String s = null;
			while ((s = br.readLine()) != null) {// 使用readLine方法，一次读一行
				result = result + "\n" + s;
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}


	/**
	 * 功能描述: 上次文件识别基因型频率
	 * @param: [valueJsons]
	 * @return: java.util.Map<java.lang.String,java.util.Map<java.lang.String,java.lang.String>>
	 * @auther: ChenHaiFeng
	 * @date: 2019/8/14 19:10
	 */
	public static Map<String, Map<String,String>> getGeneFrequencyMarkers(InputStream inputStream){
		Map<String,Map<String,String>> sampleMarkersMap=new HashMap<String,Map<String,String>>();
			//logger.info("[下机数据解析]：下载文件-"+valueJson);
			String txt= HelloController.readDefinition(inputStream);
			if(StringUtils.isNotEmpty(txt)) {
				String[] lines=txt.split("\n");
				if(lines.length>1) {
					for(int i=0;i<lines.length;i++) {
						//logger.info("[下机数据解析]：解析文件"+i+"=="+lines[i]);
						String[] infos=lines[i].split("\t");
						if(infos==null||infos.length<3||infos.length>4||StringUtils.isEmpty(infos[0])) {
							continue;
						}
						String sampleGene=infos[0];
						if(!sampleMarkersMap.containsKey(sampleGene)) {
							sampleMarkersMap.put(sampleGene, new HashMap<>());
						}
						sampleMarkersMap.get(sampleGene).put(infos[1],infos[2]);
					}
				}
			}
		return sampleMarkersMap;
	}
}
