package com.bigdata.omp.util;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import com.trilead.ssh2.StreamGobbler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class RmtShellExecutor {
	private static final Logger logger = LoggerFactory.getLogger(RmtShellExecutor.class);
    private Connection connection;
    private Session session;
    /** 远程机器IP */
    private String ip;
    /** 用户名 */
    private String user;
    /** 密码 */
    private String password;

	private int	port = 22;

    private static final int DEFAULT_PORT = 22;

    protected StringBuilder stdOut; //标准正确输出
    protected StringBuilder stdErr; //标准错误输出
    
    public RmtShellExecutor(String ip, String user, String password) {
        this(ip, DEFAULT_PORT, user, password);
    }

	public RmtShellExecutor(String ip, int port, String user, String password) {
        logger.info("ip: " + ip + ", port: " + port + ", user: " + user + ", password: " + password);
		this.ip = ip;
		this.user = user;
		this.password = password;
		this.port = port;
	}

    /**
     * 登录远程主机
     */
	private boolean login() throws Exception {
		connection = new Connection(ip);
        connection.connect();
        boolean authenticateWithPassword = connection.authenticateWithPassword(user, password);
        if (!authenticateWithPassword) {
            throw new RuntimeException("Authentication failed. Please check hostName, userName and passwd");
        }
		return authenticateWithPassword;
    }

    /**
     * 执行脚本
     * @param cmds 命令
     * @return 脚本执行结果
     */
    public Map<String, String> exec(String cmds) {
        stdOut = new StringBuilder();
        stdErr = new StringBuilder();
        try {
            if (login()) {
            	session = connection.openSession();
                session.requestDumbPTY();
                session.startShell();
                OutputStream out = session.getStdin();
                out.write((cmds + "\n").getBytes(StandardCharsets.UTF_8));
                out.write("exit \n".getBytes(StandardCharsets.UTF_8));
//                Thread.sleep(2000);
                BufferedReader stdoutReader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStdout()), StandardCharsets.UTF_8));
                BufferedReader stderrReader = new BufferedReader(new InputStreamReader(new StreamGobbler(session.getStderr()), StandardCharsets.UTF_8));
    			String line = "";
    			while ((line = stdoutReader.readLine()) != null) {
                    stdOut.append(line + "\n");
    			}
    			
    			while ((line = stderrReader.readLine()) != null) {
    				stdErr.append(line + "\n");
    			}
    			out.flush();
    			out.close();
    			stdoutReader.close();
    			stderrReader.close();
    			session.close();
            } else {
            	logger.error("登录远程机器失败" + ip);
                stdErr.append("登录远程机器失败" + ip + "<br>");
            }
        } catch(Exception e) {
            logger.error("远程命令执行失败：", e);
        } finally {
            if (session != null) {
                session.close();
            }
            if(connection != null) {
            	connection.close();
            }
        }

        Map<String, String> result = new HashMap<>(2);
        result.put("stdOut", stdOut.toString());
        result.put("stdErr", stdErr.toString());
        return result;
    }

    /**
     * 执行脚本
     * @param cmds 命令
     */
    public void execute(String cmds) {
        try {
            if (login()) {
                session = connection.openSession();
                session.requestDumbPTY();
                session.startShell();
                OutputStream out = session.getStdin();
                out.write((cmds + "\n").getBytes(StandardCharsets.UTF_8));
                out.write("exit \n".getBytes(StandardCharsets.UTF_8));
                Thread.sleep(2000);
                out.flush();
                out.close();
                session.close();
            } else {
                logger.error("登录远程机器失败" + ip);
            }
        } catch(Exception e) {
            logger.error("远程命令执行失败：", e);
        } finally {
            if (session != null) {
                session.close();
            }
            if(connection != null) {
                connection.close();
            }
        }
    }

    public void cancel() {
    	connection.close();
    }
}
