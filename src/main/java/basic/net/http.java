package basic.net;

import com.alibaba.fastjson.JSON;
import utils.Constants;
import utils.HttpUtils;
import utils.SignUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;

public class http {
    public static void main(String[] args) {
        LocalDate targetDate = LocalDate.of(2019, 4, 27);
        LocalDate today = targetDate.plusDays(1);
        IntStream.range(0, 24).forEach(hour -> {
            String startTime;
            String endTime;
            if (hour == 23) {
                LocalTime start = LocalTime.of(hour, 0);
                LocalTime end = LocalTime.of(0, 0);
                startTime = LocalDateTime.of(targetDate, start).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00"));
                endTime = LocalDateTime.of(today, end).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00"));
            } else {
                LocalTime start = LocalTime.of(hour, 0);
                LocalTime end = LocalTime.of(hour + 1, 0);
                startTime = LocalDateTime.of(targetDate, start).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00"));
                endTime = LocalDateTime.of(targetDate, end).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:00:00"));
            }
            String codeStr = Constants.CodeStr;
            getData(startTime, endTime, codeStr, hour);
        });


    }

    public static void getData(String start, String end, String codeStr, int hour) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("start_time", start);
        map.put("end_time", end);
        map.put("codes", codeStr);
        String sign = SignUtil.createSign(map, Constants.Token);
        System.out.println("getDataSign\t" + sign);
        map.put("sign", sign);

        String s = HttpUtils.doPost(Constants.URL, JSON.toJSONString(map), "application/json");
        System.out.println("=======================" + hour + "=====================");
        System.out.println(s);
        Map map1 = JSON.parseObject(s, Map.class);
        if (Integer.valueOf(map1.get("status").toString()) == 0) {
            final Map data = (Map) map1.get("data");

            List<Map<String, Object>> list = (List<Map<String, Object>>) data.get("data");
        }
    }


}
