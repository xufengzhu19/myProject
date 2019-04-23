package utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Tools {

    private static final Log logger = LogFactory.getLog(Tools.class);
    /**
     * 一小时的毫秒数
     */
    public static final long HOUR_MILLIS = 3600000l;
    // ////////////////////////////////////////////////////////////////////////////////////////////
    private static ThreadLocal<DecimalFormat> NUMBER_FORMAT_LOCAL = new ThreadLocal<DecimalFormat>();
    /**
     * 判断是否是手机的正则表达式
     */
    private static final Pattern Pattern_Mobile = Pattern
            .compile("^1[0-9]{10}$");
    /**
     * 判断是否是数字的正则表达式
     */
    private static final Pattern Pattern_Number = Pattern.compile("\\d+");
    /**
     * 判断是否是日期+时间的正则表达式，如2013-01-23 15:07:47
     */
    private static final Pattern Pattern_DateTime = Pattern
            .compile("^[0-9]{4}-[0-9]{2}-[0-9]{2} [0-9]{2}:[0-9]{2}:[0-9]{2}$");

    /**
     * 判断是否是整型数字，包括负数
     */
    private static final Pattern Pattern_Int = Pattern.compile("^[-]?[\\d]+$");

    private static ThreadLocal<SimpleDateFormat> DATE_FORMAT_LOCAL = new ThreadLocal<SimpleDateFormat>();
    private static ThreadLocal<SimpleDateFormat> DATE_TIME_FORMAT_LOCAL = new ThreadLocal<SimpleDateFormat>();
    public static Date defaultDate = DateBuilder.getDate(DateBuilder.YYYY_MM_DD_HHMMSS, "1970-01-01 00:00:00");

    private Tools() {
    }

    /**
     * 获取SimpleDateFormat，格式化时间为：yyyy-MM-dd
     *
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getDateFormat() {
        SimpleDateFormat df = DATE_FORMAT_LOCAL.get();
        if (df == null) {
            df = new SimpleDateFormat("yyyy-MM-dd");
            DATE_FORMAT_LOCAL.set(df);
        }
        return df;
    }

    /**
     * 获取SimpleDateFormat，格式化时间为：yyyy-MM-dd HH:mm:ss
     *
     * @return SimpleDateFormat
     */
    public static SimpleDateFormat getDateTimeFormat() {
        SimpleDateFormat df = DATE_TIME_FORMAT_LOCAL.get();
        if (df == null) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DATE_TIME_FORMAT_LOCAL.set(df);
        }
        return df;
    }

    /**
     * 获取一个格式化的时间
     *
     * @param time   long
     * @param format 格式化字符串
     * @return String
     */
    public static String getFormatDate(long time, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        Date date = new Date(time);
        return sf.format(date);
    }

    /**
     * 时间格式化，格式：2011-11-11 11:11:11
     * - 时间
     *
     * @return String
     */
    public static String getDateTime() {
        return getDateTime(new Date());
    }

    /**
     * 时间格式化
     *
     * @param date - 时间对象
     * @return String
     */
    public static String getDateTime(Date date) {
        if (date == null)
            return "";
        return getDateTimeFormat().format(date);
    }

    /**
     * 格式化日期 yyyy-MM-dd HH:mm:ss，返回Date
     *
     * @return long
     */
    public static Date getDateTime(String date) {
        if (Tools.isNull(date))
            return null;
        try {
            return getDateTimeFormat().parse(date);
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    /**
     * 逆向解析一个时间，返回一个long型的时间
     *
     * @param date   - 字符型时间
     * @param format - 格式化时间
     * @return long 型时间
     */
    public static long getDateTimeMillis(String date, String format) {
        SimpleDateFormat sf = new SimpleDateFormat(format);
        try {
            return sf.parse(date).getTime();
        } catch (ParseException e) {
            logger.error(e.getMessage(), e);
        }
        return 0;
    }

    /**
     * 获得当前日期与本周一相差的天数
     *
     * @return int
     */
    private static int getMondayPlus() {
        Calendar cd = Calendar.getInstance();
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return 6;
        } else {
            return dayOfWeek - 2;
        }
    }

    /**
     * 四舍五入返回一个小数
     *
     * @param d     - 要格式化的数字
     * @param scale - 返回小数的位数
     * @return double
     */
    public static double getDouble(double d, int scale) {
        BigDecimal bd = BigDecimal.valueOf(d + 0.00001d);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 四舍五入返回一个小数
     *
     * @param f     - 要格式化的数字
     * @param scale - 返回小数的位数
     * @return float
     */
    public static float getFloat(float f, int scale) {
        BigDecimal bd = BigDecimal.valueOf(f);
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 四舍五入返回一个小数
     *
     * @param f
     * @param scale
     * @return
     */
    public static float getFloat(Float f, int scale) {
        if (f == null)
            return getFloat(0f, scale);
        BigDecimal bd = BigDecimal.valueOf(f.floatValue());
        return bd.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
    }

    /**
     * 字符串不能为空
     *
     * @param str        - 字符串
     * @param defaultStr - 字符串为空显示的字符
     * @return 不为空的字符串
     */
    public static String formatString(String str, String defaultStr) {
        if (Tools.isNull(str))
            return defaultStr;
        return str;
    }

    /**
     * 字符串不能为空
     *
     * @param str - 字符串
     * @return 不为空的字符串
     */
    public static String formatString(String str) {
        return formatString(str, "");
    }

    /**
     * 返回一个字符串是否为空，如果字符串为""也为true，空格也为true
     *
     * @param str - String
     * @return true false
     */
    public static boolean isNull(String str) {
        if (str == null || str.trim().length() == 0)
            return true;
        return false;
    }

    /**
     * 返回一个字符串是否不为空
     *
     * @param str - String
     * @return True or False
     */
    public static boolean isNotNull(String str) {
        if (str == null || str.trim().length() == 0)
            return false;
        return true;
    }

    /**
     * 返回List是否为空
     *
     * @param list - List
     * @return True or False
     */
    public static boolean isNull(List<?> list) {
        if (list == null || list.isEmpty())
            return true;
        return false;
    }

    /**
     * 返回List是否不为空
     *
     * @param list - List
     * @return True or False
     */
    public static boolean isNotNull(List<?> list) {
        if (list == null || list.isEmpty())
            return false;
        return true;
    }

    /**
     * 返回Map是否为空
     *
     * @param map - Map
     * @return True or False
     */
    public static boolean isNull(Map<?, ?> map) {
        if (map == null || map.isEmpty())
            return true;
        return false;
    }

    /**
     * 返回Map是否不为空
     *
     * @param map - Map
     * @return True or False
     */
    public static boolean isNotNull(Map<?, ?> map) {
        if (map == null || map.isEmpty())
            return false;
        return true;
    }

    /**
     * 判断Object是否为空，注意，此处仅仅判断 null
     *
     * @param obj - Object
     * @return True or False
     */
    public static boolean isNull(Object obj) {
        if (obj == null)
            return true;
        return false;
    }

    /**
     * 判断Object是否不为空，注意，此处仅仅判断 null
     *
     * @param obj - Object
     * @return True or False
     */
    public static boolean isNotNull(Object obj) {
        if (obj == null)
            return false;
        return true;
    }

    /**
     * 返回Set是否不为空
     *
     * @param set - Set
     * @return True or False
     */
    public static boolean isNotNull(Set<?> set) {
        if (set == null || set.isEmpty())
            return false;
        return true;
    }

    /**
     * 返回Set是否为空
     *
     * @param set - Set
     * @return True or False
     */
    public static boolean isNull(Set<?> set) {
        if (set == null || set.isEmpty())
            return true;
        return false;
    }

    /**
     * 判断数组是否为空
     *
     * @param array - Object[]
     * @return True or False
     */
    public static boolean isNull(Object[] array) {
        if (array == null || array.length == 0)
            return true;
        return false;
    }

    /**
     * 判断数组是否不为空
     *
     * @param array - Object[]
     * @return True or False
     */
    public static boolean isNotNull(Object[] array) {
        if (array == null || array.length == 0)
            return false;
        return true;
    }

    /**
     * 判断字符串是否为浮点型数字，包含负数
     *
     * @param str - String
     * @return true or false
     */
    public static boolean isDouble(String str) {
        if (str == null || str.length() == 0)
            return false;
        return Pattern.matches("^[-]?[\\d]+([.]?[\\d]*)$", str);
    }

    /**
     * 判断字符串是否为整型数字，包括负数。
     *
     * @param str - String
     * @return True or False
     */
    public static boolean isInt(String str) {
        if (isNull(str))
            return false;
        Matcher m = Pattern_Int.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否是手机号码
     *
     * @param str - String
     * @return true or false
     */
    public static boolean isMobile(String str) {
        if (str == null || str.length() != 11)
            return false;
        Matcher m = Pattern_Mobile.matcher(str);
        return m.matches();
    }

    /**
     * 判断是否是座机
     *
     * @param str - String
     * @return True or False
     */
    public static boolean isTel(String str) {
        if (str == null)
            return false;

        return Pattern.matches("^(\\d{3,4}-)?\\d{7,9}$", str);
    }


    /**
     * 判断一段字符串是否是数字，不包括全角和负数。 此方法替代<code>Tools.isMath(java.lang.String)</code>
     *
     * @param str - 字符串
     * @return boolean 布尔值
     */
    public static boolean isNumber(String str) {
        if (str == null)
            return false;
        Matcher m = Pattern_Number.matcher(str);
        return m.matches();
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

    /**
     * 去掉字符串头尾的空格
     *
     * @param str - 要替换的字符串
     * @return String
     */
    public static String trim(String str) {
        if (str == null)
            return "";
        return str.trim();
    }

    /**
     * 字符串转换成int型,默认值返回0
     *
     * @param s - String
     * @return int
     */
    public static int parseInt(String s) {
        return parseInt(s, 0);
    }

    /**
     * 字符串转换成int型
     *
     * @param s            - String
     * @param defaultValue - 如果转换错误，则返回defaultValue
     * @return int
     */
    public static int parseInt(String s, int defaultValue) {
        if (s == null)
            return defaultValue;

        try {
            return Integer.parseInt(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 字符串转换long型,默认值返回0
     *
     * @param s - String
     * @return long
     */
    public static long parseLong(String s) {
        return parseLong(s, 0);
    }

    /**
     * 字符串转换成long型
     *
     * @param s            - String
     * @param defaultValue - 如果转换错误，则返回defaultValue
     * @return long
     */
    public static long parseLong(String s, long defaultValue) {
        if (s == null)
            return defaultValue;

        try {
            return Long.parseLong(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 字符串抓换成float型
     *
     * @param s            - String
     * @param defaultValue - 如果转换错误，则返回defaultValue
     * @return float
     */
    public static float parseFloat(String s, float defaultValue) {
        if (s == null)
            return defaultValue;

        try {
            return Float.parseFloat(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 字符串转换成double型，默认值返回0
     *
     * @param s - String
     * @return double
     */
    public static double parseDouble(String s) {
        return parseDouble(s, 0d);
    }

    /**
     * 字符串抓换成double型
     *
     * @param s            - String
     * @param defaultValue - 如果转换错误，则返回defaultValue
     * @return double
     */
    public static double parseDouble(String s, double defaultValue) {
        if (s == null)
            return defaultValue;

        try {
            return Double.parseDouble(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * 字符串抓换成boolean型
     *
     * @param s            - String
     * @param defaultValue - 如果转换错误，则返回defaultValue
     * @return boolean
     */
    public static boolean parseBoolean(String s, boolean defaultValue) {
        if (s == null)
            return defaultValue;

        try {
            return Boolean.parseBoolean(s);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Long转换成long
     *
     * @param l            - Long
     * @param defaultValue - 默认显示值
     * @return long
     */
    public static long longValue(Long l, long defaultValue) {
        if (l == null)
            return defaultValue;
        return l.longValue();
    }

    /**
     * Integer转换成int
     *
     * @param i            - Integer
     * @param defaultValue - 默认显示值
     * @return int
     */
    public static int intValue(Integer i, int defaultValue) {
        if (i == null)
            return defaultValue;
        return i.intValue();
    }

    /**
     * Float转换成float
     *
     * @param f            - Float
     * @param defaultValue - 默认显示值
     * @return float
     */
    public static float floatValue(Float f, float defaultValue) {
        if (f == null)
            return defaultValue;
        return f.floatValue();
    }

    /**
     * Double转换成double
     *
     * @param d            - Double
     * @param defaultValue - 默认显示值
     * @return double
     */
    public static double doubleValue(Double d, double defaultValue) {
        if (d == null)
            return defaultValue;
        return d.doubleValue();
    }

    /**
     * Boolean转换成Boolean
     *
     * @param b            - Boolean
     * @param defaultValue - 默认显示值
     * @return boolean
     */
    public static boolean booleanValue(Boolean b, boolean defaultValue) {
        if (b == null)
            return defaultValue;
        return b.booleanValue();
    }

    /**
     * 获取DecimalFormat，保留两位小数
     *
     * @return DecimalFormat
     */
    public static DecimalFormat getDecimalFormat() {
        DecimalFormat df = NUMBER_FORMAT_LOCAL.get();
        if (df == null) {
            df = new DecimalFormat("#0.00");
            NUMBER_FORMAT_LOCAL.set(df);
        }
        return df;
    }

    /**
     * 获取两位Float
     *
     * @param number - 数字
     * @return String
     */
    public static String getFloat2(float number) {
        DecimalFormat df = getDecimalFormat();
        return df.format(number);
    }

    /**
     * 获取两个Double
     *
     * @param number - 数字
     * @return String
     */
    public static String getDouble2(double number) {
        DecimalFormat df = getDecimalFormat();
        return df.format(number);
    }

    /**
     * 获取两位Float
     *
     * @param number - 数字
     * @param zero   - 如果小数位数为0，是否显示小数位。
     * @return String
     */
    public static String getDouble2(double number, boolean zero) {
        String r = getDouble2(number);
        if (!zero) {
            int indexOf;
            if (r != null && (indexOf = r.lastIndexOf(".")) > -1) {
                String r0 = r.substring(indexOf);

                int loc = r.length();
                for (int i = r0.length() - 1; i >= 0; i--) {
                    if (r0.charAt(i) == '0' || r0.charAt(i) == '.') {
                        loc--;
                    } else {
                        break;
                    }
                }
                return r.substring(0, loc);
            }
        }

        return r;
    }

    /**
     * 获取本机IP地址
     *
     * @return String
     */
    public static String getLocalIP() {
        String ip = "";
        try {
            Enumeration<NetworkInterface> e1 = NetworkInterface
                    .getNetworkInterfaces();
            a:
            while (e1.hasMoreElements()) {
                NetworkInterface ni = (NetworkInterface) e1.nextElement();
                Enumeration<InetAddress> e2 = ni.getInetAddresses();
                while (e2.hasMoreElements()) {
                    InetAddress ia = e2.nextElement();
                    if (ia != null && ia instanceof Inet4Address) {
                        String t = ia.getHostAddress();
                        if (Tools.isNotNull(t) && !t.startsWith("127.0")) {
                            ip = t;
                            break a;
                        }
                    }
                }
            }
        } catch (SocketException e) {
            logger.error(e.getMessage(), e);
        }
        return ip;
    }

    /**
     * emoji表情替换
     *
     * @param source  原字符串
     * @param slipStr emoji表情替换成的字符串
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source, String slipStr) {
        if (StringUtils.isNotBlank(source)) {
            return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", slipStr);
        } else {
            return source;
        }
    }

    /**
     * 计算时间
     *
     * @param time
     * @return
     */
    public static Date calTime(double time) {
        long currentTime = System.currentTimeMillis();
        currentTime += time * 60 * 60 * 1000;
        Date date = new Date(currentTime);
        return date;
    }
    public static Date calTime(double time,Date startDate) {
        long startDateTime = startDate.getTime();
        startDateTime += time * 60 * 60 * 1000;
        Date date = new Date(startDateTime);
        return date;
    }
    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentTime() {
        long currentTime = System.currentTimeMillis();
        Date date = new Date(currentTime);
        return date;
    }

    //获取当天的开始时间
    public static Date getDayBegin() {
        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    //获取明天的开始时间
    public static Date getBeginDayOfTomorrow() {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, 1);

        return cal.getTime();
    }

    /**
     * 将emoji表情替换成*
     *
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if(source!=null && source.trim().length()>0){
            return filter(source);
//			 return source.replaceAll("[\ud800\udc00-\udbff\udfff\ud800-\udfff]", "*");
        }else{
            return source;
        }

    }


    public static String filter(String str) {
        if(str.trim().isEmpty()){
            return str;
        }
        String pattern="[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr="*";
        Pattern emoji=Pattern.compile(pattern);
        Matcher  emojiMatcher=emoji.matcher(str);
        str=emojiMatcher.replaceAll(reStr);
        return str;
    }

    public static int checkPunishIntParams(String param) {
       if (Tools.isNull(param)) {
           return 0;
       } else {
           return Integer.parseInt(param);
       }
    }

    public static double checkPunishDoubleParams(String param) {
        if (Tools.isNull(param)) {
            return 0;
        } else {
            return Double.parseDouble(param);
        }
    }

}
