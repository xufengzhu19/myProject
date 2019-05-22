package others;

import org.apache.zookeeper.ZooKeeper;

public class zookeeper {

    public static void main(String[] arges) throws InterruptedException {
        zkWatcher zk = new zkWatcher();
        //初始化zk
//        ZooKeeper zooKeeper = zk.createConntection();
        System.out.println(zk.createNode("/test","123456",false));
    }

}
