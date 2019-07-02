package others.work;

import config.RedisConfig;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.ExcelUtil;
import utils.NumberUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ImportFeature2Redis {
    private static final Logger log = LoggerFactory.getLogger(ImportFeature2Redis.class);

    public static void main(String[] args) throws IOException {
//        HashMap<String, List<Object>> map = ExcelUtil.readExcelColumns("C:\\Users\\xufengzhu\\Desktop\\clickdata.xlsx");
        HashMap<String, List<Object>> map = ExcelUtil.readExcelColumns("./clickdata.xlsx");
        int idx = 4;
        long start = System.currentTimeMillis();
        log.info("import data feature to redis db={},start={}", idx, start);
        String group = "clickfeature:";
        map.forEach((k, v) -> {
            if (CollectionUtils.isNotEmpty(v) && Objects.nonNull(v.get(0)) && NumberUtils.isNumber(v.get(0).toString())) {
                RedisConfig.getJedis(idx).rpush(group + k, "true");
                return;
            }
            v.stream().distinct().forEach(o -> {
                RedisConfig.getJedis(idx).rpush(group + k, k + "=" + o);
            });
        });
        log.info("import data feature to redis over! spendTime={}", System.currentTimeMillis() - start);
    }
}
