package basic.thread;

import javafx.concurrent.Task;
import sun.rmi.server.InactiveGroupException;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

public class thread {
    public static void main(String[] args) {
        FutureTask<Integer> task = new FutureTask<Integer>((Callable<Integer>) () -> {
            int i = 0;
            for (; i < 200; i++)
                System.out.println(Thread.currentThread().getName() + "#" + i);
            return i;
        });

        for (int i = 0; i < 200; i++) {
            System.out.println(Thread.currentThread().getName() + "#" + i);
            if (i == 10) {
//                1.继承Thread类
                new thread1().start();//Thread-0#183
                new thread1().start();//Thread-1#183

//                2.实现接口
                thread2 t = new thread2();
                new Thread(t, "new1").start();//new1#0
                new Thread(t, "new2").start();//new2#0

//                3.Callable和Future结合
                new Thread(task, "task1").start();
                new Thread(task, "task2").start();

            }
        }

        try {
            //返回最终值，200
            System.out.println("子线程的返回值" + task.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
//       使用lambda表达式创建线程
        Thread labd = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("lambda");
            }
        });
        labd.start();


    }
}

class thread1 extends Thread {
    private int i;

    @Override
    public void run() {
        for (; i < 200; i++)
            System.out.println(getName() + "#" + i);
    }
}

class thread2 implements Runnable {
    private int i;

    @Override
    public void run() {
        for (; i < 200; i++)
            System.out.println(Thread.currentThread().getName() + "#" + i);
    }
}

class thread3 {

}