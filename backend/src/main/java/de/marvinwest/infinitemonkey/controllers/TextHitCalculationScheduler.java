package de.marvinwest.infinitemonkey.controllers;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import de.marvinwest.infinitemonkey.model.MonkeysTypingRun;
import de.marvinwest.infinitemonkey.model.TextHit;

// MONOLITHIC: REFACTOR HARD! Javadoc missing!
// Scheduler seems to fast, logs suggest, that a new character is added before persistance
@Configuration
@EnableScheduling
public class TextHitCalculationScheduler {
	
	private final Logger logger = LoggerFactory.getLogger(TextHitCalculationScheduler.class);
	
	@Autowired
	private TextHitRepository hitRepository;
	
	@Autowired
	private MonkeysTypingRunRepository runRepository;
	
	private TextHitState currentState;
	private TextHitState nextState;
	
	private MonkeysTypingRun currentRun;
	
	private boolean initialized = false;
		
	public void startRun(MonkeysTypingRun run) {
		this.initialized = false;
		this.logger.info("A new run is started ...");
		if (this.currentRun != null) {
			currentRun.setEndTime(LocalDateTime.now());
			runRepository.save(currentRun);
		}
		this.currentRun = run;
		var character = fetchRandomCharacterFromAlphabet();
		this.currentState = new TextHitState(character);
		this.initialized = true;
		
	}

	@Scheduled(fixedDelay = 1000)
	public void calculateTextHit() {
		if (initialized) {
			var nextCharacter = this.fetchRandomCharacterFromAlphabet();
			logger.info("new calculation, new character: " + nextCharacter);
			this.nextState = new TextHitState(nextCharacter);
			this.currentStateUpdateCalculation();
		}
	}
	
	private String fetchRandomCharacterFromAlphabet() {
		Random rand = new Random();
		List<String> alphabet = currentRun.buildDistinctAlphabet();
		return alphabet.get(rand.nextInt(alphabet.size()));
	}
	
	private void currentStateUpdateCalculation() {
		if (isCurrentStateValid()) {
			this.futureStateUpdateCalculation();
		} else {
			this.currentState = this.nextState;
		}
	}
	
	private void futureStateUpdateCalculation() {
		if (isFutureStateValid()) {
			this.currentState.addTextHit(nextState.getCurrentText());
			if (currentState.getCurrentText().equals(currentRun.getTargetText())) {
				var textHitToPersist = new TextHit(currentRun, currentState.getCurrentText());
				this.hitRepository.save(textHitToPersist);
				this.currentRun.setEndTime(LocalDateTime.now());
				this.runRepository.save(currentRun);
				
				this.currentRun = null;
				this.initialized = false;
				this.logger.info("A run was finished.");
			}
		} else {
			currentRun.maybePersistanceThreshold()
				.ifPresent(threshold -> this.maybePersistCurrentState(threshold, currentState));
			this.currentState = this.nextState;
		}
	}
	
	private boolean isCurrentStateValid() {
		var currentStateExpected = currentRun.getTargetText().substring(0, currentState.getEndIndex());
		var currentStateGiven = currentState.getCurrentText();
		return currentStateExpected.equals(currentStateGiven);
	}
	
	private boolean isFutureStateValid() {
		var futureStateGiven = currentState.getCurrentText() + nextState.getCurrentText();
		var futureStateExpected = currentRun.getTargetText().substring(0, currentState.getEndIndex() + nextState.getEndIndex());
		return  futureStateExpected.equals(futureStateGiven);
	}
	
	// No Persistance after starting runs via POST
	private void maybePersistCurrentState(Integer persistanceThreshold, TextHitState stateToPersist) {
		if (stateToPersist.getEndIndex() >= persistanceThreshold) {
			logger.info("Persist State at: " + stateToPersist.getCurrentText());
			
			var finalTextHit = stateToPersist.getCurrentText();
			var textHitToPersist = new TextHit(currentRun, finalTextHit);
			this.hitRepository.save(textHitToPersist);
		}
	}
	
}