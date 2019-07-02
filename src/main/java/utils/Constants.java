package utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Constants {
    public static final ReentrantLock Reent_Lock = new ReentrantLock();
    public static final Condition Reent_Not_Empty_Lock = Reent_Lock.newCondition();
    public static final Condition Reent_Not_Full_Lock = Reent_Lock.newCondition();
    public static final LinkedBlockingQueue<Integer> BQueue = new LinkedBlockingQueue<>(5);
}
