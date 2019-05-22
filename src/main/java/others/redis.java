package others;

import redis.clients.jedis.Jedis;

import static utils.Constants.*;

public class redis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis(RedisHost, RedisPort);
        jedis.auth(RedisPwd);
        jedis.connect();
//    jedis.set("a", "va");
//jedis.set("b", "vb");
//  jedis.set("c", "vc");
//  jedis.set("d", "vd");
//  jedis.set("e", "vd");
//  jedis.set("f", "vd");
//  jedis.set("g", "vdssss");
        System.out.println(jedis.get("k1"));


    }
}
