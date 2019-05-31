package basic.thread;

import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

import static utils.Constants.LOCK;

public class MyLock {
    public static void main(String[] args) {
        syncBlock();
        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void syncBlock(){
        //加锁，拥有LOCK的Monitor
        synchronized (LOCK){
            System.out.println("enter syn block!");
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static ReentrantLock rLock=new ReentrantLock();
    static int i=0;
    public static void reentLock(){
        rLock.lock();
        i++;
        rLock.unlock();
        if (rLock.tryLock()){
            System.out.println("我获得了锁！");
        }
    }

}

