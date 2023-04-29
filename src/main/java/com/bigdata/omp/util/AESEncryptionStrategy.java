package com.bigdata.omp.util;


import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * AES加密策略的实现类
 */
public class AESEncryptionStrategy implements EncryptionStrategy
{
    private final static String pwd = "etl-hfdept4";
    
    /**
     * 数据库密码加密
     * 
     * @param pwd
     * @return
     */
    @Override
    public String encrypt(String pwd)
    {
        if (pwd == null || pwd.length() == 0)
        {
            return "";
        }
        byte[] encryptResult = encrypt(pwd, pwd);
        if (encryptResult == null)
        {
            return "";
        }
        return new String(parseByte2HexStr(encryptResult));
    }
    
    /**
     * 解密,解密失败则返回原文
     * 
     * @param content
     * @return 解密后的字符串或未解密成功的输入
     */
    @Override
    public String decrypt(String content)
    {
        try{
            byte[] decryptFrom = parseHexStr2Byte(content);  
            byte[] decryptResult = decrypt(decryptFrom,pwd);
            return new String(decryptResult) ;
        }catch (Exception e){
            try{
                //兼容老Encr的密文
                return Encr.decryptPasswordOptionallyEncrypted(content);
            }catch(Exception ex)
            {
                return content;
            }
        }
    }
    
    /**
     * 加密
     * 
     * @param content 需要加密的内容
     * @param password 加密密码
     * @return
     **/
    public static byte[] encrypt(String content, String password)
    {
        
        try
        {
            
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器
            byte[] byteContent = content.getBytes("utf-8");
            cipher.init(Cipher.ENCRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(byteContent);
            return result; // 加密
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 解密
     * 
     * @param content 待解密内容
     * @param password 解密密钥
     * @return
     **/
    public static byte[] decrypt(byte[] content, String password)
    {
        try
        {
            
            KeyGenerator kgen = KeyGenerator.getInstance("AES");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            random.setSeed(password.getBytes());
            kgen.init(128, random);
            SecretKey secretKey = kgen.generateKey();
            byte[] enCodeFormat = secretKey.getEncoded();
            SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/NoPadding");// 创建密码器
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始化
            byte[] result = cipher.doFinal(content);
            return result; // 加密
        }
        catch (NoSuchAlgorithmException e)
        {
            e.printStackTrace();
        }
        catch (NoSuchPaddingException e)
        {
            e.printStackTrace();
        }
        catch (InvalidKeyException e)
        {
            e.printStackTrace();
        }
        catch (IllegalBlockSizeException e)
        {
            e.printStackTrace();
        }
        catch (BadPaddingException e)
        {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * 将二进制转换成16进制
     * 
     * @param buf
     * @return
     **/
    public static String parseByte2HexStr(byte buf[])
    {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++)
        {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1)
            {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }
    
    /**
     * 将16进制转换为二进制
     * 
     * @param hexStr
     * @return
     **/
    public static byte[] parseHexStr2Byte(String hexStr)
    {
        if (hexStr.length() < 1)
        {
            return null;
        }
        byte[] result = new byte[hexStr.length() / 2];
        for (int i = 0; i < hexStr.length() / 2; i++)
        {
            int high = Integer.parseInt(hexStr.substring(i * 2, i * 2 + 1), 16);
            int low = Integer.parseInt(hexStr.substring(i * 2 + 1, i * 2 + 2), 16);
            result[i] = (byte)(high * 16 + low);
        }
        return result;
    }
    
}
