package others;

public class ZookeeperDemo {

    public static void main(String[] arges) throws InterruptedException {
        ZkWatcherDemo zk = new ZkWatcherDemo();
        //初始化zk
//        ZooKeeper zooKeeper = zk.createConntection();
        System.out.println(zk.createNode("/test","123456",false));
    }

}
