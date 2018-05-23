package com.jinanlongen.sparrow.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 
 * @description
 * @author shangyao
 * @date 2018年3月22日
 */
public class DateUtils {

  public static Date getDate(String date) {
    String dataformat = "yyyy-MM-dd hh-mm-ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataformat);
    Date dates = null;
    try {
      dates = simpleDateFormat.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return dates;
  }

  public static Date getDate2(String date) {
    String dataformat = "yyyy-MM-dd hh:mm:ss";
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat(dataformat);
    Date dates = null;
    try {
      dates = simpleDateFormat.parse(date);
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return dates;
  }

  /*
   * 将时间戳转换为时间
   */
  public static String stampToDate(String s) {
    String res;
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    long lt = new Long(s);
    Date date = new Date(lt);
    res = simpleDateFormat.format(date);
    return res;
  }

  public static String dateString() {
    LocalDateTime time = LocalDateTime.now();
    return "" + time.getYear() + time.getMonth().getValue() + time.getDayOfMonth() + time.getHour()
        + time.getMinute() + time.getSecond();
  }
}
