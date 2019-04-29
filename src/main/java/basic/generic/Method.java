package basic.generic;

import java.util.Collection;
import java.util.Collections;

/**
 * 泛型方法，在声明方法时定义一个或多个类型形参
 */
public class Method<E>{
//    Collections.copy()
//     public static <T> void copy(List<? super T> dest, List<? extends T> src)

    /**泛型方法和类型通配符的区别
     *  T 产生的唯一效果就是在不同的调用点传入不同的实际类型，这种情况应该使用通配符：
     *         通配符就是被设计来实现灵活的子类化的。
     */
    boolean test(Collection<? extends E> c){
        return false;
    }
    <T extends E> boolean test1(Collection<T> c){
        return false;
    }



}
