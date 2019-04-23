package basic.generic;

import java.util.List;

/**
 * 类型通配符，使用通配符是因为类型形参之间的继承关系在泛型中不能使用
 * 例如，List<Shape> list,不能list.add(circle)
 */
public abstract class Shape {
    public abstract void draw(Canvas c);

    public static void main(String[] args) {

    }
}

class Circle extends Shape {
    @Override
    public void draw(Canvas c) {
        System.out.println("circle");
    }
}
class Rectangle extends Shape{
    @Override
    public void draw(Canvas c) {
        System.out.println("rectangle");
    }
}
class Canvas{
    /**
     * 被限制的泛型通配符
     * List<? extends Shape>,shape的子类型
     * 类型形参上限，List<T extends Shape & java.io.Serializable>,最多一个父类,可以多个接口，且接口位于类之后
     */
    public void drawAll(List<?> list){
        for (Object obj:list){
            Shape s=(Shape) obj;
            s.draw(this);
        }
    }
}