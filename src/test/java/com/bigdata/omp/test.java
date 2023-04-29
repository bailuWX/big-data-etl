package com.bigdata.omp;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by blwx on 2019/12/4.
 */
public class test {

    public static void main(String args[]) {
        Double timeStamp = 1575422614.888;
        System.out.println(timeStamp.longValue());
        String result1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(timeStamp.longValue() * 1000));
        System.out.println("10位数的时间戳（秒）--->Date:" + result1);
        System.out.println(System.currentTimeMillis());
        String result2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()));
        System.out.println("10位数的时间戳（秒）--->Date:" + result2);



    }

}
