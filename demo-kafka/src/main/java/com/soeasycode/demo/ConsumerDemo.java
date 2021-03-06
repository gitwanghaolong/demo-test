package com.soeasycode.demo;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.connect.source.SourceRecord;

import java.util.Arrays;
import java.util.Properties;

/**
 * 消费者
 */
public class ConsumerDemo {
    public static void main(String[] args){
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "192.168.1.135:9092");//xxx是服务器集群的ip
        properties.put("group.id", "jd-group");
        properties.put("enable.auto.commit", "true");
        properties.put("auto.commit.interval.ms", "1000");
        properties.put("auto.offset.reset", "latest");
        properties.put("session.timeout.ms", "30000");
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String, SourceRecord> kafkaConsumer = new KafkaConsumer<String, SourceRecord>(properties);
        kafkaConsumer.subscribe(Arrays.asList("demo"));
        while (true) {
            ConsumerRecords<String, SourceRecord> records = kafkaConsumer.poll(100);
            for (ConsumerRecord<String, SourceRecord> record : records) {
                System.out.println("-----------------");
                System.out.printf("offset = %d, value = %s", record.offset(), record.value());
                System.out.println();
            }
        }
    }
}
