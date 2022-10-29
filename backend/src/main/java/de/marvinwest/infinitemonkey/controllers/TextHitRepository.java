package de.marvinwest.infinitemonkey.controllers;

import org.springframework.data.jpa.repository.JpaRepository;

import de.marvinwest.infinitemonkey.model.TextHit;

public interface TextHitRepository extends JpaRepository<TextHit, Long> {

}
