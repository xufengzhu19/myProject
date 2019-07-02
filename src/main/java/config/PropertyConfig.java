
package config;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author xufengzhu
 */

public class PropertyConfig {
    private volatile static Properties properties;

    private PropertyConfig() {
    }

    public static Properties getProperties() {
        if (null == properties) {
            synchronized (PropertyConfig.class) {
                if (null == properties) {
                    Properties properties = new Properties();
                    try {
                        BufferedReader bufferedReader = new BufferedReader(new FileReader("app.properties"));
                        properties.load(bufferedReader);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return properties;
    }

    public static final String IP = getProperties().get("ip").toString();

    public static final String Redis_Host = getProperties().get("redis.host").toString();
    public static final String Redis_Port = getProperties().get("redis.port").toString();
    public static final String Redis_Pwd = getProperties().get("redis.pwd").toString();
    public static final String Redis_Cluster_Host1 = getProperties().get("redis.cluster.host1").toString();
    public static final String Redis_Cluster_Host2 = getProperties().get("redis.cluster.host2").toString();
    public static final String Redis_Cluster_Host3 = getProperties().get("redis.cluster.host3").toString();
    public static final String Redis_Cluster_Port1 = getProperties().get("redis.cluster.port1").toString();
    public static final String Redis_Cluster_Port2 = getProperties().get("redis.cluster.port2").toString();
    public static final String Redis_Cluster_Port3 = getProperties().get("redis.cluster.port3").toString();

    public static final String Baidu_APP_ID = getProperties().get("baidu.app.id").toString();
    public static final String Baidu_API_KEY = getProperties().get("baidu.api.key").toString();
    public static final String Baidu_SECRET_KEY = getProperties().get("baidu.secret.key").toString();

    public static final String ZK_URL = getProperties().get("zookeeper.url").toString();

    public static final String Mail_User = getProperties().get("mail.user").toString();
    public static final String Mail_Pass = getProperties().get("mail.pwd").toString();
    public static final String Mail_QQ_User = getProperties().get("mail.qq.user").toString();
    public static final String Mail_QQ_Pass_Imap = getProperties().get("mail.qq.pwd.imap").toString();
    public static final String Mail_QQ_Pass_Pop = getProperties().get("mail.qq.pwd.pop").toString();

    public static final String Kafka_Brokers = getProperties().get("kafka.brokers").toString();
    public static final String Kafka_Cluster_Brokers = getProperties().get("kafka.cluster.brokers").toString();
    public static final String Kafka_Topic = getProperties().get("kafka.topic").toString();
    public static final String Kafka_Group_ID = getProperties().get("kafka.group.id").toString();

    public static final String Canal_Host = getProperties().get("canal.host").toString();
    public static final String Canal_Port = getProperties().get("canal.port").toString();
    public static final String Canal_User = getProperties().get("canal.user").toString();
    public static final String Canal_Pwd = getProperties().get("canal.pwd").toString();
    public static final String Canal_Destination = getProperties().get("canal.destination").toString();
    public static final String Canal_DB= getProperties().get("canal.subscribe").toString();

    public static final String Mysql_Url= getProperties().get("mysql.url").toString();
    public static final String Mysql_User= getProperties().get("mysql.user").toString();
    public static final String Mysql_Pwd= getProperties().get("mysql.pwd").toString();


}

