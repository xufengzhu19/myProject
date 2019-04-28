package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author xufengzhu
 */

public class NumberUtils {
    private static Random rnd = new Random();
    /**
     * 判断是否是手机的正则表达式
     */
    private static final Pattern Pattern_Mobile = Pattern.compile("^1[0-9]{10}$");

    /**
     * 判断是否是整型数字，包括负数
     */
    private static final Pattern Pattern_Int = Pattern.compile("^[-]?[\\d]+$");

    private NumberUtils() {
    }

    /**
     * float转换成可靠精度的 double
     */
    public static double getDouble(float f) {
        BigDecimal bd = new BigDecimal(String.valueOf(f));
        return bd.doubleValue();
    }

    /**
     * double 转换成float
     */
    public static float getFloat(Double d) {
        if (d == null) {
            return 0;
        }
        BigDecimal bd = new BigDecimal(d);
        return bd.floatValue();
    }

    /**
     * 获取两位float
     */
    public static String getFloat2(float f) {
        DecimalFormat myformat = new DecimalFormat("#0.00");
        return myformat.format(f);
    }

    /**
     * 获取一位float
     */
    public static String getFloat1(float f) {
        DecimalFormat myformat = new DecimalFormat("#0.0");
        return myformat.format(f);
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
     * 获取一位double
     */
    public static String getDouble1(double d) {
        DecimalFormat myformat = new DecimalFormat("#0.0");
        return myformat.format(d);
    }

    /**
     * 获取两位double
     */
    public static String getDouble2(double f) {
        DecimalFormat myformat = new DecimalFormat("#0.00");
        return myformat.format(f);
    }

    /**
     * 截取字符串
     *
     * @param source 源字符串
     * @param length 需要截取的长度
     * @param suffix 截取后添加的后缀
     * @return
     */
    public static String subString(String source, int length, String suffix) {
        if (source == null || source.isEmpty()) {
            return "";
        } else {
            if (source.length() > length) {
                source = source.substring(0, length);
                if (suffix != null) {
                    source += suffix;
                }
            }
        }
        return source;
    }

    public static int[] getRandomNumber(int num, int max) {
        int[] nums = new int[num];
        Random r = new Random();
        for (int i = 0; i < num; i++) {
            nums[i] = r.nextInt(max);
            max--;
        }
        return nums;
    }

    /**
     * 判断字符串是否数字
     *
     * @param str
     * @return
     */
    public static boolean isNumber(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 判断字符串是否为浮点型数字，包含负数
     *
     * @param str - String
     * @return true or false
     */
    public static boolean isDouble(String str) {
        if (str == null || str.isEmpty())
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
        if (str == null || str.isEmpty())
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

    public static String getRandomNumber(int digCount) {
        StringBuilder sb = new StringBuilder(digCount);
        for (int i = 0; i < digCount; i++)
            sb.append((char) ('0' + rnd.nextInt(10)));
        return sb.toString();
    }

    /**
     * 计算百分比
     *
     * @param f1       被除数
     * @param f2       除数
     * @param radix    差的保留小数位数
     * @param perRadix 显示百分比保留小数位数
     * @return 字符串（含百分号，13%）
     */
    public static String percent(float f1, float f2, int radix, int perRadix) {
        NumberFormat nf = NumberFormat.getPercentInstance();
        nf.setMinimumFractionDigits(perRadix);
        float f = BigDecimal.valueOf(f1 / f2).setScale(radix, BigDecimal.ROUND_HALF_UP).floatValue();
        return nf.format(f);
    }

    /**
     * @param d1
     * @param d2
     * @return
     * @throws
     * @author lihaibo 2015-7-8 下午05:38:19
     * @Method: addDouble
     * @Description: double值的加法精确计算
     */
    public static double addDouble(double d1, double d2) {
        return new BigDecimal(Double.toString(d1)).add(new BigDecimal(Double.toString(d2))).doubleValue();
    }

    /**
     * @param d1
     * @param d2
     * @return
     * @throws
     * @author lihaibo 2015-7-8 下午05:41:09
     * @Method: subtractDouble
     * @Description: double的减法的精确计算
     */
    public static double subtractDouble(double d1, double d2) {
        return new BigDecimal(Double.toString(d1)).subtract(new BigDecimal(Double.toString(d2))).doubleValue();
    }

    /**
     * @param str 原始字符串
     * @return
     */
    public static Long parseLong(String str, long defVal) {
        if (str == null || str.isEmpty())
            return null;
        Long ret = defVal;
        ret = Long.parseLong(str);
        return ret;
    }

    public static Double parseDouble(String str, double defVal) {
        if (str == null || str.isEmpty())
            return null;
        Double ret = defVal;
        ret = Double.parseDouble(str);
        return ret;
    }

    //得到随机数
    public static int getRandom(int start, int end) {
        int number = (int) (Math.random() * (end - start + 1)) + start;
        return number;
    }


}
