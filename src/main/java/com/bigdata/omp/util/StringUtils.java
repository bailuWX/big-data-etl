package com.bigdata.omp.util;

import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
	
	
	/**
	 * 过滤空格
	 * @param content
	 * @return
	 */
	public static String filterWhite(String content){
		String result = "";
		if(!isEmpty(content)){
			result = trim(content);
		}
		return result;
	}
	
	public static String trim(String content){
		if(null != content){
			String temp = content;
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");   
            Matcher m = p.matcher(temp);   
            content = m.replaceAll("");   
		}	
		return content;
	}
	
	/**
	 * 
	 * 功能 :判断字符串是否 为null 或 为 ""
	 * 
	 * 开发：tcsu 2011-10-12
	 * 
	 * @param str
	 *            待判断的字符串
	 * @return true 为null 或 为 ""，false 反之。
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str)) {
			return true;
		}

		return false;
	}

	/**
	 * 
	 * 功能 :判断对象是否为Null或者toString()函数返回""
	 * 
	 * 开发：tcsu 2011-10-12
	 * 
	 * @param obj
	 *            待判断的对象
	 * @return true 为null 或 为 ""，false 反之。
	 */
	public static boolean isEmpty(Object obj) {
		return obj == null || "".equals(obj.toString());
	}

	/**
	 * 
	 * 功能 :在字符串的前面与后面加上%，用于查询参数
	 * 
	 * 开发：tcsu 2011-10-12
	 * 
	 * @param str
	 *            待处理的字符串
	 * @return 加上%的字符串
	 */
	public static String addLikeMark(String str) {
		return "%" + str + "%";
	}
	/**
	 * 
	 * 功能 :在字符串的前面与后面加上%，用于查询参数
	 * 
	 * 开发：tcsu 2011-10-12
	 * 
	 * @param str
	 *            待处理的字符串
	 * @return 加上%的字符串
	 */
	public static String addLeftLikeMark(String str) {
		return "%" + str;
	}
	
	/**
	 * 
	 * 功能 :在字符串的前面与后面加上%，用于查询参数
	 * 
	 * 开发：tcsu 2011-10-12
	 * 
	 * @param str
	 *            待处理的字符串
	 * @return 加上%的字符串
	 */
	public static String addRightLikeMark(String str) {
		return str + "%";
	}
	
	/**
	 * 
	 * 功能 : 将对象数组转化为字符串数组
	 * 
	 * 开发：tcsu 2011-10-12
	 * 
	 * @param objectArray
	 *            对象数组
	 * @return 字符串数组
	 */
	public static String[] convertObjectArrayToStringArray(Object[] objectArray) {
		String[] stringArray = new String[objectArray.length];
		int i = 0;
		for (Object o : objectArray) {
			stringArray[i] = o.toString();
			i++;
		}
		return stringArray;
	}

	/**
	 * 
	 * 功能 : 将String数组转换成String列表
	 * 
	 * 开发：tcsu 2011-10-12
	 * 
	 * @param array
	 *            数组
	 * @return 列表
	 */
	public static List<String> arrayToList(String[] array) {
		List<String> result = new ArrayList<String>();
		for (String s : array) {
			result.add(s);
		}
		return result;
	}

	/**
	 * 
	 * 功能 :生成供Jsonp调用的Json串
	 * 
	 * 开发：tcsu 2011-10-12
	 * 
	 * @param json
	 *            原Json串
	 * @param jsonpcallback
	 *            jsonpcallback
	 * @return 新Json串
	 */
	public static String genJsonCallBack(String json, String jsonpcallback) {
		String realJson = json;
		if (!StringUtils.isEmpty(jsonpcallback)) {
			realJson = jsonpcallback + "(" + json + ")";
		}
		return realJson;
	}
	/**
	 * 保留小数
	 * @param num 被格式化的数据
	 * @param formatString 格式化字符串 如保留三位小数为："000"
	 * @return Double 格式化后的数据
	 */
	public static Double toDouble(Double num ,String formatString){
		DecimalFormat decimalFormat = new DecimalFormat("#."+formatString);
		String strNum = decimalFormat.format(num);
		num = Double.parseDouble(strNum);
		return num;
	}
	
	
    public static String getRandomInt(int length ) {
    	Random random = new Random();
    	StringBuffer str = new StringBuffer();
    	for (int i = 0; i < length; i++) {
    	    str.append(random.nextInt(10)); // 生成随机数字 0-9
    	}
    	return str.toString();
    }
    
    /**
     * 替换html常用特殊字符
     * @param str
     * @return
     */
    public static String checkHtml(String str){
    	String[] htmlChar = {"&", "<", ">", "\"","®","©","™"," "};
    	String[] relChar = {"&amp;", "&lt;", "&gt;", "&quot;","&reg;","&copy;","&trade;","&emsp;"};
    	for(int i = 0;i < htmlChar.length;i++){
    		str = str.replaceAll(htmlChar[i], relChar[i]);
    	}
    	return str;
    }
    
    /**
     * 根据键值对填充字符串，如("hello ${name}",{name:"xiaoming"})
     * 输出：
     * @param content
     * @param map
     * @return
     */
    public static String renderString(String content, Map<String, String> map){
        Set<Map.Entry<String, String>> sets = map.entrySet();
        for(Map.Entry<String, String> entry : sets) {
            String regex = "\\$\\{" + entry.getKey() + "\\}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(content);
            content = matcher.replaceAll(entry.getValue());
        }
        return content;
    }
    
    public static void main(String[] args) {
    	String str  = "####<Jul 11, 2016 11:10:21 PM CST> <Info> <org.hibernate.validator.engine.resolver.DefaultTraversableResolver> <odm114110> <pad-3001> <[STANDBY] ExecuteThread: '3' for queue: 'weblogic.kernel.Default (self-tuning)'> <<WLS Kernel>> <> <86fde4886ec56ffc:-1a977594:155da815f7c:-8000-0000000000000004> <1468249821093> <BEA-000000> <Instantiated an instance of org.hibernate.validator.engine.resolver.JPATraversableResolver.> ";
    	System.out.println(StringUtils.checkHtml(str));
    }
}
