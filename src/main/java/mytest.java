import com.hankcs.hanlp.corpus.io.ByteArray;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class mytest {

    public static void main(String[] args) {
        test();
    }
    public static void test(LocalDateTime... dateTimes){
        System.out.println(ObjectUtils.allNotNull(dateTimes));

    }
}
