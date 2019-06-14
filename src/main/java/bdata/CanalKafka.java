package bdata;

import com.alibaba.otter.canal.client.CanalConnector;
import com.alibaba.otter.canal.client.CanalConnectors;
import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.CanalEntry.*;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Constants;
import utils.JsonHelper;

import java.io.IOException;
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
//            connector.subscribe(".*\\..*");
            connector.subscribe("canal_tsdb\\.sync_crm_cc_call_log");

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
        } catch (IllegalArgumentException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
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

        List<String> list = new ArrayList<>();
        message.getEntries().forEach(entry -> {
            if (entry.getEntryType() == EntryType.TRANSACTIONBEGIN
                    || entry.getEntryType() == EntryType.TRANSACTIONEND) {
                return;
            }
            CanalEntry.RowChange rowChage = null;
            try {
                rowChage = CanalEntry.RowChange.parseFrom(entry.getStoreValue());
            } catch (InvalidProtocolBufferException e) {
                e.printStackTrace();
            }

            EventType eventType = rowChage.getEventType();
            System.out.println(String.format("================> binlog[%s:%s] , name[%s,%s] , eventType : %s",
                    entry.getHeader().getLogfileName(), entry.getHeader().getLogfileOffset(),
                    entry.getHeader().getSchemaName(), entry.getHeader().getTableName(), eventType));

            StringBuilder rowStr = new StringBuilder();
            if (eventType == eventType.DELETE) {
                System.out.println("================delete==================");
                rowChage.getRowDatasList().forEach(rowData -> {
                    //                HashMap<String, String> map = new HashMap<>();
                    rowData.getAfterColumnsList().forEach(column -> {
                        //                    map.put(column.getName(), column.getValue());
                        rowStr.append(column.getValue() + ",");
                        System.out.println(column.getName() + " : " + column.getValue() + "    update=====================================" + column.getUpdated());
                    });
                    if (StringUtils.isBlank(rowStr)) {
                        System.out.println("rowStr is null!");
                        return;
                    }
                    list.add(rowStr.deleteCharAt(rowStr.length() - 1).toString());
                    System.out.println("rowStr=" + rowStr);
                });
            } else if (eventType == eventType.INSERT || eventType == eventType.UPDATE) {
                System.out.println("================insert==================");
                rowChage.getRowDatasList().forEach(rowData -> {
                    //                HashMap<String, String> map = new HashMap<>();
                    rowData.getAfterColumnsList().forEach(column -> {
                        //                    map.put(column.getName(), column.getValue());
                        rowStr.append(column.getValue() + ",");
                        System.out.println(column.getName() + " : " + column.getValue() + "    update=" + column.getUpdated());
                    });
                    if (StringUtils.isBlank(rowStr)) {
                        System.out.println("rowStr is null!");
                        return;
                    }
                    list.add(rowStr.deleteCharAt(rowStr.length() - 1).toString());
                    System.out.println("rowStr=" + rowStr);
                });
            }else {
                System.out.println("what?????????");
                return;
            }

        });
        if (CollectionUtils.isEmpty(list)) {
            System.out.println("list is null,can not send!");
            return;
        }
        ProducerRecord<String, String> record = new ProducerRecord<>(Kafka_Topic, JsonHelper.toJson(list));
        producer.send(record);
    }

    private String syncCrm(){
        return "";
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


