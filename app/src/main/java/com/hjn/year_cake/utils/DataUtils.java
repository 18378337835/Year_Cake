package com.hjn.year_cake.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import utilpacket.utils.LogUtils;

/**
 * Created by ${templefck} on 2018/9/10.
 */
public class DataUtils {
    public static String getDateD(String data){

        String dd            = null;
        SimpleDateFormat OldDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat NewFormat     = new SimpleDateFormat("dd");
        try {
            dd=NewFormat.format(OldDateFormat.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dd;

    }

    public static String getDateD(String data, String sf1, String sf2){

        String dd = null;
        SimpleDateFormat OldDateFormat = new SimpleDateFormat(sf1);
        SimpleDateFormat NewFormat = new SimpleDateFormat(sf2);
        try {
            dd=NewFormat.format(OldDateFormat.parse(data));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dd;

    }



    public static String judgeTime(String start_time, String end_time){
        String str = "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try{
            Date startDate = sdf.parse(start_time);
            Date endDate   = sdf.parse(end_time);
            Date nowDate   = new Date();
            if(endDate.before(startDate) || start_time.equals(end_time)){
                return "到期时间须大于开始时间";
            }
            if(endDate.before(nowDate) || end_time.equals(sdf.format(nowDate))){
                return "到期时间须大于当前时间";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }


    /**
     * 时间比较
     *
     * @param end_time
     * @param is 结束时间是否为当前时间
     * @return
     */
    public static boolean judgeEndTime(String start_time, String end_time, boolean is) {

        boolean isok = false;
        Date date2;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date date1 = sdf.parse(start_time);
            if (is){
                date2 = sdf.parse(sdf.format(new Date()));
            }else {
                date2 = sdf.parse(end_time);
            }
            //时间比较，相等返回0，大于返回1，小于返回-1.
            if (date2.compareTo(date1) > 0) {
                isok = true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isok;
    }

    //字符串转时间戳
    public static String getTime(Date date, boolean isBegin){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        String timeString;
        if (isBegin){
            timeString = sdf1.format(date)+" 00:00:01";
        }else {
            timeString = sdf1.format(date)+" 23:59:59";
        }

        LogUtils.e("yxb---"+timeString);
        String timeStamp = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d;
        try{
            d = sdf.parse(timeString);
            long l = d.getTime();
            timeStamp = String.valueOf(l).substring(0,10);
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp;
    }

    /**
     * 日期转换
     * @param date
     * @param isBegin
     * @return
     */
    public static String changeDate(Date date, boolean isBegin){
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String timeString;
        if (isBegin){
            timeString = sdf1.format(date);
        }else {
            timeString = sdf1.format(date);
        }
        return timeString;
    }

    /**
     * 字符串转时间戳
     * @param date
     * @return
     */
    public static long formatDate(String date){
        long timeStamp = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date d;
        try{
            d = sdf.parse(date);
            timeStamp = d.getTime();
        } catch(ParseException e){
            e.printStackTrace();
        }
        return timeStamp / 1000;
    }

}
