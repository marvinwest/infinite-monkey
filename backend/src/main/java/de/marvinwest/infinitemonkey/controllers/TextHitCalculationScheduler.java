package de.marvinwest.infinitemonkey.controllers;

import java.util.Comparator;
import java.util.List;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import de.marvinwest.infinitemonkey.model.MonkeysTypingRun;
import de.marvinwest.infinitemonkey.model.TextHit;

@Configuration
@EnableScheduling
public class TextHitCalculationScheduler {
	
	private static final List<String> ALPHABET = List.of("a","b","c","d","e","f","g","h","i","j","k","l","m","n","o","p","q","r","s","t","u","v","w","x","y","z");
	private static final String TARGET_TEXT = "loremipsumdolorsitament";
	private static final int PERSISTANCE_THRESHOLD = 2;
	
	private final Logger logger = LoggerFactory.getLogger(TextHitCalculationScheduler.class);
	
	@Autowired
	private TextHitRepository hitRepository;
	
	@Autowired
	private MonkeysTypingRunRepository runRepository;
	
	private TextHitState currentState;
	private TextHitState nextState;
	
	private boolean initialized = false;
	
	@EventListener(ApplicationReadyEvent.class)
	private void afterStartUp() {
		var character = fetchRandomCharacterFromAlphabet();
		this.currentState = new TextHitState(character);
		this.initialized = true;
	}

	@Scheduled(fixedDelay = 1000)
	public void calculateTextHit() {
		if (initialized) {
			var nextCharacter = this.fetchRandomCharacterFromAlphabet();
			this.nextState = new TextHitState(nextCharacter);
			this.currentStateUpdateCalculation();
		}
	}
	
	private String fetchRandomCharacterFromAlphabet() {
		Random rand = new Random();
		return ALPHABET.get(rand.nextInt(ALPHABET.size()));
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
		} else {
			maybePersistCurrentState(currentState);
			this.currentState = this.nextState;
		}
	}
	
	private boolean isCurrentStateValid() {
		var currentStateExpected = TARGET_TEXT.substring(0, currentState.getEndIndex());
		var currentStateGiven = currentState.getCurrentText();
		return currentStateExpected.equals(currentStateGiven);
	}
	
	private boolean isFutureStateValid() {
		var futureStateExpected = TARGET_TEXT.substring(0, currentState.getEndIndex() + nextState.getEndIndex());
		var futureStateGiven = currentState.getCurrentText() + nextState.getCurrentText();
		return  futureStateExpected.equals(futureStateGiven);
	}
	
	private void maybePersistCurrentState(TextHitState stateToPersist) {
		if (stateToPersist.getEndIndex() >= PERSISTANCE_THRESHOLD) {
			logger.info("Persist State at: " + stateToPersist.getCurrentText());
			
			Comparator<MonkeysTypingRun> runStartTimeComparator = Comparator.comparing(MonkeysTypingRun::getStartTime);
			var currentRun = runRepository.findAll()
					.stream()
					.max(runStartTimeComparator)
					.orElseThrow(() -> new IllegalStateException("At least one run has to be persisted"));
			
			var finalTextHit = stateToPersist.getCurrentText();
			var textHitToPersist = new TextHit(currentRun, finalTextHit);
			this.hitRepository.save(textHitToPersist);
		}
	}
	
}