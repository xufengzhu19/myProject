package utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class SignUtil {
    /**
     * * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * * @param params 需要排序并参与字符拼接的参数组
     * * @return 拼接后字符串
     * * @throws UnsupportedEncodingException
     */
    public static String createLinkStringByGet(Map<String, String> params)  {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
//        System.out.println(keys);
        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
//            value = URLEncoder.encode(value, "UTF-8");
            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }


        return prestr;
    }

    public static String createSign(Map<String, String> params, String accessToken){
        String linkStringByGet = createLinkStringByGet(params);
        //加密后的字符串
        String encodeStr = DigestUtils.md5Hex(linkStringByGet + accessToken);
//        System.out.println("MD5加密后的字符串为:encodeStr=" + encodeStr);

        return encodeStr.toUpperCase();
    }
}
