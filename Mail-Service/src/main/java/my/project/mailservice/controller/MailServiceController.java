package my.project.mailservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import my.project.mailservice.mailService.MailService;

@RestController
@RequestMapping("/Mail-service")
public class MailServiceController {
	
	@Autowired
	private MailService mailService;
	
	@PostMapping("/send")
	public String sendMail() {
		return mailService.sendMail();	
	}
}
