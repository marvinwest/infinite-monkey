package de.marvinwest.infinitemonkey;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class InfiniteMonkeyApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(InfiniteMonkeyApplication.class, args);
	}
	
//	@EventListener(ApplicationReadyEvent.class)
//	public void afterStartUp() {
//		var timeOfStart = LocalDateTime.now();
//		var currentRun = new MonkeysTypingRun(timeOfStart);
//		
//		logger.info("Creating new run..");
//		this.runRepository.save(currentRun);
//		
//		logger.info("Fetch number of runs..");
//		List<MonkeysTypingRun> allRuns = this.runRepository.findAll();
//		logger.info("Number of runs: " +  allRuns.size());
//	}

}
