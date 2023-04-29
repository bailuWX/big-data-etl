package com.bigdata.omp.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Vector;

import cn.hutool.core.util.StrUtil;
import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import com.jcraft.jsch.SftpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by blwx on 2020/2/20.
 * * 说明:exec用于执行命令;sftp用于文件处理
 */
public class SSHRemoteCall {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    // 私有的对象
    private static SSHRemoteCall sshRemoteCall;

    /**
     * 私有的构造方法
     */
    private SSHRemoteCall() {
    }

    // 懒汉式,线程不安全,适合单线程
    public static SSHRemoteCall getInstance() {
        if (sshRemoteCall == null) {
            sshRemoteCall = new SSHRemoteCall();
        }
        return sshRemoteCall;
    }

    // 懒汉式,线程安全,适合多线程
    public static synchronized SSHRemoteCall getInstance2() {
        if (sshRemoteCall == null) {
            sshRemoteCall = new SSHRemoteCall();
        }
        return sshRemoteCall;
    }

    private static final int DEFAULT_PORT = 22;// 默认端口号
    private int port;// 端口号

    private static String ipAddress = "192.168.129.137";// ip地址
    private static String userName = "root";// 账号
    private static String password = "bigdata123";// 密码

    private Session session;// JSCH session
    private boolean logined = false;// 是否登陆

    /**
     * 构造方法,可以直接使用DEFAULT_PORT
     *
     * @param ipAddress
     * @param userName
     * @param password
     */
    public SSHRemoteCall(String ipAddress, String userName, String password) {
        this(ipAddress, DEFAULT_PORT, userName, password);
    }

    /**
     * 构造方法,方便直接传入ipAddress,userName,password进行调用
     *
     * @param ipAddress
     * @param port
     * @param userName
     * @param password
     */
    public SSHRemoteCall(String ipAddress, int port, String userName, String password) {
        super();
        this.ipAddress = ipAddress;
        this.userName = userName;
        this.password = password;
        this.port = port;
    }

    /**
     * 远程登陆
     *
     * @throws Exception
     */
    public void sshRemoteCallLogin(String ipAddress, int port, String userName, String password) throws Exception {

        // 创建jSch对象
        JSch jSch = new JSch();
        try {
            // 获取到jSch的session, 根据用户名、主机ip、端口号获取一个Session对象
            session = jSch.getSession(userName, ipAddress, port);
            // 设置密码
            session.setPassword(password);

            // 方式一,通过Session建立连接
            // session.setConfig("StrictHostKeyChecking", "no");
            // session.connect();

            // 方式二,通过Session建立连接
            // java.util.Properties;
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);// 为Session对象设置properties
            // session.setTimeout(3000);// 设置超时
            session.connect();//// 通过Session建立连接

        } catch (JSchException e) {
            // 设置登陆状态为false
            logined = false;
            throw new Exception(
                    "主机登录失败, IP = " + ipAddress + ", USERNAME = " + userName + ", Exception:" + e.getMessage());
        }
    }

    /**
     * 关闭连接
     */
    public void closeSession() {
        // 调用session的关闭连接的方法
        if (session != null) {
            // 如果session不为空,调用session的关闭连接的方法
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    private static void closeChannel(Channel channel) {
        if (channel != null) {
            if (channel.isConnected()) {
                channel.disconnect();
            }
        }
    }

    /**
     * 执行相关的命令
     *
     * @param command
     * @throws IOException
     */
    public void execCommand(String command) throws IOException {
        InputStream in = null;// 输入流(读)
        Channel channel = null;// 定义channel变量
        try {
            // 如果命令command不等于null
            if (command != null) {
                // 打开channel
                //说明：exec用于执行命令;sftp用于文件处理
                channel = session.openChannel("exec");
                // 设置command
                ((ChannelExec) channel).setCommand(command);
                // channel进行连接
                channel.connect();
                // 获取到输入流
                in = channel.getInputStream();
                // 执行相关的命令
                String processDataStream = processDataStream(in);
                // 打印相关的命令
                System.out.println("1、打印相关返回的命令: " + processDataStream);
            }
        } catch (JSchException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                in.close();
            }
            if (channel != null) {
                channel.disconnect();
            }
        }

    }

    /**
     * 对将要执行的linux的命令进行遍历
     *
     * @param in
     * @return
     * @throws Exception
     */
    public String processDataStream(InputStream in) throws Exception {
        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String result = "";
        try {
            while ((result = br.readLine()) != null) {
                sb.append(result);
                // System.out.println(sb.toString());
            }
        } catch (Exception e) {
            throw new Exception("获取数据流失败: " + e);
        } finally {
            br.close();
        }
        return sb.toString();
    }

    /**
     * 上传文件 可参考:https://www.cnblogs.com/longyg/archive/2012/06/25/2556576.html
     *
     * @param directory
     *            上传文件的目录
     * @param uploadFile
     *            将要上传的文件
     */
    public void uploadFile(String directory, String fileName, String uploadFile) throws JSchException {
        // 打开channelSftp
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        boolean success = false;
        try {
            logger.info("目录【"+directory+"】下，上传文件："+fileName);

            // 远程连接
            channelSftp.connect();
            // 创建一个文件名称问uploadFile的文件
            File file = new File(uploadFile);
            // 将文件进行上传(sftp协议)
            // 将本地文件名为src的文件上传到目标服务器,目标文件名为dst,若dst为目录,则目标文件名将与src文件名相同.
            // 采用默认的传输模式:OVERWRITE
            channelSftp.put(new FileInputStream(file), directory + fileName, ChannelSftp.OVERWRITE);
            success = true;
        } catch (SftpException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            // 切断远程连接
            channelSftp.exit();
            if(success){
                logger.info("文件【"+fileName+"】上传成功！");
            }else{
                logger.info("文件【"+fileName+"】上传失败！");
            }
            closeChannel(channelSftp);
        }

    }

    /**
     * 下载文件 采用默认的传输模式：OVERWRITE
     *
     * @param src
     *            linux服务器文件地址
     * @param dst
     *            本地存放地址
     * @throws JSchException
     * @throws SftpException
     */
    public void fileDownload(String src, String dst) throws JSchException, SftpException {
        // src 是linux服务器文件地址,dst 本地存放地址
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();
        // 下载文件,多个重载方法
        channelSftp.get(src, dst);
        // 切断远程连接,quit()等同于exit(),都是调用disconnect()
        channelSftp.quit();
        // channelSftp.disconnect();
        System.out.println("3、" + src + " ,下载文件成功.....");
    }

    /**
     * 删除文件
     *
     * @param directory
     *            要删除文件所在目录
     * @param deleteFile
     *            要删除的文件
     * @param sftp
     * @throws SftpException
     * @throws JSchException
     */
    public boolean deleteFile(String deletDirectory, String deletFileName) throws JSchException {

        logger.info("删除目录【"+deletDirectory+"】下，文件："+deletFileName);
        // 打开openChannel的sftp
        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        boolean success = false;
        try {
            // 远程连接
            channelSftp.connect();
            boolean flag = openDir(deletDirectory, channelSftp);
            if (flag) {
                //进入目录
                channelSftp.cd(deletDirectory);
                // 删除文件
                channelSftp.rm(deletFileName);
            } else {
                logger.error("对应的目录" + deletDirectory + "不存在！");
            }
            success = true;
        }catch (Exception e){

        }finally {
            if(success){
                logger.info("文件【"+deletFileName+"】删除成功！");
            }else{
                logger.info("文件【"+deletFileName+"】删除失败！");
            }
            closeChannel(channelSftp);
            return success;
        }

    }

    /**
     * 列出目录下的文件
     *
     * @param directory
     *            要列出的目录
     */
    public List<String> getSftpPathList(String directory) throws JSchException, SftpException {
        List<String> fileList = new ArrayList<>();

        ChannelSftp channelSftp = (ChannelSftp) session.openChannel("sftp");
        // 远程连接
        channelSftp.connect();

        boolean flag = openDir(directory,channelSftp);
        logger.info("获取对应的目录【"+directory+"】下文件！");
        if(flag){
            Vector<?> vv = channelSftp.ls(directory);
            if(vv == null || vv.size() == 0){
                logger.info("对应的目录【"+directory+"】下无文件！");
                return null;
            }else{
                for(Object object : vv){
                    ChannelSftp.LsEntry entry=(ChannelSftp.LsEntry)object;
                    String filename=entry.getFilename();
                    if(".".equals(filename) || "..".equals(filename)){
                        continue;
                    }
                    if(!StrUtil.isEmpty(filename)){
                        fileList.add(filename);
                    }
                }
            }
        }else{
            logger.error("对应的目录"+directory+"不存在！");
        }
        logger.info("文件列表："+fileList.toString());
        // 切断连接
        channelSftp.exit();

        return fileList;
    }

    public static boolean openDir(String directory,ChannelSftp sftp) throws SftpException {
        try{
            sftp.cd(directory);
            return true;
        }catch(SftpException e){
            sftp.mkdir(directory);
            return false;
        }
    }

    public static void main(String[] args) {
        // 连接到指定的服务器
        try {
            // 1、首先远程连接ssh
            SSHRemoteCall.getInstance().sshRemoteCallLogin(ipAddress, DEFAULT_PORT, userName, password);
            // 打印信息
            System.out.println("0、连接远程服务器,ip地址: " + ipAddress + ",账号: " + userName + ",连接成功.....");

            // 2、执行相关的命令
            // 查看目录信息
            // String command = "ls /home/hadoop/package ";
            // 查看文件信息
            // String command = "cat /home/hadoop/package/test ";
            // 查看磁盘空间大小
            // String command = "df -lh ";
            // 查看cpu的使用情况
            // String command = "top -bn 1 -i -c ";
            // 查看内存的使用情况
//            String command = "free ";
//            SSHRemoteCall.getInstance().execCommand(command);

            // 3、上传文件
            String directory = "/usr/docker/prometheus-data/rules_test1/";// 目标文件夹路径
            String fileName = "test.txt";
            String uploadFile = "G:\\test.txt";// 本地文件名
            SSHRemoteCall.getInstance().uploadFile(directory, fileName , uploadFile);

            // 4、下载文件
            // src 是linux服务器文件地址,dst 本地存放地址,采用默认的传输模式：OVERWRITE
            //test为文件名称哈
//            String src = "/home/hadoop/package/test";
//            String dst = "E:\\";
//            SSHRemoteCall.getInstance().fileDownload(src, dst);


            // 5、刪除文件
            String deleteDirectory = "/usr/docker/prometheus-data/rules_test1/";
            String deleteFileName = "test.txt";
            SSHRemoteCall.getInstance().deleteFile(deleteDirectory,deleteFileName);

            // 6、展示目录下的文件信息
            String lsDirectory = "/usr/docker/prometheus-data/rules_test1/";
            SSHRemoteCall.getInstance().getSftpPathList(lsDirectory);

            // 7、关闭连接
            SSHRemoteCall.getInstance().closeSession();
        } catch (Exception e) {
            // 打印错误信息
            System.err.println("远程连接失败......");
            e.printStackTrace();
        }
    }
}

