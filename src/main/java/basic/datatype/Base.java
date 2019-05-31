package basic.datatype;

import java.io.Serializable;

/**
 * 静态分派-方法重载，动态分派-方法重写
 */
public class Base {

//    public static void sayHello(char arg) {
//        System.out.println("hello char");
//    }
//
//    public static void sayHello(int arg) {
//        System.out.println("hello int");
//    }
//
//    public static void sayHello(long arg) {
//        System.out.println("hello long");
//    }
//
    public static void sayHello(Character arg) {
        System.out.println("hello Character");
    }

//    public static void sayHello(Serializable arg) {
//        System.out.println("hello Serializable");
//    }
//
//    public static void sayHello(Object arg) {
//        System.out.println("hello Object");
//    }
    public static void sayHello(char... arg) {
        System.out.println("hello char...");
    }

    byte aByte=1;
    short aShort=2;
    int aInt=4;
    long aLong=8;

    char aChar=2;

    float aFloat=4;
    double aDouble=8;

    boolean aBoolean=false;

    public static void main(String[] args) {
//char->int->long->float->double
//Serializable是Character包装类实现的接口
//Character不会自动转型Integer,只能转型他的接口或父类
//变长参数的优先级最低
        sayHello('a');
    }
}
