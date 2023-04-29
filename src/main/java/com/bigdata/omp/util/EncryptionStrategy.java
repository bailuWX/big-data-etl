package com.bigdata.omp.util;

/**
 * 加密策略接口
 * 加密策略需要实现该接口才能被加密机调用
 * @author zxc
 * since 2017年11月23日
 */
public interface EncryptionStrategy
{
    /**
     * 加密
     * @param content
     * @return
     */
    public String encrypt(String content);
    /**
     * 解密
     * @param content
     * @return
     */
    public String decrypt(String content);
}
