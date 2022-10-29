package de.marvinwest.infinitemonkey.model;

import java.time.LocalDateTime;

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
	private LocalDateTime startTime;
	
	/*
	 * JPA-Constructor
	 */
	MonkeysTypingRun() {
	}

	public MonkeysTypingRun(LocalDateTime startTime) {
		this.startTime = startTime;
	}
	
	public long getId() {
		return id;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}
	
}
