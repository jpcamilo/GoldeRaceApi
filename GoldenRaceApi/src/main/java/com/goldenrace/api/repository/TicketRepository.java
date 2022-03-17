package com.goldenrace.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goldenrace.api.model.TicketModel;

public interface TicketRepository extends JpaRepository<TicketModel, Long> {

	
	
}
