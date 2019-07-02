package basic.thread;

import config.PropertyConfig;

import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;

/**
 * 线程创建的几种方式
 */

public class MyThread {
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
                new Thread1().start();//Thread-0#183
                new Thread1().start();//Thread-1#183

//                2.实现接口
                Thread2 t = new Thread2();
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

class Thread1 extends Thread {
    private int i;

    @Override
    public void run() {
        for (; i < 200; i++)
            System.out.println(getName() + "#" + i);
    }
}

class Thread2 implements Runnable {
    private int i;

    @Override
    public void run() {
        for (; i < 200; i++)
            System.out.println(Thread.currentThread().getName() + "#" + i);
    }
}

/**
 * 线程通讯-join测试线程
 */
class ThreadJoin implements Runnable {
    private Thread thread;

    public ThreadJoin(Thread thread) {
        this.thread = thread;
    }

    @Override
    public void run() {
        try {
            //实现原理synchronized方法，wait
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName());
    }
}

class threadWait implements Runnable {
    private List<Integer> list;

    public threadWait(List list) {
        this.list = list;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (list) {
                try {
                    //为防止出现notify过早的现象，需要有一个条件判断，已经notify过就无需再wait了，否则会一直阻塞
                    //使用while进行条件判断，而不是if，因为while结束后会再次进行判断看是否继续
                    while (list.isEmpty()) {
                        System.out.println("消费者" + Thread.currentThread().getName() + "  list为空，进行wait");
                        //wait释放锁，RUNNING变为WAITING，当前线程放置到对象的等待队列
                        //从wait返回的前提是获取了对象锁
                        list.wait();
                        System.out.println("消费者" + Thread.currentThread().getName() + "  退出wait");
                    }
                    Integer element = list.remove(0);
                    System.out.println("消费者" + Thread.currentThread().getName() + "  消费数据：" + element);
                    //调用notify或all之后，wait线程不会返回，需要等当前线程执行结束释放锁
                    //notify将等待队列的一个线程移动到同步队列,all是将全部移动，移动后由WAITING变为BLOCKED
                    //在多生产多消费的情况下，notify可能会造成‘假死’（生产者唤醒的是生产者），应使用notifyAll
                    list.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

class ThreadNotify implements Runnable {
    private List<Integer> list;
    private int maxLength;

    public ThreadNotify(List list, int maxLength) {
        this.list = list;
        this.maxLength = maxLength;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (list) {
                try {
                    //为防止出现notify过早的现象，需要有一个条件判断，已经notify过就无需再wait了，否则会一直阻塞
                    //使用while进行条件判断，而不是if，因为while结束后会再次进行判断看是否继续
                    while (list.size() == maxLength) {
                        System.out.println("生产者" + Thread.currentThread().getName() + "  list以达到最大容量，进行wait");
                        //wait释放锁，RUNNING变为WAITING，当前线程放置到对象的等待队列
                        //从wait返回的前提是获取了对象锁
                        list.wait();
                        System.out.println("生产者" + Thread.currentThread().getName() + "  退出wait");
                    }
                    Random random = new Random();
                    int i = random.nextInt();
                    System.out.println("生产者" + Thread.currentThread().getName() + " 生产数据" + i);
                    list.add(i);
                    //调用notify或all之后，wait线程不会返回，需要等当前线程执行结束释放锁
                    //notify将等待队列的一个线程移动到同步队列,all是将全部移动，移动后由WAITING变为BLOCKED
                    //在多生产多消费的情况下，notify可能会造成‘假死’（生产者唤醒的是生产者），应使用notifyAll
                    list.notifyAll();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

class ThreadAwait implements Runnable {
    private List<Integer> list;
    private Lock lock;

    public ThreadAwait(List list, Lock lock) {
        this.list = list;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (list.isEmpty()) {
                    System.out.println("消费者" + Thread.currentThread().getName() + "  list为空，进行wait");
                    PropertyConfig.Reent_Not_Empty_Lock.await();
                    System.out.println("消费者" + Thread.currentThread().getName() + "  退出wait");
                }
                Integer element = list.remove(0);
                System.out.println("消费者" + Thread.currentThread().getName() + "  消费数据：" + element);
                PropertyConfig.Reent_Not_Full_Lock.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

class ThreadSignal implements Runnable {
    private List<Integer> list;
    private int maxLength;
    private Lock lock;

    public ThreadSignal(List list, int maxLength, Lock lock) {
        this.list = list;
        this.maxLength = maxLength;
        this.lock = lock;
    }

    @Override
    public void run() {
        while (true) {
            lock.lock();
            try {
                while (list.size() == maxLength) {
                    System.out.println("生产者" + Thread.currentThread().getName() + "  list以达到最大容量，进行wait");
                    PropertyConfig.Reent_Not_Full_Lock.await();
                    System.out.println("生产者" + Thread.currentThread().getName() + "  退出wait");
                }
                Random random = new Random();
                int i = random.nextInt();
                System.out.println("生产者" + Thread.currentThread().getName() + " 生产数据" + i);
                list.add(i);
                PropertyConfig.Reent_Not_Empty_Lock.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }
}

class ThreadBQP implements Runnable{
    private BlockingQueue queue;
    public ThreadBQP(BlockingQueue queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (true) {
                Random random = new Random();
                int i = random.nextInt();
                System.out.println("生产者" + Thread.currentThread().getName() + "生产数据" + i);
                queue.put(i);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
class ThreadBQC implements Runnable{
    private BlockingQueue queue;
    public ThreadBQC(BlockingQueue queue) {
        this.queue = queue;
    }
    @Override
    public void run() {
        try {
            while (true) {
                Integer element = (Integer) queue.take();
                System.out.println("消费者" + Thread.currentThread().getName() + "正在消费数据" + element);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}