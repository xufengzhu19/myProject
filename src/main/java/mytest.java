import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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
