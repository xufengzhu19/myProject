package bdata;

import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;

import static utils.Constants.Kafka_Brokers;
import static utils.Constants.Kafka_Topic;

public class KafkaProducer {
    public static void main(String[] args) {
        Properties properties=new Properties();
        properties.put("key.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer","org.apache.kafka.common.serialization.StringSerializer");
        properties.put("bootstrap.servers",Kafka_Brokers);

        org.apache.kafka.clients.producer.KafkaProducer<String,String> producer=new org.apache.kafka.clients.producer.KafkaProducer<>(properties);
        ProducerRecord<String,String> record=new ProducerRecord<>(Kafka_Topic,"hello,kafka3333333333!");
        producer.send(record);
        producer.close();

    }
}
