package others.work;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;
import org.apache.flink.util.Collector;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;

import static config.PropertyConfig.*;

public class FlinkKafka {
    public static void main(String[] args) throws Exception {
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        env.enableCheckpointing(60000);

        // 配置kafka和zookeeper的ip和端口
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", Kafka_Brokers);
        properties.setProperty("zookeeper.connect", ZK_URL);
        properties.setProperty("group.id", Kafka_Group_ID);

        FlinkKafkaConsumer011<String> myConsumer = new FlinkKafkaConsumer011<String>("test", new SimpleStringSchema(),
                properties);

        DataStream<String> stream = env.addSource(myConsumer);

        DataStream<Tuple2<String, Integer>> counts = stream.flatMap(new LineSplitter()).keyBy(0).sum(1);

        counts.print();

        env.execute("XFZFlinkKafka");
    }

    public static final class LineSplitter implements FlatMapFunction<String, Tuple2<String, Integer>> {
        private static final long serialVersionUID = 1L;

        public void flatMap(String value, Collector<Tuple2<String, Integer>> out) throws IOException {
            String[] tokens = value.toLowerCase().split("\\W+");
            for (String token : tokens) {
                if (token.length() > 0) {
                    File file = new File("/opt/software/out.log");
                    file.getName();
                    file.createNewFile();
                    FileWriter fileWriter = new FileWriter("/opt/software/out.log",true);
                    fileWriter.write(token);
                    fileWriter.close();
                    out.collect(new Tuple2<String, Integer>(token, 1));
                }
            }
        }
    }

}
