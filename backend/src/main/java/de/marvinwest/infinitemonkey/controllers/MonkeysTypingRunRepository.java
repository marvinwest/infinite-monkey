package de.marvinwest.infinitemonkey.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import de.marvinwest.infinitemonkey.model.MonkeysTypingRun;

public interface MonkeysTypingRunRepository extends JpaRepository<MonkeysTypingRun, Long> {

}
