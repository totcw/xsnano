package com.betterda.xsnano.util;

import com.tencent.open.utils.SystemUtils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2016/7/7.
 */
public class TimeTool {

    private final static long yearLevelValue = 365*24*60*60*1000L ;
    private final static long monthLevelValue = 30*24*60*60*1000L ;
    private final static long dayLevelValue = 24*60*60*1000L ;
    private final static long hourLevelValue = 60*60*1000L ;
    private final static long minuteLevelValue = 60*1000L ;
    private final static long secondLevelValue = 1000L ;
    /**
     * 获取现在到结束日期的时间差
     *
     * @param
     * @return
     */
    public static long getTime(long timemillongs) {

        Date startDate = new Date(System.currentTimeMillis());

        return timemillongs - startDate.getTime();
    }

    public static String getDifference(long nowTime , long targetTime){//目标时间与当前时间计算
        long period = targetTime - nowTime ;
        return getDifference(period);
    }

    public static String getDifference(long period) {//根据毫秒差计算时间差
        String result = null ;


        /*******计算出时间差中的年、月、日、天、时、分、秒*******/
        int year = getYear(period) ;
        int month = getMonth(period - year*yearLevelValue) ;
        int day = getDay(period - year*yearLevelValue - month*monthLevelValue) ;
        int hour = getHour(period - year*yearLevelValue - month*monthLevelValue - day*dayLevelValue) ;
        int minute = getMinute(period - year*yearLevelValue - month*monthLevelValue - day*dayLevelValue - hour*hourLevelValue) ;
        int second = getSecond(period - year*yearLevelValue - month*monthLevelValue - day*dayLevelValue - hour*hourLevelValue - minute*minuteLevelValue) ;


        result = year+"年"+month+"月"+day+"天"+hour+"时"+minute+"分"+second+"秒";
        return result;
    }
    public static int getYear(long period){
        return (int) (period/yearLevelValue);
    }
    public static int getMonth(long period){
        return (int) (period/monthLevelValue);
    }
    public static int getDay(long period){
        return (int) (period/dayLevelValue);
    }
    public static int getHour(long period){
        return (int) (period/hourLevelValue);
    }
    public static int getMinute(long period){
        return (int) (period/minuteLevelValue);
    }
    public static int getSecond(long period){
        return (int) (period/secondLevelValue);
    }

    public static void main(String[] args) {
        /*****测试效果，这里的毫秒数是15天，3小时，50分，18秒，999毫秒的毫秒总数，只精确到秒，所以999毫秒舍去*****/
       // System.out.println(TimeTool.getDifference(1000*60*60*24*15+1000*60*60*3+1000*60*50+1000*18+999)) ;

    }
}
