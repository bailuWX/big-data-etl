package com.bigdata.omp.util;

/**
 * 加密机 可以设置不同的加密算法
 * 
 * @author zxc since 2017年11月23日
 */
public class Encryptor
{
    /**
     * 加密策略
     */
    private EncryptionStrategy encryptionStrategy = null;
    
    public Encryptor(EncryptionStrategy encryptionStrategy)
    {
        super();
        this.encryptionStrategy = encryptionStrategy;
    }
    
    public String decrypt(String content)
    {
        return encryptionStrategy.decrypt(content);
    }
    
    public String encrypt(String content)
    {
        
        return encryptionStrategy.encrypt(content);
    }
    
/*    public static void main(String[] args)
    {
        final Encryptor encryptor = new Encryptor(new AESEncryptionStrategy());
        String[] encryptArr =
            new String[] {"Encrypted 2be98afc86aa7f2e4cb79be65e197bbd6", "Encrypted 2be98afc86aa7f2e4cb79ce10bec3fe8b",};
        for (String string : encryptArr)
        {
            // 解密
            String encryptStr = encryptor.decrypt(string);
            // 加密
            // String encryptStr = encryptor.encrypt(string);
            System.out.println(encryptStr);
        }
    }*/
}
