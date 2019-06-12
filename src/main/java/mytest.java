import com.oracle.webservices.internal.api.message.ContentType;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.util.IOUtils;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import utils.ExcelUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class mytest {
    public static void main(String[] args) throws IOException {
        List<Object> linked = new LinkedList<>();
        linked.add(null);
        System.out.println(linked.size());
        System.out.println(Objects.isNull(linked.get(0)));
    }

    public static void test(String... flag) {
        if (ArrayUtils.isEmpty(flag))
            System.out.println("flag=" + flag);
        else
            System.out.println("not empty,flag=" + flag);
    }
}
