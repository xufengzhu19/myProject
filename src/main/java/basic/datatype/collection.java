package basic.datatype;

import entity.Student;

import java.util.*;

public class collection {
    public static void main(String[] args) {
        Collection myHashSet = new HashSet();

//        myHashSet.add(null);
//        System.out.println(myHashSet.size());//1

/**HashSet
 * hash算法通过hashCode计算出元素的存储位置，重写equals时也应该重写hashCode
 * HashSet是基于HashMap实现的。HashSet中的元素，只是存放在了底层HashMap的key上， 而value使用一个static final的Object对象标识
 */

//        Student student1=new Student();
//        Student student2=new Student();
//        System.out.println(student1==student2);
//        Map map=new HashMap();
//        map.put(student1,"1");
//        student1.setName("s1");
//        map.put(student2,"2");
//        student2.setName("s2");
//        System.out.println(student1.hashCode());
//        System.out.println(student2.hashCode());
//        System.out.println(map.get(student1));
//        System.out.println(map.get(student2));
//        System.out.println(map.size());
//        Iterator<Map.Entry<Student, String>> it = map.entrySet().iterator();
//        while (it.hasNext()){
//            Map.Entry<Student, String> entry = it.next();
//            System.out.println("key= " + entry.getKey().getName() + " and value= " + entry.getValue());
//        }
//        System.out.println(student1.getName());
//        System.out.println(student2.getName());

        LinkedList l1=new LinkedList();
        l1.add("a");
        l1.add("b");
        System.out.println(l1.get(0));

        List l2=new ArrayList();
        l2.add("a2");
        l2.add("b2");
        l1.removeFirst();
        l2.remove(0);
        System.out.println(l1.size());
        System.out.println(l2.size());
    }
}
