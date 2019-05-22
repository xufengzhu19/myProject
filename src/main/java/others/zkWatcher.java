package others;

import com.hankcs.hanlp.classification.utilities.io.ILogger;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import utils.Constants;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

public class zkWatcher implements Watcher {
    private Logger logger=Logger.getLogger(zkWatcher.class);
//    static {
//        PropertyConfigurator.configure("log4j.properties");
//    }

    private static ZooKeeper zk;

    private static final int time_out = 5000;

    private static CountDownLatch countDownLatch = new CountDownLatch(1);

    /**
     * 重写process用来监控watcher事件
     *
     * @param watchedEvent
     */
    @Override
    public void process(WatchedEvent watchedEvent) {
        Event.EventType type = watchedEvent.getType();
        Event.KeeperState state = watchedEvent.getState();
        //是否连接成功
        if (Event.KeeperState.SyncConnected == state) {
            if (Event.EventType.None == type) {
                System.out.println("成功连接到zk服务器");
                //zk连接成功将计数器清零
                countDownLatch.countDown();
            }
            //如果是创建节点事件
            else if (Event.EventType.NodeCreated == type) {
                System.out.println("创建节点成功");
            }
            //如果节点中的数据被改变
            else if (Event.EventType.NodeDataChanged == type) {
                System.out.println("节点数据发生变化");
            }
            //如果子节点的数据发生改变
            else if (Event.EventType.NodeChildrenChanged == type) {
                System.out.println("子节点数据发生改变");
            }
            //节点被删除事件
            else if (Event.EventType.NodeDeleted == type) {
                System.out.println("节点被删除");
            } else ;

        } else if (Event.KeeperState.Disconnected == state) {
            //连接失败直接退出
            return;
        }
        try {
            //阻塞当前线程,直到zk初始化完毕.
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取zk连接
     *
     * @return
     * @throws InterruptedException
     */
    public ZooKeeper createConntection() throws InterruptedException {
        try {
            zk = new ZooKeeper(Constants.ZK_URL, time_out, this);
            //阻塞当前线程,当创好连接以后再继续执行当前线程
            countDownLatch.await();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return zk;
    }

    /**
     * 关闭zk连接
     *
     * @param zooKeeper
     */
    public void close(ZooKeeper zooKeeper) {
        try {
            zooKeeper.close();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 验证此节点是否存在
     *
     * @param path
     * @param watcher
     * @return
     */
    public Stat extis(String path, boolean watcher) {
        try {
            Stat exists = zk.exists(path, watcher);
            return exists;
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建节点
     *
     * @param path 节点路径
     * @param data 数据
     * @return
     */
    public String createNode(String path, String data, boolean watcher) {
        String result = null;
        try {
            zk=createConntection();
            Stat exists = zk.exists(path, watcher);
            if (null == exists) {
                result = zk.create(path, data.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            } else {
                System.out.println("节点已经存在");
            }
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 修改节点的数据
     *
     * @param path    节点路径
     * @param data    数据
     * @param version 版本号
     * @return
     */
    public Stat setNodeData(String path, String data, int version, boolean watcher) {
        Stat stat = null;
        try {
            Stat exists = zk.exists(path, watcher);
            if (exists != null)
                stat = zk.setData(path, data.getBytes(), version);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return stat;
    }

    /**
     * 删除节点
     *
     * @param path    节点的路径
     * @param version 版本号,当输入-1时候是指所有数据
     */
    public void deleteNode(String path, int version, boolean watcher) {
        try {
            Stat exists = zk.exists(path, watcher);
            if (exists != null)
                zk.delete(path, version);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取节点数据
     *
     * @param path
     * @return
     */
    public String getNodeData(String path, boolean watcher) {
        byte[] data = null;
        try {
            data = zk.getData(path, watcher, null);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new String(data);
    }
}
