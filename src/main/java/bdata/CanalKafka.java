package bdata;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.internals.Topic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;
import utils.JsonHelper;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import static utils.Constants.*;



public class CanalKafka {

    public static CanalKafkaProducer producer = new CanalKafkaProducer();

    public static void main(String args[]) {
        producer.init();
        CanalToKafkaServer.execute();

//        canalTest();
    }

    private static void canalTest() {
        // 创建链接
        CanalConnector connector = CanalConnectors.newSingleConnector(new InetSocketAddress(Constants.Canal_Host, Constants.Canal_Port),
                Canal_Destination, Canal_User, Canal_Pwd);
        int batchSize = 1000;
        int emptyCount = 0;
        try {
            connector.connect();
            connector.subscribe(".*\\..*");

            connector.rollback();
            int totalEmtryCount = 1200;
            while (emptyCount < totalEmtryCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
//                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    emptyCount = 0;
                    // System.out.printf("message[batchId=%s,size=%s] \n", batchId, size);
                    printEntry(message.getEntries());
                }

                connector.ack(batchId); // 提交确认
                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
        } finally {
            connector.disconnect();
        }
    }

    private static void printEntry(List<Entry> entrys) {
        for (Entry entry : entrys) {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN
                    || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                continue;
            }

            RowChange rowChage = null;
            try {
                rowChage = RowChange.parseFrom(entry.getStoreValue());
            } catch (Exception e) {
                throw new RuntimeException("ERROR ## parser of eromanga-event has an error , data:" + entry.toString(),
                        e);
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));


            for (RowData rowData : rowChage.getRowDatasList()) {
                if (eventType == EventType.DELETE) {
                    printColumn(rowData.getBeforeColumnsList());
                } else if (eventType == EventType.INSERT) {
                    printColumn(rowData.getAfterColumnsList());
                } else {
                    System.out.println("-------> before");
                    printColumn(rowData.getBeforeColumnsList());
                    System.out.println("-------> after");
                    printColumn(rowData.getAfterColumnsList());
                }
            }
        }
    }

    private static void printColumn(List<Column> columns) {
        for (Column column : columns) {
            System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
        }
    }

}

//======================================================================================================================
class SimpleCanalClient {

    private CanalConnector connector = null;

    public SimpleCanalClient() {

        // 创建链接
        connector = CanalConnectors.newSingleConnector(new InetSocketAddress(Canal_Host, Canal_Port), Canal_Destination, Canal_User, Canal_Pwd);
    }

    public List<Entry> execute(int batchSize) throws InstantiationException, IllegalAccessException, NoSuchMethodException, SecurityException {

        //int batchSize = 1;
        int emptyCount = 0;
//        Object obj = clazz.newInstance();
//        Method method = clazz.getMethod("send", Message.class);
        try {
            connector.connect();
            connector.subscribe(".*\\..*");
//            connector.subscribe("test.test1");

            connector.rollback();
            int totalEmptyCount = 1200;
            while (emptyCount < totalEmptyCount) {
                Message message = connector.getWithoutAck(batchSize); // 获取指定数量的数据
                long batchId = message.getId();
                int size = message.getEntries().size();
                if (batchId == -1 || size == 0) {
                    emptyCount++;
                    System.out.println("empty count : " + emptyCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                } else {
                    emptyCount = 0;
//                    method.invoke(obj, message);
                    CanalKafka.producer.send(message);
                }
                connector.ack(batchId); // 提交确认

                // connector.rollback(batchId); // 处理失败, 回滚数据
            }

            System.out.println("empty too many times, exit");
//        } catch (IllegalAccessException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        } catch (IllegalArgumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
        } finally {
            connector.disconnect();
        }
        return null;
    }
}

class CanalKafkaProducer {

    private static final Logger logger = LoggerFactory.getLogger(CanalKafkaProducer.class);

    private Producer<String, String> producer;

    public void init() {

        Properties properties = new Properties();
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers", Kafka_Brokers);
        producer = new KafkaProducer<String, String>(properties);
    }

    public void stop() {
        try {
            logger.info("## stop the kafka producer");
            producer.close();
        } catch (Throwable e) {
            logger.warn("##something goes wrong when stopping kafka producer:", e);
        } finally {
            logger.info("## kafka producer is down.");
        }
    }

    public void send(Message message) throws IOException {

//        ProducerRecord<String, Message> record;
//        if (topic.getPartition() != null) {
//            record = new ProducerRecord<String, Message>(topic.getTopic(), topic.getPartition(), null, message);
//        } else {
//            record = new ProducerRecord<String, Message>(topic.getTopic(), message);
//        }
        List<HashMap<String, String>> list = new ArrayList<>();
        message.getEntries().forEach(entry -> {
            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }
            rowChage.getRowDatasList().forEach(rowData -> {
                HashMap<String, String> map = new HashMap<>();
                rowData.getAfterColumnsList().forEach(column -> {
                    map.put(column.getName(), column.getValue());
                    System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
                });
                list.add(map);
            });
        });
        ProducerRecord<String, String> record = new ProducerRecord<>(Kafka_Topic, JsonHelper.toJson(list));
        producer.send(record);
//        if (logger.isDebugEnabled()) {
//            logger.debug("send message to kafka topic: {} \n {}", topic.getTopic(), message.toString());
//        }
    }
}

class CanalToKafkaServer {
    public static void execute() {
        SimpleCanalClient simpleCanalClient = new SimpleCanalClient();
        try {
//            simpleCanalClient.execute(1000, CanalKafkaProducer.class);
            simpleCanalClient.execute(1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

//class KafkaToHdfs extends Thread {
//    private static String kafkaHost = null;
//    private static String kafkaGroup = null;
//    private static String kafkaTopic = null;
//    private static String hdfsUri = null;
//    private static String hdfsDir = null;
//    private static String hadoopUser = null;
//    private static Boolean isDebug = false;
//
//    private ConsumerConnector consumer = null;
//
//    private static Configuration hdfsConf = null;
//    private static FileSystem hadoopFS = null;
//
//    public static void main(String[] args) {
////        if (args.length < 6) {
////            useage();
////            System.exit(0);
////        }
////        Map<String, String> user = new HashMap<String, String>();
////        user = System.getenv();
////        user.put("HADOOP_USER_NAME","hadoop");
////        if (user.get("HADOOP_USER_NAME") == null) {
////            System.out.println("请设定hadoop的启动的用户名，环境变量名称：HADOOP_USER_NAME，对应的值是hadoop的启动的用户名");
////            System.exit(0);
////        } else {
////            hadoopUser = user.get("HADOOP_USER_NAME");
////        }
//        hadoopUser = "hadoop";
//        init(args);
//
//        System.out.println("开始启动服务...");
//
//        hdfsConf = new Configuration();
//        try {
//            hdfsConf.set("fs.defaultFS", hdfsUri);
//            hdfsConf.set("dfs.support.append", "true");
//            hdfsConf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
//            hdfsConf.set("dfs.client.block.write.replace-datanode-on-failure.enable", "true");
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        //创建好相应的目录
//        try {
//            hadoopFS = FileSystem.get(hdfsConf);
//            //如果hdfs的对应的目录不存在，则进行创建
//            if (!hadoopFS.exists(new Path("/" + hdfsDir))) {
//                hadoopFS.mkdirs(new Path("/" + hdfsDir));
//            }
//            hadoopFS.close();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//
//        KafkaToHdfs selfObj = new KafkaToHdfs();
//        selfObj.start();
//
//        System.out.println("服务启动完毕，监听执行中");
//    }
//
//    public void run() {
//        Properties props = new Properties();
//        props.put("zookeeper.connect", kafkaHost);
//        props.put("group.id", kafkaGroup);
//
//        props.put("zookeeper.session.timeout.ms", "10000");
//        props.put("zookeeper.sync.time.ms", "200");
//        props.put("auto.commit.interval.ms", "1000");
//        props.put("auto.offset.reset", "smallest");
//        props.put("format", "binary");
//        props.put("auto.commit.enable", "true");
//        props.put("serializer.class", "kafka.serializer.StringEncoder");
//
//        ConsumerConfig consumerConfig = new ConsumerConfig(props);
//        this.consumer = Consumer.createJavaConsumerConnector(consumerConfig);
//
//        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
//        topicCountMap.put(kafkaTopic, new Integer(1));
//        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
//        KafkaStream<byte[], byte[]> stream = consumerMap.get(kafkaTopic).get(0);
//        ConsumerIterator<byte[], byte[]> it = stream.iterator();
//
//        while (it.hasNext()) {
//            String tmp = new String(it.next().message());
//            String fileContent = null;
//
//            if (!tmp.endsWith("\n"))
//                fileContent = new String(tmp + "\n");
//            else
//                fileContent = tmp;
//
//            debug("receive: " + fileContent);
//
//            try {
//                hadoopFS = FileSystem.get(hdfsConf);
//
//                String fileName = "/" + hdfsDir + "/" +
//                        (new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime())) + ".txt";
//
//                Path dst = new Path(fileName);
//
//                if (!hadoopFS.exists(dst)) {
//                    FSDataOutputStream output = hadoopFS.create(dst);
//                    output.close();
//                }
//
//                InputStream in = new ByteArrayInputStream(fileContent.getBytes("UTF-8"));
//                OutputStream out = hadoopFS.append(dst);
//                IOUtils.copyBytes(in, out, 4096, true);
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                try {
//                    hadoopFS.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//        consumer.shutdown();
//    }
//
//    private static void init(String[] args) {
//        kafkaHost = "10.175.118.105:2182";
//        kafkaGroup = "test-consumer-group";
//        kafkaTopic = "test";
//        hdfsUri = "hdfs://10.175.118.105:9000";
//        hdfsDir = "shxsh";
//        if (args.length > 5) {
//            if (args[5].equals("true")) {
//                isDebug = true;
//            }
//        }
//
//        debug("初始化服务参数完毕,参数信息如下");
//        debug("KAFKA_HOST: " + kafkaHost);
//        debug("KAFKA_GROUP: " + kafkaGroup);
//        debug("KAFKA_TOPIC: " + kafkaTopic);
//        debug("HDFS_URI: " + hdfsUri);
//        debug("HDFS_DIRECTORY: " + hdfsDir);
//        debug("HADOOP_USER: " + hadoopUser);
//        debug("IS_DEBUG: " + isDebug);
//    }
//
//    private static void debug(String str) {
//        if (isDebug) {
//            System.out.println(str);
//        }
//    }
//
//    private static void useage() {
//        System.out.println("* kafka写入到hdfs的Java工具使用说明 ");
//        System.out.println("# java -cp kafkatohdfs.jar KafkaToHdfs KAFKA_HOST KAFKA_GROUP KAFKA_TOPIC HDFS_URI HDFS_DIRECTORY IS_DEBUG");
//        System.out.println("*  参数说明:");
//        System.out.println("*   KAFKA_HOST      : 代表kafka的主机名或IP:port，例如：namenode:2181,datanode1:2181,datanode2:2181");
//        System.out.println("*   KAFKA_GROUP     : 代表kafka的组，例如：test-consumer-group");
//        System.out.println("*   KAFKA_TOPIC     : 代表kafka的topic名称 ，例如：usertags");
//        System.out.println("*   HDFS_URI        : 代表hdfs链接uri ，例如：hdfs://namenode:9000");
//        System.out.println("*   HDFS_DIRECTORY  : 代表hdfs目录名称 ，例如：usertags");
//        System.out.println("*  可选参数:");
//        System.out.println("*   IS_DEBUG        : 代表是否开启调试模式，true是，false否，默认为false");
//    }
//}


