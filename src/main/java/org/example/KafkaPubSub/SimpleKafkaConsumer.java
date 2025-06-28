package org.example.KafkaPubSub;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.errors.WakeupException;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimpleKafkaConsumer {
    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    private static final String TOPIC_NAME = "test-topic";
    private static final String CONSUMER_GROUP_ID = "my-java-consumer-group"; // A unique group ID
    private static final AtomicBoolean running = new AtomicBoolean(true); // Flag to control consumer loop

    public static void main(String[] args) {
        // 1. Configure Consumer Properties
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONSUMER_GROUP_ID);

        // Optional: Controls what happens when there is no initial offset in Kafka
        // or if the current offset does not exist any more on the server (e.g. topic was deleted)
        // "earliest": automatically reset the offset to the earliest offset (read from beginning)
        // "latest": automatically reset the offset to the latest offset (read only new messages)
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        // Optional: Auto commit offsets periodically
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000"); // Commit every 1 second

        // 2. Create Kafka Consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);

        // Add a shutdown hook to handle graceful exit

        try (consumer) {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Shutdown hook initiated. Shutting down consumer...");
                running.set(false);
                consumer.wakeup(); // Interrupt poll() to exit gracefully
            }));
            // 3. Subscribe to the topic(s)
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));
            System.out.println("Subscribed to topic: " + TOPIC_NAME + " in consumer group: " + CONSUMER_GROUP_ID);

            // 4. Poll for new data
            while (running.get()) {
                // Poll for records with a timeout. If no records within this duration, it returns empty.
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(10000));

                if (records.isEmpty()) {
                    // System.out.println("No records received. Polling again...");
                    continue; // Continue polling if no records
                }

                for (ConsumerRecord<String, String> record : records) {
                    System.out.println("----------------------------------------");
                    System.out.println("Received message:");
                    System.out.println("Topic: " + record.topic());
                    System.out.println("Partition: " + record.partition());
                    System.out.println("Offset: " + record.offset());
                    System.out.println("Key: " + record.key());
                    System.out.println("Value: " + record.value());
                    System.out.println("Timestamp: " + record.timestamp());
                    System.out.println("----------------------------------------");
                    // For a single message, you might want to break or exit here
//                    running.set(false); // Stop after processing the first message
//                    break; // Exit the for loop after processing one record
                }
            }
        } catch (WakeupException e) {
            // Expected exception when consumer.wakeup() is called
            System.out.println("Consumer wake up exception caught (normal shutdown).");
        } catch (Exception e) {
            System.err.println("An error occurred during consumption: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 5. Close the consumer
            System.out.println("Consumer closed.");
        }
    }
}