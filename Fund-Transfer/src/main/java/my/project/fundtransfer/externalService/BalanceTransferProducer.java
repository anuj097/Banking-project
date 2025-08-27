package my.project.fundtransfer.externalService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import my.project.fundtransfer.dto.TransferEvent;

/*
 * ******************  Few Things to note down for Mac OS Terminal to Start a Kafka  ****************************
 * 
 * START ZOOKEEPRER SERVER: bin/zookeeper-server-start.sh  config/zookeeper.properties
 * 
 * START KAFKA BROKER: bin/kafka-server-start.sh config/server.properties
 * 
 * CREATE A TOPIC: bin/kafka-topics.sh --create --topic transfer-events --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
 * 
 * START PRODUCER: bin/kafka-console-producer.sh --topic transfer-events --bootstrap-server localhost:9092
 * 
 * START CONSUMER: bin/kafka-console-consumer.sh --topic transfer-events --from-beginning --bootstrap-server localhost:9092
 */

@Service
public class BalanceTransferProducer {
	
	@Value("${spring.kafka.topic}")
	private String topic;
	
	KafkaTemplate<String, TransferEvent> kafkaTemplate;
	
	@Autowired
    public BalanceTransferProducer(KafkaTemplate<String, TransferEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

	public void sendMessage(TransferEvent event) {
		kafkaTemplate.send(topic, event);
	}

}
