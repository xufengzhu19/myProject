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

/**
 * @author xufengzhu
 */

public class Tools {

    private static final Log logger = LogFactory.getLog(Tools.class);

    private Tools() {
    }

    public static String defString(String str, String defaultStr) {
        if (Tools.isNull(str))
            return defaultStr;
        return str;
    }

    public static boolean isNull(String str) {
        if (str == null || str.isEmpty())
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
        if (str == null || str.isEmpty())
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
     * 去掉字符串头尾的空格
     *
     * @param str - 要替换的字符串
     * @return String
     */
    public static String trim(String str) {
        if (str == null || str.isEmpty())
            return "";
        return str.trim();
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
     * 将emoji表情替换成*
     *
     * @param source
     * @return 过滤后的字符串
     */
    public static String filterEmoji(String source) {
        if (source != null && source.trim().length() > 0) {
            return filter(source);
        } else {
            return source;
        }
    }


    public static String filter(String str) {
        if (str.trim().isEmpty()) {
            return str;
        }
        String pattern = "[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]";
        String reStr = "*";
        Pattern emoji = Pattern.compile(pattern);
        Matcher emojiMatcher = emoji.matcher(str);
        str = emojiMatcher.replaceAll(reStr);
        return str;
    }

}
