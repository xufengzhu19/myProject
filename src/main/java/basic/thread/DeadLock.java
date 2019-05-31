package basic.thread;

/**
 * 死锁，A B 线程都阻塞，互相需要
 */
public class DeadLock {
    public static void main(String[] args) {
        Thread t1=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized ("A"){
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    synchronized ("B"){
                        System.out.println("t1");
                    }
                }
            }
        });
        Thread t2=new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized ("B"){
                    synchronized ("A"){
                        System.out.println("t2");
                    }
                }
            }
        });
        t1.start();
        t2.start();
    }
}
