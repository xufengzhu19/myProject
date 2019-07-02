package config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;

import java.util.Collections;
import java.util.Properties;

import static config.PropertyConfig.*;

public class KafkaConfig {
//    public static void main(String[] args) {
//        //消费者
////        while (true) {
////            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(1000));
////            records.forEach(s -> System.out.println("===========" + s.value()));
////        }
//
//        //生产者
//        ProducerRecord<String,String> record=new ProducerRecord<>(Kafka_Topic,"hello-kafka3333333333!");
//        kafkaProducer.send(record);
//        kafkaProducer.close();
//
//    }

    private volatile static KafkaConsumer kafkaConsumer;
    private volatile static KafkaProducer kafkaProducer;

    private KafkaConfig() {
    }

    public static KafkaConsumer getConsumer() {
        if (null == kafkaConsumer) {
            synchronized (KafkaConfig.class) {
                if (null == kafkaConsumer) {
                    Properties properties = new Properties();
                    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    properties.put("bootstrap.servers", Kafka_Brokers);
                    properties.put("group.id", Kafka_Group_ID);

                    kafkaConsumer = new KafkaConsumer<>(properties);
                    kafkaConsumer.subscribe(Collections.singletonList(Kafka_Topic));
                }
            }
        }
        return kafkaConsumer;
    }

    public static KafkaProducer getProducer() {
        if (null == kafkaProducer) {
            synchronized (KafkaConfig.class) {
                if (null == kafkaProducer) {
                    Properties properties = new Properties();
                    properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
                    properties.put("bootstrap.servers", Kafka_Cluster_Brokers);
                    properties.put("group.id", Kafka_Group_ID);

                    kafkaProducer = new KafkaProducer(properties);
                    kafkaConsumer.subscribe(Collections.singletonList(Kafka_Topic));
                }
            }
        }
        return kafkaProducer;
    }
}
