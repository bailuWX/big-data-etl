package com.bigdata.omp.util;



/**
 * 加密工具类
 * 
 * @author zxc 
 * @since 2017年11月23日
 */
public class EncryptorUtil
{
    /**
     * 加密机
     */
    private static final Encryptor encryptor = new Encryptor(new AESEncryptionStrategy());
    
    /**
     * 加密
     * 
     * @param content
     * @return
     */
    public static String encrypt(String content)
    {
        return encryptor.encrypt(content);
    }
    
    /**
     * 解密
     * 
     * @param encrypted
     * @return
     */
    public static String decrypt(String encrypted)
    {
        return encryptor.decrypt(encrypted);
    }
    
    public static void main(String[] args)
    {
    	String[] ps = {"2be98afc86aa7f28cae0da22586c7f9fa","2be98afc86ae7a8ac8a37b975d7c3fd89"};
	    for (int i = 0; i < ps.length; i++) {
	    	String encrypted = "Encrypted "+ps[i];
	        System.out.println(i+"\t"+ ps[i]+"\t"+EncryptorUtil.decrypt(encrypted));
		}
        System.out.println(EncryptorUtil.decrypt("A566935B8E546EAE256D174F02B40F1D"));
        System.out.println(EncryptorUtil.encrypt("5541855"));
        System.out.println(EncryptorUtil.decrypt("1B9C63CD3E2575151AA4E7287684996D"));
    }
}
