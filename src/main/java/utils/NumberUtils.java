package utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Random;
import java.util.regex.Pattern;

public class NumberUtils {
    private static Random rnd = new Random();

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
        BigDecimal bd = new BigDecimal(d);
        return bd.floatValue();
    }

    /**
     * 获取两位float
     */
    public static String getfloat2(float f) {
        DecimalFormat myformat = new DecimalFormat("#0.00");
        return myformat.format(f);
    }

    /**
     * 获取一位float
     */
    public static String getfloat1(float f) {
        DecimalFormat myformat = new DecimalFormat("#0.0");
        return myformat.format(f);
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
     * 获取两位float
     */
    public static String gettotalfloat2(float f, float num) {
        DecimalFormat myformat = new DecimalFormat("#0.00");
        return myformat.format(f * num);
    }

    /**
     * 获取两位float
     */
    public static float getf2(float f) {
        String str = getfloat2(f);
        return Float.valueOf(str);
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
        if (source == null) {
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

    /**
     * 获取随机四位数.
     */
    public static String getFourCode() {
        String desc = "0123456789";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            int idx = (int) (Math.random() * desc.length());
            sb.append(desc.charAt(idx));
        }
        return sb.toString();
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
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
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
     * @param s 原始字符串
     * @return
     */
    public static Long parseLong(String s, long defaVal) {
        Long ret = defaVal;
        ret = Long.parseLong(s);
        return ret;
    }

    public static Double parseDouble(String s, double defaVal) {
        Double ret = defaVal;
        ret = Double.parseDouble(s);
        return ret;
    }

    //得到随机数
    public static int getRandom(int start, int end) {
        int number = (int) (Math.random() * (end - start + 1)) + start;
        return number;
    }
}
