package my.project.mailservice.ExternalService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import my.project.fundtransfer.dto.TransferEvent;
import my.project.mailservice.mailService.MailService;

@Service
public class ConsumerService {
	
	//private MailService mailService;
	
	@KafkaListener(topics = "transfer-events", groupId = "fund-group")
	public void listen(TransferEvent event) {
		System.out.println("The data is : "+ event.toString());
		
		//mailService.consumedData(event);
	}
}
