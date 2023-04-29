/**
 * @Title: TypeHelper.java
 * @Description: 类型转换工具类
 * @Package: com.bigdata.ddf.util;
 * @author: 黄强（huangqiang@bigdata.com）
 * @date: 2018年12月14日 下午3:47:28
 * @version: V1.0  
 */
package com.bigdata.omp.util;
import java.util.List;
import java.util.Map;

/**
 * @ClassName: TypeHelper
 * @Description: 类型转换工具类
 */
public class TypeHelper{
	/**
	 * @Title: toString
	 * @Description: toString
	 * @param s
	 * @param defaultValue
	 * @return String
	 */
	public static String toString(Object s, String defaultValue) {
		if(s == null || StringHelper.isEmpty(s.toString())) {
            return defaultValue;
        }
		if(s.getClass().isArray()){
				String str = "";
				Object[] arr = (Object[]) s;
				for(int i=0; i<arr.length; i++){
					str += arr[i];
					if(i < arr.length - 1){
						str += ",";
					}
				}
				return str;
			}
		return s.toString();
	}
	
	/**
	 * @Title: toString
	 * @Description: toString
	 * @param s
	 * @return String
	 */
	public static String toString(Object s) {
		return toString(s, "");
	}
	
	/**
	 * @Title: toString
	 * @Description: toString
	 * @param arr
	 * @return String
	 */
	public static String toString(String[] arr) {
		return toString(arr, ",");
	}
	
	/**
	 * @Title: toString
	 * @Description: toString
	 * @param arr
	 * @param sep
	 * @return String
	 */
	public static String toString(String[] arr, String sep) {
		if(arr == null) {
            return "";
        }
		String s = "";
		for(int i=0; i<arr.length; i++){
			s += arr[i] + (i<arr.length-1 ? sep : "");
		}
		return s;
	}
	
	/**
	 * @Title: toLong
	 * @Description: toLong
	 * @param value
	 * @param defaultValue
	 * @return Long
	 */
	public static Long toLong(Object value, Long defaultValue) {
        	if (StringHelper.isEmpty(value)) {
                return defaultValue;
            }
            return Long.valueOf(toString(value));
	}
	
	/**
	 * @Title: toLong
	 * @Description: toLong
	 * @param value
	 * @return Long
	 */
	public static Long toLong(Object value) {
		return toLong(value, null);
	}
	
	/**
	 * @Title: toDouble
	 * @Description: toDouble
	 * @param value
	 * @param defaultValue
	 * @return Double
	 */
	public static Double toDouble(Object value, Double defaultValue) {
        	if (StringHelper.isEmpty(value)) {
                return defaultValue;
            }
            return Double.valueOf(toString(value));
	}
	
	/**
	 * @Title: toDouble
	 * @Description: toDouble
	 * @param value
	 * @return Double
	 */
	public static Double toDouble(Object value) {
		return toDouble(value, null);
	}
	
	/**
	 * @Title: toInteger
	 * @Description: toInteger
	 * @param value
	 * @param defaultValue
	 * @return Integer
	 */
	public static Integer toInteger(Object value, Integer defaultValue) {
        	if (StringHelper.isEmpty(value)||"null".equals(value)) {
                return defaultValue;
            }
            return Integer.valueOf(toString(value));
	}
	
	/**
	 * @Title: toInteger
	 * @Description: toInteger
	 * @param value
	 * @return Integer
	 */
	public static Integer toInteger(Object value) {
		return toInteger(value, null);
	}
	
	/**
	 * @Title: toFloat
	 * @Description: toFloat
	 * @param value
	 * @param defaultValue
	 * @return Float
	 */
    public static Float toFloat(Object value, Float defaultValue) {
        	if (StringHelper.isEmpty(value)) {
                return defaultValue;
            }
            return Float.valueOf(toString(value));
    }

	/**
	 * @Title: toFloat
	 * @Description: toFloat
	 * @param value
	 * @return Float
	 */
    public static Float toFloat(Object value) {
        return toFloat(value, null);
    }
    
    /**
     * @Title: toInt
     * @Description: toInt
     * @param value
     * @param defaultValue
     * @return int
     */
    public static int toInt(Object value, int defaultValue) {
        if (value instanceof Integer) {
            return ((Integer) value).intValue();
        }
        else if (value instanceof Long) {
            return ((Long) value).intValue();
        }
        else {
          return toInteger(value + "", defaultValue);
        }
    }
    
    /**
     * @Title: toInt
     * @Description: toInt
     * @param value
     * @return int
     */
    public static int toInt(Object value) {
        return toInt(value, 0);
    }
	/**
	 * @Title: isArray
	 * @Description: 是否数组
	 * @param value
	 * @return
	 */
	public static boolean isArray(Object value) {
		return value != null && value.getClass().isArray();
	}
	
	/**
	 * @Title: isArray
	 * @Description: 是否List
	 * @param value
	 * @return
	 */
	public static boolean isList(Object value) {
		return value instanceof List<?>;
	}
	
	/**
	 * @Title: isMap
	 * @Description: 是否Map
	 * @param value
	 * @return
	 */
	public static boolean isMap(Object value) {
		return value instanceof Map;
	}
	
	/**
	 * @Title: isNumber
	 * @Description: 判断是否是number型
	 * @param value
	 * @return
	 */
	public static boolean isNumber(Object value) {
		if (value == null) {
            return false;
        }
		if ((value instanceof Number)) {
			return true;
		}
		if ((value instanceof String)) {
			try {
				Double.parseDouble((String) value);
				return true;
			} catch (NumberFormatException e) {
				return false;
			}
		}
		return false;
	}
	/**
	 * @Title: isPrimitive
	 * @Description: 是否基础类型
	 * @param value
	 * @return
	 */
	public static boolean isPrimitive(Object value) {
		return value == null || value instanceof Comparable;
	}
}
