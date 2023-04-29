package com.bigdata.omp.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by blwx on 2020/2/20.
 */
public class FileUtil {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 私有的对象
    private static FileUtil fileUtil;

    /**
     * 私有的构造方法
     */
    private FileUtil() {
    }

    public static FileUtil getInstance() {
        if (fileUtil == null) {
            fileUtil = new FileUtil();
        }
        return fileUtil;
    }

    //源路径
    private static String srcPath = "G:\\test\\";

    //文件名称
    private static String fileName = "";

    //规则组名称
    private static String ruleGroupName = "";

    //规则名称
    private static String ruleName = "";

    //告警规则
    private static String ruleExpress = "";

    //持续周期
    private static Integer ruleDurationType = 0;

    //告警标题
    private static String ruleSummary ="";

    //告警详情
    private String ruleDescription = "";

    //规则id
    private long ruleId = 0L;

    /**
     * 构造方法,方便直接传入参数进行调用
     *
     */
    public FileUtil(String srcPath, String fileName, String ruleGroupName, String ruleName,
                    String ruleExpress, Integer ruleDurationType, String ruleSummary, String ruleDescription, long ruleId) {
        super();
        this.srcPath = srcPath;
        this.fileName = fileName;
        this.ruleGroupName = ruleGroupName;
        this.ruleName = ruleName;
        this.ruleExpress = ruleExpress;
        this.ruleDurationType = ruleDurationType;
        this.ruleSummary = ruleSummary;
        this.ruleDescription = ruleDescription;
        this.ruleId = ruleId;
    }

    public Boolean createFlie(){
        Boolean success = false;
        File file = new File(srcPath);

        //文件路径不存在则生成
        if(!file.exists()){
            file.mkdir();
        }
        try{//异常处理
            //如果file文件夹下没有test.txt就会创建该文件
            BufferedWriter bw =new BufferedWriter(new FileWriter(srcPath+fileName));
            String N = "\n";
            StringBuffer result = new StringBuffer();
            result.append("groups:"+N);
            result.append("- name: "+ruleGroupName+N);
            result.append("  rules:"+N);
            result.append("  - alert: "+ruleName+N);
            result.append("    expr: "+ruleExpress+N);
            result.append("    for: "+ruleDurationType+"m"+N);
            result.append("    labels:"+N);
            result.append("      ruleId: "+ruleId+N);
            result.append("    annotations:"+N);
            result.append("      summary: "+"\""+ruleSummary+"\""+N);
            result.append("      description: "+"\""+ruleDescription+"\""+N);

            bw.write(result.toString());//在创建好的文件中写入"Hello"
            bw.close();//一定要关闭文件
            logger.info("生成文件【"+fileName+"】成功！");
            logger.info("》》》文件内容：");
            logger.info("\n"+result.toString());
            success = true;
        }catch(IOException e) {
            e.printStackTrace();
            logger.info("生成文件【"+fileName+"】失败！");
        }

        return success;
    }



    public static void main(String[] args){
        FileUtil fileutil = new FileUtil("G:\\test2\\","test2.yml","example","InstanceDown","up == 0",1,"Instance {{ $labels.instance }} down","{{ $labels.instance }} of job {{ $labels.job }} has been down for more than 1 minutes.",0L);
        Boolean success = fileutil.createFlie();
    }

    public static FileUtil getFileUtil() {
        return fileUtil;
    }

    public static void setFileUtil(FileUtil fileUtil) {
        FileUtil.fileUtil = fileUtil;
    }

    public static String getSrcPath() {
        return srcPath;
    }

    public static void setSrcPath(String srcPath) {
        FileUtil.srcPath = srcPath;
    }

    public static String getFileName() {
        return fileName;
    }

    public static void setFileName(String fileName) {
        FileUtil.fileName = fileName;
    }

    public String getRuleGroupName() {
        return ruleGroupName;
    }

    public void setRuleGroupName(String ruleGroupName) {
        this.ruleGroupName = ruleGroupName;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public String getRuleExpress() {
        return ruleExpress;
    }

    public void setRuleExpress(String ruleExpress) {
        this.ruleExpress = ruleExpress;
    }

    public static Integer getRuleDurationType() {
        return ruleDurationType;
    }

    public static void setRuleDurationType(Integer ruleDurationType) {
        FileUtil.ruleDurationType = ruleDurationType;
    }

    public long getRuleId() {
        return ruleId;
    }

    public void setRuleId(long ruleId) {
        this.ruleId = ruleId;
    }

    public String getRuleSummary() {
        return ruleSummary;
    }

    public void setRuleSummary(String ruleSummary) {
        this.ruleSummary = ruleSummary;
    }

    public String getRuleDescription() {
        return ruleDescription;
    }

    public void setRuleDescription(String ruleDescription) {
        this.ruleDescription = ruleDescription;
    }
}
