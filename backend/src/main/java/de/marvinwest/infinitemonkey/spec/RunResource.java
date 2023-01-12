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
	
	// make alphabet distinct string of distinct characters before persisting
	@PostMapping(path = "/run/start",
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public RunResponse startRun(@RequestBody RunRequest request) {
		final var targetText = request.getTargetText();
		final var alphabet = request.getAlphabet();
		MonkeysTypingRun newRun = new MonkeysTypingRun(targetText, alphabet);
		request.maybePersistanceThreshold().ifPresent(newRun::setPersistanceThreshold);
		
		newRun = runRepository.save(newRun);
		scheduler.startRun(newRun);
		
		return buildResponseBody(newRun);
	}
	
	// Does Not display Optional parameters that should be present.
	@GetMapping(path = "/run")
	public List<RunResponse> fetchRuns() {
		return runRepository.findAll()
				.stream()
				.map(this::buildResponseBody)
				.toList();
	}
	
	public RunResponse buildResponseBody(MonkeysTypingRun run) {
		final var id = run.getId();
		final var targetText = run.getTargetText();
		final var alphabet = run.getAlphabet();
		
		final var runResponse = new RunResponse(id, targetText, alphabet);
		
		run.maybePersistanceThreshold().ifPresent(runResponse::setPersistanceThreshold);
		
		return runResponse;
	}
	
}
