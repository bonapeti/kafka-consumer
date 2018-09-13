package bonapeti.kafka.consumer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

public class Consumer {

	public static void main(String[] args) {
		Properties props = new Properties();
	     props.put("bootstrap.servers", envVar("kafka.brokers"));
	     props.put("group.id", "test");
	     props.put("enable.auto.commit", "true");
	     props.put("auto.commit.interval.ms", "1000");
	     props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
	     
	     try (KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props)) {
	    	 consumer.subscribe(Arrays.asList("foo", "bar"));
		     while (true) {
		         ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
		         for (ConsumerRecord<String, String> record : records)
		             System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
		     }	 
	     }
	}
	
	private static String envVar(String name) {
		return Objects.requireNonNull(System.getProperty(name),"Environment variable '" + name + "' missing!");
	}

}
