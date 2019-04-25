package kafka.tutorial1;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class ProducerDemo {
    public static void main(String[] args) {
        String bootstrapServers = "127.0.0.1:9092";

        //SET PRODUCER PROPERTIES

        Properties properties = new Properties();
        //https://kafka.apache.org/documentation/#producerconfigs
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        //Lets Kafka know what type of data you will be sending and how it should be serialized to bytes
        //As Kafka converts whatever we send to a bit pattern (0s and 1s)
        //In this case, we will send strings

        //CREATE PRODUCER

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        //SEND DATA - ASYNCHRONOUS

        //create a Producer record
        ProducerRecord<String, String> record = new ProducerRecord<String, String>("first_topic", "Hello, World!");
        producer.send(record);
        //flush data
        producer.flush();
        //close producer
        producer.close();
    }
}
