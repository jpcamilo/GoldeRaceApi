package com.goldenrace.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.goldenrace.api.excepcion.ResourceNotFoundException;
import com.goldenrace.api.model.DetailModel;
import com.goldenrace.api.repository.DetailRepository;
import com.goldenrace.api.repository.TicketRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class DetailController {

	@Autowired
	TicketRepository ticketRepository;
	
	@Autowired
	DetailRepository detailRepository;

	
	@GetMapping("/tickets/{ticketId}/details")
	public ResponseEntity<List<DetailModel>> getAllDetailsByTicketId(
			@PathVariable(value = "ticketId") Long ticketId) {
		if (!ticketRepository.existsById(ticketId)) {
			throw new ResourceNotFoundException("Not found Ticket with id = " + ticketId);
		}

		List<DetailModel> details = detailRepository.findByTicketModelId(ticketId);
		return new ResponseEntity<>(details, HttpStatus.OK);
	}

	@GetMapping("/details/{id}")
	public ResponseEntity<DetailModel> getDetailsByTicketId(@PathVariable(value = "id") Long id) {
		DetailModel detail = detailRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Details with id = " + id));

		return new ResponseEntity<>(detail, HttpStatus.OK);
	}
	
	@PostMapping("/tickets/{ticketId}/details")
	public ResponseEntity<DetailModel> createdetail(@PathVariable(value = "ticketId") Long ticketId,
			@RequestBody DetailModel detailRequest) {
		DetailModel detail = ticketRepository.findById(ticketId).map(ticket -> {
			detailRequest.setTicketModel(ticket);
			return detailRepository.save(detailRequest);
		}).orElseThrow(() -> new ResourceNotFoundException("Not found Ticket with id = " + ticketId));

		return new ResponseEntity<>(detail, HttpStatus.CREATED);
	}

	@PutMapping("/details/{id}")
	public ResponseEntity<DetailModel> updateDetail(@PathVariable("id") long id, @RequestBody DetailModel detailRequest) {
		DetailModel detail = detailRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("CommentId " + id + "not found"));

		detail.setAmount(detailRequest.getAmount());
		detail.setDescription(detail.getDescription());
		
		return new ResponseEntity<>(detailRepository.save(detail), HttpStatus.OK);
	}

	@DeleteMapping("/details/{id}")
	public ResponseEntity<HttpStatus> deleteDetails(@PathVariable("id") long id) {
		detailRepository.deleteById(id);

		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@DeleteMapping("/tickets/{ticketId}/details")
	public ResponseEntity<List<DetailModel>> deleteAllDetailsOfTicket(
			@PathVariable(value = "ticketId") Long ticketId) {
		if (!ticketRepository.existsById(ticketId)) {
			throw new ResourceNotFoundException("Not found Ticket with id = " + ticketId);
		}

		detailRepository.deleteByTicketModelId(ticketId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}


}
