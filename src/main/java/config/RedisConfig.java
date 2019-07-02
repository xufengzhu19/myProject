package config;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

import static config.PropertyConfig.*;

public class RedisConfig {
//    public static void main(String[] args) {
//        getJedis().set("a","2019年6月28日14:33:28");
//        System.out.println(getJedis().get("a"));
//    }

    private volatile static Jedis jedis;
    private volatile static JedisCluster jedisCluster;

    private RedisConfig() {
    }

    public static Jedis getJedis(Integer... index) {
        if (null == jedis) {
            synchronized (RedisConfig.class) {
                if (null == jedis) {
                    jedis = new Jedis("127.0.0.1", 6379);
//                    jedis.auth("12345");
                    if (Objects.nonNull(index[0]))
                        jedis.select(index[0]);
                    jedis.connect();
                }
            }
        }
        return jedis;
    }

    public static JedisCluster getJedisCluster() {
        if (null == jedisCluster) {
            synchronized (RedisConfig.class) {
                if (null == jedisCluster) {
                    JedisPoolConfig poolConfig = new JedisPoolConfig();
                    // 最大连接数
                    poolConfig.setMaxTotal(10);
                    // 最大空闲数
                    poolConfig.setMaxIdle(1);
                    // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
                    // Could not get a resource from the pool
                    poolConfig.setMaxWaitMillis(1000);
                    Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
                    nodes.add(new HostAndPort(Redis_Cluster_Host1, Integer.parseInt(Redis_Cluster_Port1)));
                    nodes.add(new HostAndPort(Redis_Cluster_Host2, Integer.parseInt(Redis_Cluster_Port2)));
                    nodes.add(new HostAndPort(Redis_Cluster_Host3, Integer.parseInt(Redis_Cluster_Port3)));

                    jedisCluster = new JedisCluster(nodes, 60, 60, 3, Redis_Pwd, poolConfig);
                }
            }
        }
        return jedisCluster;
    }
}
