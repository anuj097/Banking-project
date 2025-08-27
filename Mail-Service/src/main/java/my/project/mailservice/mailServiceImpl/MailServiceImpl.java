package my.project.mailservice.mailServiceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import my.project.fundtransfer.dto.TransferEvent;
import my.project.mailservice.mailService.MailService;

@Service
public class MailServiceImpl implements MailService {
	
	private TransferEvent event;

	@Override
	public String sendMail() {
		System.out.println("Inside sendMail "+ event.toString());
		return "Mail sent successfully";
	}

	public void consumedData(TransferEvent data) {
		BeanUtils.copyProperties(data, event);
		
	}

	
}
