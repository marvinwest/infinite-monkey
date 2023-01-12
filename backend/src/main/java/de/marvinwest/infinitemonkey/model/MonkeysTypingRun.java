package de.marvinwest.infinitemonkey.model;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "monkeystypingrun")
public class MonkeysTypingRun {

	@Id
	@GeneratedValue
	private long id;

	@Column(nullable = false)
	private String targetText;

	@Column(nullable = false)
	private String alphabet;

	@Column(nullable = false)
	private LocalDateTime startTime;

	@Column(nullable = true)
	private LocalDateTime endTime;

	@Column(nullable = true)
	private Integer persistanceThreshold;

	/*
	 * JPA-Constructor
	 */
	MonkeysTypingRun() {
	}

	public MonkeysTypingRun(String targetText, String alphabet) {
		this.targetText = targetText;
		this.alphabet = alphabet;
		this.startTime = LocalDateTime.now();
	}

	public long getId() {
		return id;
	}

	public String getTargetText() {
		return targetText;
	}
	
	public String getAlphabet() {
		return alphabet;
	}

	/**
	 * Converts the single String of allowed characters into a list of Strings.
	 */
	public List<String> buildDistinctAlphabet() {
		return Arrays
				.stream(alphabet.split(""))
				.distinct()
				.toList();
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public Optional<LocalDateTime> maybeEndTime() {
		return Optional.ofNullable(endTime);
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public Optional<Integer> maybePersistanceThreshold() {
		return Optional.ofNullable(persistanceThreshold);
	}

	public void setPersistanceThreshold(Integer persistanceThreshold) {
		this.persistanceThreshold = persistanceThreshold;
	}

}
