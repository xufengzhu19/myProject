import basic.datatype.Collection;
import config.RedisConfig;
import org.apache.commons.collections4.CollectionUtils;
import utils.ExcelUtil;
import utils.FileUtils;
import utils.IdUtils;
import utils.NumberUtils;

import javax.mail.search.SearchTerm;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class mytest {

    public static void main(String[] args) throws IOException {
//        HashMap<String, List<Object>> map = ExcelUtil.readExcelColumns("C:\\Users\\xufengzhu\\Desktop\\clickdata.xlsx");
        HashMap<String, List<Object>> map = ExcelUtil.readExcelColumns("./");
        String group = "clickfeature:";
        map.forEach((k, v) -> {
            if (CollectionUtils.isNotEmpty(v) && Objects.nonNull(v.get(0)) && NumberUtils.isNumber(v.get(0).toString())) {
                RedisConfig.getJedis(4).rpush(group + k, "true");
                return;
            }
            v.stream().distinct().forEach(o -> {
                RedisConfig.getJedis(4).rpush(group + k, k + "=" + o);
            });
        });
    }
}
