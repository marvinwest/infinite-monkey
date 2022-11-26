package de.marvinwest.infinitemonkey.spec;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import de.marvinwest.infinitemonkey.controllers.MonkeysTypingRunRepository;
import de.marvinwest.infinitemonkey.controllers.TextHitCalculationScheduler;
import de.marvinwest.infinitemonkey.model.MonkeysTypingRun;

@RestController
public class RunResource {

	@Autowired
	private MonkeysTypingRunRepository runRepository;
	
	@Autowired
	private TextHitCalculationScheduler scheduler;
	
	// Is the optional persistanceThreshold actually persisted when in Request?
	@PostMapping(path = "/run/start",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public MonkeysTypingRun startRun(@RequestBody MonkeysTypingRunRequest request) {
		final var targetText = request.getTargetText();
		final var alphabet = request.getAlphabet();
		MonkeysTypingRun newRun = new MonkeysTypingRun(targetText, alphabet);
		request.maybePersistanceThreshold().ifPresent(newRun::setPersistanceThreshold);
		
		newRun = runRepository.save(newRun);
		scheduler.startRun(newRun);
		
		return newRun;
	}
	
	// Does Not display Optional parameters that should be present.
	@GetMapping(path = "/run")
	public List<MonkeysTypingRun> fetchRuns() {
		return runRepository.findAll();
	}
	
}
