package com.bigdata.omp.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author wangyi
 * @date 2020/3/20
 */
public class DateUtil {

    /**
     * .Description://根据字符日期返回星期几
     * .Author:麦克劳林
     * .@Date: 2018/12/29
     */
    public static String getWeek(String dateTime){
        String week = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(dateTime);
            SimpleDateFormat dateFm = new SimpleDateFormat("EEEE");
            week = dateFm.format(date);
            week = week.replaceAll("星期","周");
        }catch (ParseException e){
            e.printStackTrace();
        }
        return week;
    }

    /**
     * 获取过去7天内的日期数组
     * @param intervals intervals天内
     * @param dateStr 日期字符串，格式：yyyy-MM-dd
     * @return  日期数组
     */
    public static List<String> getDays(int intervals, String dateStr) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date = format.parse(dateStr);
        List<String> pastDaysList = new ArrayList<>();
        for (int i = intervals -1; i >= 0; i--) {
            pastDaysList.add(getPastDate(i, date));
        }
        return pastDaysList;
    }
    /**
     * 获取过去第几天的日期
     * @param past 过去第几天
     * @param date 日期对象
     * @return 日期
     */
    public static String getPastDate(int past, Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    public static void main(String[] args) throws ParseException {
        String dateStr = "2020-03-18";
        List<String> days = getDays(7, dateStr);
        System.out.println(days);
        String week = getWeek("2020-03-16");
        System.out.println(week);
    }
}
