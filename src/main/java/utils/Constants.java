package utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xufengzhu
 */

public class Constants {
    public static final String RedisHost = "62.234.55.241";
    public static final int RedisPort = 36379;

    public static final String CodeStr = "";
    public static final String Token = "";
    public static final String URL = "";

    public static final String LOCK = "LOCK";
    public static final ReentrantLock REENTRANT_LOCK = new ReentrantLock();
    public static final Condition notEmpty = REENTRANT_LOCK.newCondition();
    public static final Condition notFull = REENTRANT_LOCK.newCondition();
    public static final LinkedBlockingQueue<Integer> BQueue = new LinkedBlockingQueue<>(5);

    public static final String APP_ID = "16161873";
    public static final String API_KEY = "bwwn6nQNFVqMsgjDA1V9eZfz";
    public static final String SECRET_KEY = "5ZZrQICw4RnH5or8WSVgarU7FpbP7cfv";


}
