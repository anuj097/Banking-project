package my.project.bankingsystem.bank.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import lombok.extern.slf4j.Slf4j;

// EXAMPLE of --> scheduler 

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfig {
	
//	@Scheduled(cron = "0 */5 * * * *")  // Executes in every 5 minute (0 seconds, 0 minutes, 0 hours)
//	public void ScheduleCron() {
//		log.info("THIS IS A APPLICATION fixed DELAY SCHEDULER : ", System.currentTimeMillis());
//	}
	
/*	@Scheduled(fixedDelay = 2000)
	public void ScheduleDelay() {
		log.info("THIS IS A APPLICATION fixed DELAY SCHEDULER : ", System.currentTimeMillis());
	}
	
	@Scheduled(fixedRate = 4000)
	public void ScheduleFixed() {
		log.info("THIS IS A APPLICATION fixed RATE SCHEDULER : ", System.currentTimeMillis());
	}
*/
}
