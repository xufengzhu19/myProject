package utils;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xufengzhu
 */

public class DateBuilder {

    /**
     * 一年的毫秒数1
     */
    public static final long YEAR_MILLIS = 31104000000l;

    /**
     * 一月的毫秒数，30天。
     */
    public static final long MONTH_MILLIS = 2592000000l;

    /**
     * 一周的毫秒数
     */
    public static final long WEEK_MILLIS = 604800000l;

    /**
     * 一天的毫秒数
     */
    public static final long DAY_MILLIS = 86400000l;

    /**
     * 一小时的毫秒数
     */
    public static final long HOUR_MILLIS = 3600000l;

    /**
     * 一分钟的毫秒数
     */
    public static final long MINUTE_MILLIS = 60000;

    private static final Logger logger = LoggerFactory.getLogger(DateBuilder.class);
    private static DateBuilder instance;

    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYYMM = "yyyyMM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HHMM = "yyyy-MM-dd HH:mm";
    public static final String HH_MM_SS = "HH:mm:ss";
    public static final String FORMAT_DAY_BEGIN = "yyyy-MM-dd 00:00:00";
    public static final String FORMAT_DAY_END = "yyyy-MM-dd 23:59:59";
    public static final String MONTH = "yyyy年MM月";
    public static final String ERROR_TIP = "Date Operator Fail!";
    /**
     * 判断是否是日期+时间的正则表达式，如2013-01-23 15:07:47
     */
    private static final Pattern Pattern_DateTime = Pattern
            .compile("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$");
    private static ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT_LOCAL = new ThreadLocal();

    private DateBuilder() {
    }

    public static DateBuilder getInstance() {
        if (instance == null) {
            instance = new DateBuilder();
        }
        return instance;
    }

    /**
     * 获取指定格式的日期
     *
     * @param sFormat
     * @param date
     * @return
     */
    public static Date getDate(String sFormat, String date) {
        if (sFormat == null || sFormat.isEmpty() || date == null || "".equals(date)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(sFormat);
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            logger.error(ERROR_TIP, e);
        }
        return null;
    }

    /**
     * 近4周-周一
     *
     * @return
     */
    public static String getMondayAfter4weeks() {
        int days = LocalDate.now().getDayOfWeek().getValue();
        days = days + 21;
        return LocalDate.now().minusDays(days - 1).toString() + " 00:00:00";
    }

    /**
     * 获取本周一0点时间
     *
     * @return
     */
    public static String getMondayThisWeek() {
        int days = LocalDate.now().getDayOfWeek().getValue();
        return LocalDate.now().minusDays(days - 1).toString() + " 00:00:00";
    }

    /**
     * 当天12点
     *
     * @return
     */
    public static Date getTwelveOclock() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.set(Calendar.HOUR_OF_DAY, 12);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date zero = calendar.getTime();
        return zero;
    }

    /**
     * 取得系统当前日期
     *
     * @return String yyyy-mm-dd
     */
    public static String getCurrentDate() {
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONTH) + 1;
        int day = rightNow.get(Calendar.DATE);
        return convertDateToString(convertStringToDate(year + "-" + month + "-" + day));
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDateTime() {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        return format.format(date);

    }

    /**
     * 获取现在时间
     *
     * @return 返回时间字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getCurrentDate(String fm) {
        if (fm == null || fm.isEmpty()) {
            return null;
        }
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(fm);
        return format.format(date);
    }

    /**
     * 获取现在时间
     *
     * @return 返回时间字符串 yyyy-MM-dd HH:mm:ss
     */
    public static String getDateByLongTime(long time) {
        Date date = new Date(time);
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        return format.format(date);
    }

    public static String getDateTime(String format, Date date) {
        if (format == null || format.isEmpty() || date == null) {
            return null;
        }
        SimpleDateFormat fm = new SimpleDateFormat(format);
        return fm.format(date);
    }

    public static String getDateTime(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat df = DATE_TIME_FORMAT_LOCAL.get();
        if (df == null) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DATE_TIME_FORMAT_LOCAL.set(df);
        }
        return df.format(date);
    }

    public static Date getSpecifiedAfterDay(int day, Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.add(Calendar.DATE, day);
        date = new Date(c.getTimeInMillis());
        return date;
    }

    /**
     * 获得指定日期前多少天的日期
     *
     * @param day  天
     * @param date 指定日期
     * @return
     */
    public static Date getSpecifiedBeforeDay(int day, Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(date.getTime());
        c.add(Calendar.DATE, -1 * day);//天后的日期
        date = new Date(c.getTimeInMillis()); //将c转换成Date
        return date;
    }


    /**
     * 获取指定时间天
     */
    public static int getDay(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 得到指定时间小时
     */
    public static int getHour(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 得到现在分钟
     *
     * @return
     */
    public static int getMinute(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MINUTE);
    }

    /**
     * 取得系统当前时间的前n天
     *
     * @return String yyyy-mm-dd
     */
    public static String getDayBeforeCurrentDate(int day) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -day);
        return convertDateToString(convertStringToDate(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE)));
    }

    public static String getDayTimeBeforeCurrentDate(int day) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, -day);
        return getDateByLongTime(c.getTimeInMillis());
    }

    /**
     * 取得系统当前时间的前n分钟
     *
     * @param minutes
     * @return
     */
    public static String getDayTimeBeforeNow(int minutes) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MINUTE, -minutes);
        return getDateByLongTime(c.getTimeInMillis());
    }

    /**
     * 取得系统当前时间后n天
     *
     * @return String yyyy-mm-dd
     */
    public static String getDayAfterCurrentDate(int day) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_MONTH, day);
        return convertDateToString(convertStringToDate(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE)));
    }

    public static Date getDateTimeAfterCurrentDate(int day) throws ParseException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(HH_MM_SS);
        String newDate = getDayAfterCurrentDate(day);
        String time = format.format(date);
        SimpleDateFormat formatTime = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        return formatTime.parse(newDate + " " + time);
    }

    public static String getDateTimeAfterNow(int day) throws ParseException {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat(HH_MM_SS);
        String newDate = getDayAfterCurrentDate(day);
        String time = format.format(date);
        return newDate + " " + time;
    }

    /**
     * 获取日期的星期
     *
     * @return 星期数字
     */
    public static int getWeek(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.DAY_OF_WEEK) - 1;

    }

    public static String getWeekString(int week) {
        String strWeek;
        switch (week) {
            case 0:
                strWeek = "星期日";
                break;
            case 1:
                strWeek = "星期一";
                break;
            case 2:
                strWeek = "星期二";
                break;
            case 3:
                strWeek = "星期三";
                break;
            case 4:
                strWeek = "星期四";
                break;
            case 5:
                strWeek = "星期五";
                break;
            case 6:
                strWeek = "星期六";
                break;
            case 7:
                strWeek = "星期日";
                break;
            default:
                strWeek = "";
        }
        return strWeek;
    }

    // type=0,星期，type=1,周
    public static String getWeekString(int week, int type) {
        if (type == 0) {
            return getWeekString(week);
        }
        return getWeekStringbyZhou(week);
    }

    private static String getWeekStringbyZhou(int week) {
        String strWeek;
        switch (week) {
            case 0:
                strWeek = "周日";
                break;
            case 1:
                strWeek = "周一";
                break;
            case 2:
                strWeek = "周二";
                break;
            case 3:
                strWeek = "周三";
                break;
            case 4:
                strWeek = "周四";
                break;
            case 5:
                strWeek = "周五";
                break;
            case 6:
                strWeek = "周六";
                break;
            case 7:
                strWeek = "周日";
                break;
            default:
                strWeek = "";
        }
        return strWeek;
    }

    /**
     * 将一个日期字符串转化成日期
     *
     * @return Date YYYY_MM_DD_HHMMSS
     */
    public static Date convertStringToDate(String strDate) {
        if (strDate == null || strDate.isEmpty()) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
            date = df.parse(strDate);

        } catch (Exception e) {
            logger.error(ERROR_TIP, e);
        }
        return date;
    }

    public static Date convertStringToDate(String strDate, String fm) {
        if (strDate == null || strDate.isEmpty() || fm == null || fm.isEmpty()) {
            return null;
        }
        Date date = null;
        try {
            SimpleDateFormat df = new SimpleDateFormat(fm);
            date = df.parse(strDate);

        } catch (Exception e) {
            logger.error(ERROR_TIP, e);
        }
        return date;
    }

    /**
     * 输入两个字符串型的日期，比较两者的大小
     *
     * @return -1 1<2 0 1=2 1 1>2 else -2
     */
    public static int compareDate(String indate1, String indate2) {
        if (indate1 == null || indate1.isEmpty() || indate2 == null || indate2.isEmpty()) {
            return -2;
        }
        Date date1;
        Date date2;
        SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        try {
            date1 = df.parse(indate1);
            date2 = df.parse(indate2);
            if (date1.before(date2)) {
                return -1;
            } else if (date1.equals(date2)) {
                return 0;
            } else if (date1.after(date2)) {
                return 1;
            }
        } catch (ParseException e) {
            logger.error(ERROR_TIP, e);
        }
        return -2;
    }

    /**
     * 将一个日期字符串转化成Calendar
     *
     * @return Calendar
     */
    public static Calendar convertDateStringToCalendar(String strDate) {
        if (strDate == null || strDate.isEmpty()) {
            return null;
        }
        Date date = convertStringToDate(strDate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }


    /**
     * 将一个日期转化成Calendar
     *
     * @param date Date
     * @return Calendar
     */
    public static Calendar convertDateToCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c;
    }


    /**
     * 取得某个特定时间前N小时的时间
     *
     * @return yyyy-mm-dd HH:mm:ss
     */
    public static String getDayOfHourBefore(String strDate, int hour) {
        if (strDate == null || strDate.isEmpty()) {
            return null;
        }
        String dateResult = strDate;
        Date date;
        try {
            SimpleDateFormat df = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
            date = df.parse(strDate);
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.HOUR, -hour);
            dateResult = c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE) + " " + c.get(Calendar.HOUR_OF_DAY) + ":" + c.get(Calendar.MILLISECOND) + ":" + c.get(Calendar.SECOND);
        } catch (Exception e) {
            logger.error(ERROR_TIP, e);
        }
        return dateResult;
    }


    /**
     * 取得某个时间后n天,格式为yyyy-mm-dd
     *
     * @param day int
     * @return yyyy-mm-dd
     */
    public static String getDayAfterSpecificDate(String strDate, int day) {
        if (strDate == null || strDate.isEmpty()) {
            return null;
        }
        Calendar c = convertDateStringToCalendar(strDate);
        c.add(Calendar.DAY_OF_MONTH, day);
        return convertDateToString(convertStringToDate(c.get(Calendar.YEAR) + "-" + (c.get(Calendar.MONTH) + 1) + "-" + c.get(Calendar.DATE)));
    }


    /**
     * 将日期转化为字符串
     *
     * @param date
     * @return
     */
    public static String convertDateToString(Date date) {
        if (date == null) {
            return null;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(YYYY_MM_DD);
        return dateFormat.format(date);
    }

    /**
     * 将日期转化为字符串
     *
     * @param date
     * @return
     */
    public static String convertDateToString(Date date, String dateFormatstr) {
        if (date == null || dateFormatstr == null || dateFormatstr.isEmpty()) {
            return null;
        }
        SimpleDateFormat dateFormat;
        try {
            dateFormat = new SimpleDateFormat(dateFormatstr);
            return dateFormat.format(date);
        } catch (Exception e) {
            logger.error(ERROR_TIP, e);
        }
        return "";

    }


    /**
     * 获取指定时间月份
     */
    public static int getMonth(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.MONTH) + 1;
    }

    /**
     * 获取指定时间年份
     */
    public static int getYear(Date date) {
        if (date == null) {
            return 0;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.get(Calendar.YEAR);
    }

    /**
     * 取得上一个小时
     */
    public static String getPreHour(String format, int step) {
        if (format == null || format.isEmpty()) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, cal.get(Calendar.HOUR) - step);
        return sdf.format(cal.getTime());
    }


    /**
     * 是否闰年
     *
     * @param year 年
     * @return
     */
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }


    /**
     * 加/减秒
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addSec(Date date, int amount) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.SECOND, amount);
        return c.getTime();
    }

    /**
     * 加/减分钟
     *
     * @param date
     * @param amount
     * @return
     */
    public static Date addMin(Date date, int amount) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, amount);
        return c.getTime();
    }


    /**
     * 加/减分钟
     *
     * @param date
     * @param amount
     * @return
     */
    public static String addMinFormat(Date date, int amount) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MINUTE, amount);
        return convertDateToString(c.getTime(), YYYY_MM_DD_HHMMSS);
    }

    /**
     * @return String
     * @throws
     * @Title: getDateStr
     * @Description: 获取当天日期str
     */
    public static String getDateStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        return sdf.format(new Date());
    }


    /**
     * 加/减小时
     *
     * @param date
     * @return
     */
    public static Date addHour(Date date, int hour) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }

    public static Date getLastWeekStartTime() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new DateTime().plusWeeks(-1).toDate());
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return currentDate.getTime();
    }

    /**
     * @return
     * @throws
     * @Method: getcurrentWeekStartTime
     * @Description: 获取当前周的结束时间
     */
    public static Date getLastWeekEndTime() {
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(new DateTime().plusWeeks(-1).toDate());
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 23);
        currentDate.set(Calendar.MINUTE, 59);
        currentDate.set(Calendar.SECOND, 59);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return currentDate.getTime();
    }

    public static boolean isCurrentMonthTime(Date date) {
        if (date == null) {
            return false;
        }
        String d1 = getDateTime(YYYY_MM, date);
        String d2 = getDateTime(YYYY_MM, new Date());
        return d2.equals(d1);

    }

    public static String getLastBeginHour(Date time) {
        if (time == null) {
            return null;
        }
        time = addHour(time, -1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        return format.format(cal.getTime());
    }

    public static String getLastEndHour(Date time) {
        if (time == null) {
            return null;
        }
        time = addHour(time, -1);
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        return format.format(cal.getTime());
    }

    public static String getSomeDayDate(String time, int day) {
        if (time == null || time.isEmpty()) {
            return null;
        }
        Date date = convertStringToDate(time, YYYY_MM_DD_HHMMSS);
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, day);
        SimpleDateFormat format = new SimpleDateFormat(YYYY_MM_DD_HHMMSS);
        return format.format(cal.getTime());
    }

    public static String getMinBeforeCurrentDate(int minute) {
        Calendar c = Calendar.getInstance();
        c.add(12, -minute);

        return getDateTime(c.getTime());
    }

    /**
     * Date 类型 convert LocalDate 类型
     *
     * @param date
     * @return
     */
    public static LocalDate getLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        return localDate;
    }

    public static Date getLocalDateToDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        Date date = Date.from(zdt.toInstant());
        return date;
    }

    /**
     * 获取某月的第一天
     *
     * @return 返回时间长整型
     */
    public static String getFirstDayOfMonthString(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        return convertDateToString(convertStringToDate(year + "-" + month + "-" + day));
    }

    /**
     * 获取某月的最后一天
     *
     * @return 返回时间长整型
     */
    public static String getLastDayOfMonthString(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        int day = c.get(Calendar.DATE);
        return convertDateToString(convertStringToDate(year + "-" + month + "-" + day));
    }

    /**
     * 获得指定日期当月第一天
     *
     * @param date
     * @return
     */
    public static Date getFirstDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 获得指定日期当月最后一天
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        if (date == null) {
            return null;
        }
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        return c.getTime();
    }

    /**
     * 得到指定日期的凌晨的时间
     *
     * @param date - Date
     * @return long
     */
    public static Date getDayTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        return new Date(cal.getTimeInMillis());
    }

    /**
     * 根据当前日期查询本周的日期列表
     *
     * @param flag 0本周,-1上一周,1下一周.....
     * @return
     */
    public static List<Date> dateToWeek(int flag) {
        long dateMillis = (System.currentTimeMillis() + (flag * WEEK_MILLIS));
        // 毫秒数转时间
        Date date = new Date(dateMillis);
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();

        // atZone()方法返回在指定时区从此Instant生成的ZonedDateTime。
        LocalDate localDate = instant.atZone(zoneId).toLocalDate();
        // 查询当前日期是本周的第几天
        int b = localDate.getDayOfWeek().getValue();
        Date fdate;
        List<Date> list = new ArrayList<Date>();
        Long fTime = date.getTime() - b * 24 * 3600000;
        for (int a = 1; a <= 7; a++) {
            fdate = new Date();
            fdate.setTime(fTime + (a * 24 * 3600000));
            list.add(a - 1, fdate);
        }
        return list;
    }

    /**
     * 获取指定日期的23:59:59时间
     *
     * @param date
     * @return
     */
    public static Date getDayEndTime(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(date.getTime());
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MILLISECOND, 999);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        return cal.getTime();
    }

    /**
     * 获取指定日期当周第一天时间
     *
     * @param date
     * @return
     */
    public static Date getWeekMonday(Date date) {
        if (date == null) {
            return null;
        }
        Calendar currentDate = Calendar.getInstance();
        currentDate.setTime(date);
        currentDate.setFirstDayOfWeek(Calendar.MONDAY);
        currentDate.set(Calendar.HOUR_OF_DAY, 0);
        currentDate.set(Calendar.MINUTE, 0);
        currentDate.set(Calendar.SECOND, 0);
        currentDate.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return currentDate.getTime();
    }

    /**
     * 逆向解析一个时间，返回一个long型的时间
     *
     * @param date   - 字符型时间
     * @param format - 格式化时间
     * @return long 型时间
     */
    public static long getDateTimeMillis(String date, String format) {
        if (date == null || date.isEmpty() || format == null || format.isEmpty())
            return 0;
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            return sf.parse(date).getTime();
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * 判断一段字符串是否是日期+时间，2011-07-07 12:12:12
     *
     * @param str - 字符串
     * @return boolean 布尔值
     */
    public static boolean isDateTime(String str) {
        if (str == null)
            return false;
        Matcher m = Pattern_DateTime.matcher(str);
        return m.matches();
    }
}