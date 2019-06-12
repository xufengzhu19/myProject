package others;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;
import java.util.LinkedHashSet;
import java.util.Set;

import static utils.Constants.*;

public class RedisDemo {
    public static void main(String[] args) {

//        standalone();
        cluster();

    }

    public static void standalone() {
        Jedis jedis = new Jedis(RedisHostW, RedisPortW);
//        jedis.auth(RedisPwd);
        jedis.connect();
//    jedis.set("a", "va");
//jedis.set("b", "vb");
//  jedis.set("c", "vc");
//  jedis.set("d", "vd");
//  jedis.set("e", "vd");
//  jedis.set("f", "vd");
//  jedis.set("g", "vdssss");
        jedis.set("mykey", "myvalue");
        System.out.println(jedis.get("mykey"));
    }

    public static void cluster() {
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 最大连接数
        poolConfig.setMaxTotal(1);
        // 最大空闲数
        poolConfig.setMaxIdle(1);
        // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
        // Could not get a resource from the pool
        poolConfig.setMaxWaitMillis(1000);
        Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
        nodes.add(new HostAndPort(RedisHostCW0, RedisPortCW0));
        nodes.add(new HostAndPort(RedisHostCW1, RedisPortCW1));
        nodes.add(new HostAndPort(RedisHostCW2, RedisPortCW2));

        JedisCluster cluster = new JedisCluster(nodes, 60, 60, 3, RedisPwd_CW, poolConfig);
        while (true) {
            String name = cluster.get("LOCK_SYN_PRODUCT");
            System.out.println(name);
        }
    }
}
