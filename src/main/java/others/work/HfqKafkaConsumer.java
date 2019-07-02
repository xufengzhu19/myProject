package others.work;

import com.alibaba.fastjson.JSON;
import config.KafkaConfig;
import config.RedisConfig;
import models.hfq.thirdpartydata.TONGDUN;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.FileUtils;

import java.io.IOException;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class HfqKafkaConsumer {
    private static final Logger log = LoggerFactory.getLogger(HfqKafkaConsumer.class);
    public static int count = 0;
    public static final String value_path = "/home/we-op/antifraud/";
    public static final String origin_path = "/home/we-op/antifraud/origin/";

    public static void main(String[] args) throws IOException {
//        String content = FileUtils.readFile("C:\\Users\\xufengzhu\\Desktop\\7806f90ada4822aac7488d7bc379d59c_1561719705284.txt");

        ConsumerRecords<String, String> records;
        while (true) {
            records = KafkaConfig.getConsumer().poll(Duration.ofMillis(1000));
            records.forEach(record -> {
                count++;
                log.info("receive msg from AF,count={}", count);
                long start = System.currentTimeMillis();
                try {
                    FileUtils.createFile(record.value(), start + ".txt", origin_path);
                } catch (IOException e) {
                    log.error("create file error!", e);
                }
        try {
//            handleMsg(content);
                    handleMsg(record.value());
        } catch (IOException e) {
            log.error("handle msg error!", e);
        }
        long end = System.currentTimeMillis();
                log.info("handle over! spendTime={}", end - start);
            });
        }
    }

    public static void handleMsg(String content) throws IOException {
        if (StringUtils.isBlank(content)) {
            log.error("content is blank! content={}", content);
            return;
        }
        HashMap<String, Object> map = getMap(content);
        if (MapUtils.isEmpty(map)) {
            log.error("getMap.content is empty!");
            return;
        }
        HashMap<String, Object> dataVoMap = getMap(map.get("dataVo"));
        if (MapUtils.isEmpty(dataVoMap)) {
            log.error("dataVoMap is empty!");
            return;
        }
        Object userKeyObj = dataVoMap.get("userKey");
        if (Objects.isNull(userKeyObj)) {
            log.error("userKeyObj is null!");
            return;
        }
        String userKey = userKeyObj.toString();

        String fileName = userKey + "_" + System.currentTimeMillis() + ".txt";
        FileUtils.createFile(content, fileName, value_path);
        log.info("save msg as file,userKey={},fileName={}", userKey, fileName);

        HashMap<String, Object> dataVoDataMap = getMap(dataVoMap.get("data"));
        if (MapUtils.isEmpty(dataVoDataMap)) {
            log.error("dataVoDataMap is empty! userKey={}", userKey);
            return;
        }
        HashMap<String, Object> thirdPartyDataMap = getMap(dataVoDataMap.get("thirdPartyData"));
        if (MapUtils.isEmpty(thirdPartyDataMap)) {
            log.error("thirdPartyDataMap is empty! userKey={}", userKey);
            return;
        }
        HashMap<String, Object> tongDunMap = getMap(thirdPartyDataMap.get("TONGDUN_DETAILS"));
        if (MapUtils.isEmpty(tongDunMap)) {
            log.error("tongDunMap is empty! userKey={}", userKey);
            return;
        }
        getTONGDUN(tongDunMap, userKey);
    }

    public static TONGDUN getTONGDUN(HashMap<String, Object> map, String userKey) {
        try {
            if (MapUtils.isEmpty(map)) {
                log.error("tongdun map is empty!");
                return null;
            }
            Object rulesObj = map.get("rules");
            if (Objects.isNull(rulesObj)) {
                log.error("tongdun map get rules is null! userKey={}", userKey);
                return null;
            }
            List<Map<String, Object>> rulesList = (List) rulesObj;
            if (CollectionUtils.isEmpty(rulesList)) {
                log.error("rules list is empty! userKey={}", userKey);
            }
            rulesList.stream().filter(Objects::nonNull).forEach(ruleMap -> {
                String ruleId = ruleMap.get("ruleId").toString();
                log.info("userKey-ruleId={}:{}", userKey, ruleId);
                Object conditionObj = ruleMap.get("conditions");
                if (Objects.isNull(conditionObj)) {
                    log.info("conditionObj is null!");
                    return;
                }
                List<Map<String, Object>> conditionList = (List) conditionObj;
                conditionList.stream().filter(Objects::nonNull).forEach(conditionMap -> {
                    if (Objects.isNull(conditionMap.get("type"))) {
                        log.error("there is no type! userKey={}", userKey);
                        return;
                    }
                    String type = conditionMap.get("type").toString();
                    String key = userKey + ":thirdPartyData:" + type + ":" + ruleId;
                    log.info("userKey-type={}:{},key={}", userKey, type, key);
//                    if (type.equals("association_partner")) {
//                        log.info("get association_partner data,key={}, userKey={}", key, userKey);
//                        RedisConfig.getJedis().set(key, JSON.toJSONString(conditionMap));
//                        return;
//                    }
//                    if (type.equals("cross_partner")) {
//                        log.info("get cross_partner data,key={}, userKey={}", key, userKey);
//                        RedisConfig.getJedis().set(key, JSON.toJSONString(conditionMap));
//                        return;
//                    }
                    RedisConfig.getJedis().set(key, JSON.toJSONString(conditionMap));

                });
            });
        } catch (Exception e) {
            log.error("getTONGDUN error!", e);
        }
        return null;
    }

    public static HashMap<String, Object> getMap(Object object) {
        if (Objects.isNull(object)) {
            log.error("get map object is null!");
            return null;
        }
        return JSON.parseObject(object.toString(), HashMap.class);
    }

}
