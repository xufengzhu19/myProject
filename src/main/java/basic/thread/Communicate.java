package basic.thread;

import utils.Constants;

import java.util.LinkedList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Communicate {
    public static void main(String[] args) throws InterruptedException {
//        join();
        pc();
    }

    public static void join() {
        Thread pre = Thread.currentThread();
        for (int i = 0; i < 10; i++) {
            //每个线程拥有前一个的引用，需要等到前一个终止，才能从等待中返回
            Thread thread = new Thread(new ThreadJoin(pre), String.valueOf(i));
            thread.start();
            pre = thread;
        }
        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }

    /**
     * 实现生产者-消费者，有三种方式；
     * 1.记住当线程的优先级没有指定时，所有线程都携带普通优先级。
     * 2.优先级可以用从1到10的范围指定。10表示最高优先级，1表示最低优先级，5是普通优先级。
     * 3.记住优先级最高的线程在执行时被给予优先。但是不能保证线程在启动时就进入运行状态。
     * 4.与在线程池中等待运行机会的线程相比，当前正在运行的线程可能总是拥有更高的优先级。
     * 5.由调度程序决定哪一个线程被执行。
     * 6.t.setPriority()用来设定线程的优先级。
     * 7.记住在线程开始方法被调用之前，线程的优先级应该被设定。
     * 8.你可以使用常量，如MIN_PRIORITY,MAX_PRIORITY，NORM_PRIORITY来设定优先级。
     */
    public static void pc() throws InterruptedException {
        //wait-notify
//        wn();
        //await-signal
        as();
        //BlockingQueue
//        bq();

    }
    /**
     * 使用wait和notify时需要先调用对象加锁
     */
    public static void wn() throws InterruptedException {
        LinkedList linkedList = new LinkedList();
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 5; i++) {
            service.submit(new ThreadNotify(linkedList, 5));
        }
        for (int i = 0; i < 10; i++) {
            service.submit(new threadWait(linkedList));
        }
    }

    /**
     * 使用Lock可以有多个condition，不同的condition控制是消费还是生产操作，这样使用signal也不会产生假死情况；
     * 同步队列和等待队列和锁对象相关，同一个锁对象在同一个同步或等待队列中；
     * 在同步队列中哪个线程先获取锁是随机的；
     */
    public static void as(){
        LinkedList linkedList = new LinkedList();
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 5; i++) {
            service.submit(new ThreadSignal(linkedList, 5, Constants.Reent_Lock));
        }
        for (int i = 0; i < 5; i++) {
            service.submit(new ThreadAwait(linkedList, Constants.Reent_Lock));
        }

    }

    /**
     * 内部使用的Lock，signal，await
     */
    public static void bq(){
        ExecutorService service = Executors.newFixedThreadPool(15);
        for (int i = 0; i < 5; i++) {
            service.submit(new ThreadBQP(Constants.BQueue));
        }
        for (int i = 0; i < 10; i++) {
            service.submit(new ThreadBQC(Constants.BQueue));
        }
    }
}
