
package utils;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xufengzhu
 */

public class Constants {
    public static final String IP = "172.16.2.136";
    //    public static final String IP = "62.234.55.241";


    public static final String Redis_Host = IP;
    public static final int Redis_Port = 36379;
    public static final String Redis_Pwd = "a123b";
    public static final String Redis_Host_W = "172.16.2.63";
    public static final int Redis_Port_W = 27001;
    public static final String Redis_Host_Cluster_W0 = "172.16.1.126";
    public static final int Redis_Port_Cluster_W0 = 2507;
    public static final String Redis_Host_Cluster_W1 = "172.16.1.129";
    public static final int Redis_Port_Cluster_W1 = 5041;
    public static final String Redis_Host_Cluster_W2 = "172.16.2.201";
    public static final int Redis_Port_Cluster_W2 = 3046;
    public static final String Redis_Pwd_Cluster_W = "qwe123!@#";


    public static final String LOCK = "LOCK";
    public static final ReentrantLock Reent_Lock = new ReentrantLock();
    public static final Condition Reent_Not_Empty_Lock = Reent_Lock.newCondition();
    public static final Condition Reent_Not_Full_Lock = Reent_Lock.newCondition();
    public static final LinkedBlockingQueue<Integer> BQueue = new LinkedBlockingQueue<>(5);


    public static final String Baidu_APP_ID = "16161873";
    public static final String Baidu_API_KEY = "bwwn6nQNFVqMsgjDA1V9eZfz";
    public static final String Baidu_SECRET_KEY = "5ZZrQICw4RnH5or8WSVgarU7FpbP7cfv";


    public static final String ZK_URL = IP+":22181";


    public static final String Kafka_Brokers = IP+":9092";
    public static final String Kafka_Brokers_Cluster = "hadoop-1:9092,hadoop-2:9092,hadoop-3:9092";
    public static final String Kafka_Topic = "test";
    public static final String Kafka_Topic_Wfs = "fs_datavo_xml";
    public static final String Kafka_Group_ID = "group.demo";


    public static final int Canal_Port=11111;
    public static final String Canal_Host="172.16.2.136";
    public static final String Canal_User="canal";
    public static final String Canal_Pwd="canal";
    public static final String Canal_Destination="example";


    public static final String Mail_User="user1@coderbbs.top";
    public static final String Mail_Pass="123456";
    public static final String Mail_QQ_User="304882553@qq.com";
    public static final String Mail_QQ_Pass_Imap="strjqzrqghhrcagi";
    public static final String Mail_QQ_Pass_Pop="owhvdprywjwubhej";



}

