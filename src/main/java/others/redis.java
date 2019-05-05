package others;

import redis.clients.jedis.Jedis;
import utils.Constants;

public class redis {
    public static void main(String[] args) {
        Jedis jedis = new Jedis(Constants.RedisHost, Constants.RedisPort);
        jedis.auth("a123b");
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
