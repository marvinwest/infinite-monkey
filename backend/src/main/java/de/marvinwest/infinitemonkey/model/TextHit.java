package de.marvinwest.infinitemonkey.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "texthit")
public class TextHit {

	@Id
	@GeneratedValue
	private long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(nullable = false, name = "monkeystypingrun_id")
	private MonkeysTypingRun run;

	@Column(nullable = false)
	private LocalDateTime timeOfOccurence;

	@Column(nullable = false, columnDefinition = "text")
	private String finalHit;
	
	/*
	 * JPA-Constructor
	 */
	TextHit() {
	}

	public TextHit(MonkeysTypingRun run, String finalHit) {
		this.run = run;
		this.timeOfOccurence = LocalDateTime.now();
		this.finalHit = finalHit;
	}

	public long getId() {
		return id;
	}

	public MonkeysTypingRun getRun() {
		return run;
	}

	public LocalDateTime getTimeOfOccurence() {
		return timeOfOccurence;
	}

	public String getFinalHit() {
		return finalHit;
	}

}
