package com.goldenrace.api.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

import com.goldenrace.api.excepcion.*;
import com.goldenrace.api.model.DetailModel;
import com.goldenrace.api.model.TicketModel;
import com.goldenrace.api.repository.TicketRepository;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class TicketController {

	@Autowired
	TicketRepository ticketRepository;
	
	//para traer un ticket por id
	@GetMapping("/ticket/{id}")
	public ResponseEntity<TicketModel> getTicketById(@PathVariable("id") long id) {
		TicketModel ticketData = ticketRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Not found Ticket with id = " + id));

		return new ResponseEntity<>(ticketData, HttpStatus.OK);

	}
	
	//Busqueda entre fechas
	@GetMapping("/ticket/{dBegin}/{dEnd}")
	public ResponseEntity<List<TicketModel>> getTicketByDate(@PathVariable("dBegin") String dBegin, @PathVariable("dEnd") String dEnd ) {
		SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
		Date dBeginFormateada;
		Date dEndFormateada;
		try {
			dBeginFormateada = formato.parse(dBegin);
			dEndFormateada = formato.parse(dEnd);
			List<TicketModel> ticketData = ticketRepository.findByCreation_Date(dBeginFormateada,dEndFormateada);
			if (ticketData.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			} else {
				return new ResponseEntity<>(ticketData, HttpStatus.OK);			
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	//Para crear un nuevo Ticket
	@PostMapping("/ticket")
	public ResponseEntity<TicketModel> createTicket(@RequestBody TicketModel ticket) {
	    
		TicketModel _ticket = ticketRepository.save(new TicketModel(LocalDateTime.now(), ticket.getTotal_mount()));
		
	    return new ResponseEntity<>(_ticket, HttpStatus.CREATED);
			
	}
	
	@PutMapping("/ticket/{id}")
	public ResponseEntity<TicketModel> updateTicket(@PathVariable("id") long id, @RequestBody TicketModel ticket) {
		Optional<TicketModel> ticketData = ticketRepository.findById(id);
		if (ticketData.isPresent()) {
			TicketModel _ticket = ticketData.get();
			_ticket.setTotal_mount(ticket.getTotal_mount());
			return new ResponseEntity<>(ticketRepository.save(_ticket), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	@DeleteMapping("/ticket/{id}")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") long id) {
		try {
			ticketRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
