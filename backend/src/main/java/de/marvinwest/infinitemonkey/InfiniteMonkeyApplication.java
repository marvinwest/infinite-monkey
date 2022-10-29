package de.marvinwest.infinitemonkey;

import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import de.marvinwest.infinitemonkey.controllers.MonkeysTypingRunRepository;
import de.marvinwest.infinitemonkey.model.MonkeysTypingRun;

@SpringBootApplication
public class InfiniteMonkeyApplication {
	
	private final Logger logger = LoggerFactory.getLogger(InfiniteMonkeyApplication.class);
	
	@Autowired
	private MonkeysTypingRunRepository runRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(InfiniteMonkeyApplication.class, args);
	}
	
	@EventListener(ApplicationReadyEvent.class)
	public void afterStartUp() {
		var timeOfStart = LocalDateTime.now();
		var currentRun = new MonkeysTypingRun(timeOfStart);
		
		logger.info("Creating new run..");
		this.runRepository.save(currentRun);
		
		logger.info("Fetch number of runs..");
		List<MonkeysTypingRun> allRuns = this.runRepository.findAll();
		logger.info("Number of runs: " +  allRuns.size());
		
	}

}
