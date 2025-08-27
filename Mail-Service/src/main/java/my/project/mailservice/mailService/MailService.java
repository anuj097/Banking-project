package my.project.mailservice.mailService;

import my.project.fundtransfer.dto.TransferEvent;

public interface MailService {

	public String sendMail();
	
	public void consumedData(TransferEvent event);
}
