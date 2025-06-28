package org.example.KafkaPubSub;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static java.lang.Thread.sleep;

public class SimpleKafkaProducer {

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC_NAME = "test-topic";

    public static void main(String[] args) {
        // 1. Configure Producer Properties
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // Optional: Acks for message durability
        // 0 = no acknowledgment from server
        // 1 = acknowledgement from leader
        // all = acknowledgment from all in-sync replicas
        props.put(ProducerConfig.ACKS_CONFIG, "all");

        // Optional: Retries for transient errors
        props.put(ProducerConfig.RETRIES_CONFIG, 3);

        // 2. Create Kafka Producer

        try (KafkaProducer<String, String> producer = new KafkaProducer<>(props)) {
            // 3. Create a Producer Record
            // A record consists of topic, key (optional), and value.
            // Keys are used to ensure messages with the same key go to the same partition.
            String key = "message-key-1";
            for (int i = 0; i < 10; i++) {
                sleep(1000);
                String value = "Hello, Kafka! This is my " + i + "th message.";

                ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, key, value);

                // 4. Send the message asynchronously
                System.out.println("Attempting to send message: Key='" + key + "', Value='" + value + "' to topic: " + TOPIC_NAME);
                producer.send(record, (metadata, exception) -> {
                    if (exception == null) {
                        // Success!
                        System.out.println("Message sent successfully!");
                        System.out.println("Topic: " + metadata.topic());
                        System.out.println("Partition: " + metadata.partition());
                        System.out.println("Offset: " + metadata.offset());
                        System.out.println("Timestamp: " + metadata.timestamp());
                    } else {
                        // Failure
                        System.err.println("Error sending message: " + exception.getMessage());
                        exception.printStackTrace();
                    }
                }).get(); // .get() makes the send synchronous for demonstration. In real apps, don't block.
            }
        } catch (Exception e) {
            System.err.println("An error occurred during production: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. Close the producer to flush any outstanding records and free resources
            System.out.println("Producer closed.");
        }
    }
}