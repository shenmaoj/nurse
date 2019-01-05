package com.cmnt.nurse.common.utils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * @(#)  DateUtils
 *      <p>
 * @版权： 空间时间文化传播有限公司
 *      <p>
 * @描述： 日期帮助类
 *      <p>
 * @author 陈伟
 * @version ST_V1.0
 * @createDate 2016年5月14日
 * @see
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils {
	public static final long MILLISECOND_DAY = 86400000L;
	/** yyyy-MM-dd **/
	public static final String PATTEN_DATE_FORMAT_DEFAULT = "yyyy-MM-dd";
	/** yyyy/MM/dd **/
	public static final String PATTEN_DATE_FORMAT_SLASH = "yyyy/MM/dd";
	/** yyyy-MM-dd HH:mm:ss **/
	public static final String PATTEN_DATE_FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";
	/** yyyy-MM-dd HH:mm:ss:SSS **/
	public static final String PATTEN_DATE_FORMAT_DATETIME_PLUS = "yyyy-MM-dd HH:mm:ss:SSS";
	/** yyyyMMdd **/
	public static final String PATTEN_DATE_FORMAT_MAC = "yyyyMMdd";
	
	public static final String PATTEN_DATE_FORMAT_BOOKING = "HHmm";
	
	public static final String PATTEN_DATE_FORMAT_SECKILL = "HHmmss";
	
	public static final String PATTEN_DATE_FORMAT_NETTY = "yyyyMMddHHmmss";

	public static Date getNow() {
		return new Date();
	}

	public static Date currentDateTime() {
		return getNow();
	}

	public static String dateToString(Date date, String format) {
		if (date == null)
			return "";
		return new SimpleDateFormat(format).format(date);
	}

	public static String bookingDateToString(Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(PATTEN_DATE_FORMAT_BOOKING).format(date);
	}
	
	public static String seckillDateToString(Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(PATTEN_DATE_FORMAT_SECKILL).format(date);
	}
	
	public static String sqlDateToString(Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(PATTEN_DATE_FORMAT_DATETIME).format(date);
	}
	
	public static String TablesBookingDateToString(Date date) {
		if (date == null)
			return "";
		return new SimpleDateFormat(PATTEN_DATE_FORMAT_MAC).format(date);
	}
	
	public static String dateToString(Date date) {
		return dateToString(date, "yyyy-MM-dd");
	}

	public static Date stringToDate(String dateString, String pattern) {

		SimpleDateFormat df = new SimpleDateFormat();
		df.applyPattern(pattern);

		return df.parse(dateString, new ParsePosition(0));
	}

	public static Date stringToDate(String dateString) {
		return stringToDate(dateString, "yyyy-MM-dd");
	}
	
	/**
	 * @description 计算某个时间距今有多少天
	 * @author HuangJ
	 * @param begin
	 * @return
	 */
	public static int diff(Date begin){
		Calendar cal = Calendar.getInstance();
		cal.setTime(begin);
		long timesThat = cal.getTimeInMillis();
		cal.setTime(new Date());
		long timesNow = cal.getTimeInMillis();
		long dif = (timesNow - timesThat) / (1000 * 60 * 60 * 24);
		return (int)dif;
	}
	
	/**
	 *  计算某个时间距今有多少天,格式转换时去掉时分秒
	 * @description
	 * @author 陈伟
	 * @createDate  2016年5月31日
	 * @param begin
	 * @return
	 * @version ST_V1.0
	 * 
	 */
	public static int diffDays(Date begin){
		String beginTime =  dateToString(begin, PATTEN_DATE_FORMAT_DEFAULT);
		String nowDate =  dateToString(new Date(), PATTEN_DATE_FORMAT_DEFAULT);
		
		Calendar cal = Calendar.getInstance();
		cal.setTime(DateUtils.stringToDate(beginTime, DateUtils.PATTEN_DATE_FORMAT_DEFAULT));
		long timesThat = cal.getTimeInMillis();
		cal.setTime(DateUtils.stringToDate(nowDate, DateUtils.PATTEN_DATE_FORMAT_DEFAULT));
		long timesNow = cal.getTimeInMillis();
		long dif = (timesNow - timesThat) / (1000 * 60 * 60 * 24);
		return (int)dif;
		
	}
	
	/**
	 * @description 某个时间距今多少天
	 * @author HuangJ
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static int diff(String date,String pattern){
		Date d = stringToDate(date, pattern);
		return	diff(d);
	}

	public static long dateDiff(Date now, Date then) {
		if (now == null) {
			now = get("00000000000000000");
		}
		if (then == null) {
			then = get("00000000000000000");
		}
		return now.getTime() - then.getTime();
	}

	public static int daysDiff(Date now, Date then) {
		return Integer.parseInt(String.valueOf(Math.abs(dateDiff(now, then) / 86400000L)));
	}

	@SuppressWarnings("deprecation")
	public static int monthsDiff(Date now, Date then) {
		return Math.abs(now.getMonth() - then.getMonth());
	}

	public static Date getDaysAfterOrBefore(Date date, int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);

		now.set(5, now.get(5) + days);
		return now.getTime();
	}

	private static Date get(String date) {
		String year = date.substring(0, 4);
		String month = date.substring(4, 6);
		String day = date.substring(6, 8);
		String hour = date.substring(8, 10);
		String minute = date.substring(10, 12);
		String second = date.substring(12, 14);
		String millisecond = date.substring(14, 17);

		Calendar c = Calendar.getInstance();
		c.set(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day), Integer.parseInt(hour),
				Integer.parseInt(minute), Integer.parseInt(second));
		c.set(14, Integer.parseInt(millisecond));
		return c.getTime();
	}

	public static Date getWeekdayInWeek(Date appointedDate, int weekday) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(appointedDate);

		int dayOfWeek = calendar.get(7);
		calendar.add(5, weekday - dayOfWeek);

		return calendar.getTime();
	}

	public static Date getPlainDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		cal.set(11, 0);
		cal.set(12, 0);
		cal.set(13, 0);
		cal.set(14, 0);

		return cal.getTime();
	}

	public static Date getPlainTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(1, 1970);
		cal.set(2, 0);
		cal.set(5, 1);

		return cal.getTime();
	}
	
	public static String getNewReplyTime(Date date){
		long time = date.getTime();
		long nowTime = new Date().getTime();
		int difference = (int) ((nowTime-time)/1000/60);
		if(difference==0){
			return "刚刚";
		}else if(difference < 60){
			return difference+"分钟前";
		}else if(difference > 60){
			int hour = difference/60;
			if(hour < 24){
				return hour+"小时前";
			}else{
				return dateToString(date, "yyyy-MM-dd HH:mm");
			}
		}
		return dateToString(date, "yyyy-MM-dd HH:mm");
	}
	/**
	 * 获取提前时间
	 * @author shenmj
	 *
	 * @param date 
	 * @param millisecond 提前多少s （小时，分，天先转换成秒再减）
	 * @return
	 */
	public static Date subDate(Date date,long millisecond){
		if(null == date){
			return null;
		}
		return new Date((date.getTime()-millisecond));
	}
	
	public static int getCurrentMonthTheDay(){  
	    Calendar date = Calendar.getInstance();  
	    date.set(Calendar.DATE, 1);
	    date.roll(Calendar.DATE, -1);
	    return date.get(Calendar.DATE);  
	}
	
	public static String addDay(int addDayNum){  
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
	    Calendar date = Calendar.getInstance();
	    date.add(Calendar.MONTH, 0);
	    date.set(Calendar.DAY_OF_MONTH,1);
	    date.add(Calendar.DAY_OF_MONTH, addDayNum);
		return sf.format(date.getTime());
	}

}