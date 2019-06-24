package bdata;

import com.alibaba.otter.canal.protocol.CanalEntry;
import com.alibaba.otter.canal.protocol.Message;
import com.google.protobuf.InvalidProtocolBufferException;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import java.time.Duration;
import java.util.*;

import static utils.Constants.*;

public class KafkaConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("bootstrap.servers", Kafka_Brokers_Cluster);
//        properties.put("bootstrap.servers", Kafka_Brokers);
        properties.put("group.id", Kafka_Group_ID);

        org.apache.kafka.clients.consumer.KafkaConsumer<String, String> consumer = new org.apache.kafka.clients.consumer.KafkaConsumer<>(properties);
        consumer.subscribe(Collections.singletonList(Kafka_Topic_Wfs));
//        consumer.subscribe(Collections.singletonList(Kafka_Topic));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            records.forEach(s -> System.out.println("===========" + s.value()));
        }

    }
}
