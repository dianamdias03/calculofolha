package com.calculoFolha.CalculoFolha.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.calculoFolha.CalculoFolha.models.Moviment;

public interface MovimentRepository extends JpaRepository<Moviment, Long>{
	Moviment findById(long id);
}
