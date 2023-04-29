package com.bigdata.omp;

import junit.framework.TestCase;
import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;
import org.junit.Test;

public class TestAddTest extends TestCase {

    @Test
    public void test_selectUserWithCards() {
        System.out.println("testcase success!~~~~");
    }




    @Test
    public void test(){
        StandardPBEStringEncryptor standardPBEStringEncryptor = new StandardPBEStringEncryptor();
        //盐值
        standardPBEStringEncryptor.setPassword("123");
        //加密明文
        String mcode = standardPBEStringEncryptor.encrypt("mysql");


        System.out.println(mcode);
        //第二次test,解密第一次test的密文
        System.out.println(standardPBEStringEncryptor.decrypt(mcode));
        System.out.println("code=" + mcode);
    }

}
