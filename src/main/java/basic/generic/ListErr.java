package basic.generic;

import java.util.ArrayList;
import java.util.List;

public class ListErr {
    public static void main(String[] args) {
        List list=new ArrayList();
        list.add("a");
        list.add("b");
        list.add(1);//编译时不检查类型，ClassCastException
        list.stream().forEach(s-> System.out.println(((String)s).length()));
        //参数化类型，在创建集合时指定类型，List<String>
    }
}
