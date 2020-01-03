package com.calculoFolha.CalculoFolha.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calculoFolha.CalculoFolha.models.Event;

public interface EventRepository extends JpaRepository<Event, Long>{
	Event findById(long id);
}
