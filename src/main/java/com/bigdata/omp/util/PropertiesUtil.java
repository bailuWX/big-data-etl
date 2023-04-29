package com.bigdata.omp.util;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 属性工具集
 * @author    liu.bosen
 * @date      2016年6月21日 上午10:10:32
 * @copyright 深圳天源迪科信息科技股份
 */
public class PropertiesUtil {
    private static Logger log = LoggerFactory.getLogger(PropertiesUtil.class);
    private static Properties properties = null; //初始化属性  采用单例模式

    /**
     * 单例模式，初始化属性文件加载
     * @return
     */
    private static Properties getProperties(){
        if(properties == null){
            properties = new Properties();
            log.info("开始加载配置文件");
            //多个配置文件，添加到propFiles中
            String[] propFiles = {"/config/sysPropertie.properties"};
            InputStreamReader is = null;
            for(int i = 0;i < propFiles.length;i++){
                try {
                    is = new InputStreamReader(PropertiesUtil.class.getResourceAsStream(propFiles[i]), "UTF-8");;
                    properties.load(is);
                    log.info("加载配置文件"+propFiles[i]+"完成");
                }catch(Exception e){
                    log.error("加载配置文件"+propFiles[i]+"失败");
                    throw new NullPointerException("Cann't load configFile");
                }
            }
            try {
                if(is != null){
                    is.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return properties;
    }

    /**
     * 根据key获取属性值
     * @param key
     * @return
     */
    public static String getValue(String key) {
        String value = getProperties().getProperty(key);
        if (StringUtils.isNotEmpty(value)) {
            return value;
        }
        return ""; //若属性无值或者无相关属性key，则返回空串
    }


    public static void main(String[] args) {
        System.out.println(PropertiesUtil.getValue("db.jdbc.driver"));
    }
}
