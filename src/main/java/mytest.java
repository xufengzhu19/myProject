import org.apache.commons.lang3.ArrayUtils;
import utils.Base64;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class mytest {
    public static void main(String[] args) throws IOException {

        BigDecimal count=new BigDecimal(1);
        BigDecimal totalRecords=new BigDecimal(3);
        System.out.println(count.divide(totalRecords,3,BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    public static void test(String... flag) {
        if (ArrayUtils.isEmpty(flag))
            System.out.println("flag=" + flag);
        else
            System.out.println("not empty,flag=" + flag);
    }
}
