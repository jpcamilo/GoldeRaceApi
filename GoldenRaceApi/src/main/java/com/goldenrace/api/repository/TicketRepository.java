package com.goldenrace.api.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goldenrace.api.model.TicketModel;

public interface TicketRepository extends JpaRepository<TicketModel, Long> {

	@Query(value = "SELECT * FROM TICKETS T WHERE T.CREATION_DATE BETWEEN CAST(?1 AS DATE) AND CAST(?2 AS DATE);", nativeQuery=true)
	List<TicketModel> findByCreation_Date(Date dBegin, Date dEnd);
	
}
