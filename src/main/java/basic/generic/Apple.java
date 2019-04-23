package basic.generic;

/**
 * 泛型使编译器可以在编译期间对类型进行检查以提高类型安全，减少运行时由于对象类型不匹配引发的异常。
 *不能在静态变量或静态方法中使用类型形参，系统不会生成泛型类
 * @param <T>
 */
public class Apple<T> {
    private T info;

    public Apple() {
    }

    public Apple(T info) {
        this.info = info;
    }

    public T getInfo() {
        return info;
    }

    public void setInfo(T info) {
        this.info = info;
    }

    public static void main(String[] args) {
        Apple<String> apple = new Apple<>("red");
        Apple<Integer> apple1 = new Apple<>(1);
    }
}

class A extends Apple {
//如果不传T，会认为是object类型
}
