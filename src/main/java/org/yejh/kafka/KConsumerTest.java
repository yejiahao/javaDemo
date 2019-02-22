package org.yejh.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KConsumerTest {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KafkaProperties.KAFKA_CONNECT);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "test");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");// false -> consumer.commitAsync(); 手动提交偏移量
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");

        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        // consumer.subscribe(Arrays.asList(KafkaProperties.TOPIC));

        // 订阅指定的分区
        TopicPartition partition0 = new TopicPartition(KafkaProperties.TOPIC, 0);
        consumer.assign(Arrays.asList(partition0));

        // 控制消费的位置
        consumer.seek(partition0, 80);

        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf("partition: %d, offset: %d, key: %s, value: %s%n", record.partition(), record.offset(), record.key(), record.value());
            }
        }
    }
}
