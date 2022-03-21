package com.goldenrace.api.repository;




import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.goldenrace.api.model.DetailModel;

public interface DetailRepository extends JpaRepository<DetailModel, Long> {

	List<DetailModel> findByTicketModelId(Long ticketId);
	
	@Transactional
	void deleteByTicketModelId(Long ticketId);


}
