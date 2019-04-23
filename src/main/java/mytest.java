import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class mytest {

    public static int a = 0;
    public static String LOCK = "LOCK";

    public static void main(String[] args) {

        new Thread(() -> {
            synchronized (LOCK) {
                for (int i = 0; i < 1000; i++) {
                    a++;
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            System.out.println("out thread");
        }).start();

        System.out.println("2019年4月19日222333");
    }
}
