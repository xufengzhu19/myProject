package basic.thread;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class cas {
    //atomic包下AtomicStampedReference类提供compareAndSet解决ABA问题
    private AtomicInteger atomicInteger=new AtomicInteger();
    private int i=0;
    public static void main(String[] args) {
        final cas cas=new cas();
        List<Thread> list=new ArrayList<>();
        long start=System.currentTimeMillis();
        for (int j=0;j<100;j++){
            Thread t=new Thread(()->{
                for (int i=0;i<1000;i++){
                    cas.count();
                    cas.safeCount();
                }
            });
            list.add(t);
        }
        for (Thread t:list){
            t.start();
        }
        for (Thread t:list){
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(cas.i);
        System.out.println(cas.atomicInteger.get());
        System.out.println(System.currentTimeMillis()-start);

        try {
            TimeUnit.SECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void count() {
        i++;
    }

    private void safeCount() {
        while (true){
            int i=atomicInteger.get();
            boolean re=atomicInteger.compareAndSet(i,++i);
            if (re)
                break;
        }
    }

}
