package com.goldenrace.api;

import static org.assertj.core.api.Assertions.assertThat;


import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.SpringBootDependencyInjectionTestExecutionListener;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.goldenrace.api.model.DetailModel;
import com.goldenrace.api.model.TicketModel;
import com.goldenrace.api.repository.DetailRepository;
import com.goldenrace.api.repository.TicketRepository;

@RunWith(SpringRunner.class)
@DataJpaTest
public class JPAUnitTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	TicketRepository ticketRespository;

	@Autowired
	DetailRepository detailRespository;

	/////////////////////////////////////////
	//	Bloque de pruebas para el ticket
	//////////////////////////////////////////

	//verificar si no se han creado tickets, si el repositorio esta vacio
	@Test
	public void should_find_no_tickets_if_repository_is_empty() {
		Iterable<TicketModel> tickets = ticketRespository.findAll();
		assertThat(tickets).isEmpty();
	}
	
	//verificar cuando se crea un ticket
	@Test
	public void should_store_a_ticket() {
		TicketModel ticket = ticketRespository.save(new TicketModel(LocalDateTime.now(), 100));

		assertThat(ticket).hasFieldOrPropertyWithValue("total_mount", 100);
		
	}
	//verificar si estan todos los tickets creados
	@Test
	public void should_find_all_tickets() {
		TicketModel ticket1 = new TicketModel(LocalDateTime.now(), 100);
		entityManager.persist(ticket1);

		TicketModel ticket2 = new TicketModel(LocalDateTime.now(), 200);
		entityManager.persist(ticket2);

		TicketModel ticket3 = new TicketModel(LocalDateTime.now(), 300);
		entityManager.persist(ticket3);

		Iterable<TicketModel> tickets = ticketRespository.findAll();

		assertThat(tickets).hasSize(3).contains(ticket1, ticket2, ticket3);
	}
	//consulta de ticket por id
	@Test
	public void should_find_ticket_by_id() {
		TicketModel ticket1 = new TicketModel(LocalDateTime.now(), 100);
		entityManager.persist(ticket1);

		TicketModel ticket2 = new TicketModel(LocalDateTime.now(), 200);
		entityManager.persist(ticket2);

		TicketModel foundTicket = ticketRespository.findById(ticket2.getId()).get();

		assertThat(foundTicket).isEqualTo(ticket2);
	}
	//busqueda de ticket por fechas
	@Test
	public void should_find_tickets_by_date() throws Exception {
		TicketModel ticket1 = new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 100);
		entityManager.persist(ticket1);

		TicketModel ticket2 = new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 200);
		entityManager.persist(ticket2);
		
		Iterable<TicketModel> tickets = ticketRespository.findByCreation_Date(new SimpleDateFormat("yyyy-MM-dd").parse("2022-01-01"),new SimpleDateFormat("yyyy-MM-dd").parse("2022-04-01"));

		assertThat(tickets).hasSize(2).contains(ticket1, ticket2);
	}
	//actualizar ticket por id
	@Test
	public void should_update_ticket_by_id() {
		
		TicketModel ticket1 = new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 100);
		entityManager.persist(ticket1);

		TicketModel ticket2 = new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 200);
		entityManager.persist(ticket2);

		TicketModel updatedTicket = new TicketModel(LocalDateTime.parse("2022-01-12T18:14:01.184"), 500);

		TicketModel ticketNew = ticketRespository.findById(ticket2.getId()).get();
		ticketNew.setTotal_mount(updatedTicket.getTotal_mount());
		ticketRespository.save(ticketNew);

		TicketModel checkTicket = ticketRespository.findById(ticket2.getId()).get();

		assertThat(checkTicket.getId()).isEqualTo(ticket2.getId());
		assertThat(checkTicket.getTotal_mount()).isEqualTo(updatedTicket.getTotal_mount());

	}
	//Eliminar ticket por id
	@Test
	public void should_delete_ticket_by_id() {
		TicketModel ticket1 = new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 100);
		entityManager.persist(ticket1);

		TicketModel ticket2 = new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 200);
		entityManager.persist(ticket2);

		TicketModel ticket3 = new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 300);
		entityManager.persist(ticket3);
		
		ticketRespository.deleteById(ticket2.getId());

		Iterable<TicketModel> tickets = ticketRespository.findAll();

		assertThat(tickets).hasSize(2).contains(ticket1, ticket3);
	}
	//Eliminar todos los tickets
	@Test
	public void should_delete_all_tickets() {
		entityManager.persist(new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 100));
		entityManager.persist(new TicketModel(LocalDateTime.parse("2022-03-12T18:14:01.184"), 200));

		ticketRespository.deleteAll();

		assertThat(ticketRespository.findAll()).isEmpty();
	}

	
	/////////////////////////////////////////
	//	Bloque de pruebas para el ticket
	//////////////////////////////////////////

	///creacion de detail
	@Test
	public void should_create_detail() {
		TicketModel ticket1 = new TicketModel(LocalDateTime.now(), 100);
		entityManager.persist(ticket1);
		
		DetailModel detail1 = new DetailModel();
				detail1.setAmount(50);
				detail1.setDescription("Prueba");
				detail1.setLine_identifier(1);

				detail1.setTicketModel(ticket1);
				detailRespository.save(detail1);
				
			Iterable<DetailModel> founDetail = detailRespository.findByTicketModelId(ticket1.getId());
			assertThat(founDetail).hasSize(1);
	}	
	
	///consulta de detail por ticket
	@Test
	public void should_find_detail_ticket() {
		TicketModel ticket1 = new TicketModel(LocalDateTime.now(), 100);
		entityManager.persist(ticket1);
		
		DetailModel detail1 = new DetailModel();
				detail1.setAmount(50);
				detail1.setDescription("Prueba");
				detail1.setLine_identifier(1);

				detail1.setTicketModel(ticket1);
				detailRespository.save(detail1);

				List<DetailModel> founddetail = detailRespository.findByTicketModelId(ticket1.getId());

				assertThat(founddetail.get(0)).isEqualTo(detail1);
				
	}
	
	
	///consulta de detail por id
	@Test
	public void should_find_detail_by_id() {
		TicketModel ticket1 = new TicketModel(LocalDateTime.now(), 100);
		entityManager.persist(ticket1);
		
		DetailModel detail1 = new DetailModel();
				detail1.setAmount(50);
				detail1.setDescription("Prueba");
				detail1.setLine_identifier(1);

				detail1.setTicketModel(ticket1);
				detailRespository.save(detail1);
				
				DetailModel founddetail = detailRespository.findById(detail1.getId()).get();

				assertThat(founddetail).isEqualTo(detail1);
				
	}	
	///actualizacion de detail	
	@Test
	public void should_update_detail() {
		TicketModel ticket1 = new TicketModel(LocalDateTime.now(), 100);
		entityManager.persist(ticket1);
		
		DetailModel detail1 = new DetailModel();
				detail1.setAmount(50);
				detail1.setDescription("Prueba");
				detail1.setLine_identifier(1);

				detail1.setTicketModel(ticket1);
				detailRespository.save(detail1);

				DetailModel detail2 = new DetailModel();
				detail2.setAmount(50);
				detail2.setDescription("Cambiado");
				detail2.setLine_identifier(1);				
				
				DetailModel detailNew = detailRespository.findById(detail1.getId()).get();
				detailNew.setDescription(detail2.getDescription());
				detailRespository.save(detailNew);

				DetailModel checkTicket = detailRespository.findById(detail1.getId()).get();

				assertThat(checkTicket.getId()).isEqualTo(detail1.getId());
				assertThat(checkTicket.getDescription()).isEqualTo(detailNew.getDescription());

	}	
	
	
	///Eliminacion de detail por id
	@Test
	public void should_delete_detail_by_id() {
		TicketModel ticket1 = new TicketModel(LocalDateTime.now(), 100);
		entityManager.persist(ticket1);
		
		DetailModel detail1 = new DetailModel();
				detail1.setAmount(50);
				detail1.setDescription("Prueba");
				detail1.setLine_identifier(1);

				detail1.setTicketModel(ticket1);
				detailRespository.save(detail1);

		DetailModel detail2 = new DetailModel();
				detail2.setAmount(500);
				detail2.setDescription("Prueba2");
				detail2.setLine_identifier(1);

				detail2.setTicketModel(ticket1);
				detailRespository.save(detail2);
				
				detailRespository.deleteById(detail2.getId());

				Iterable<DetailModel> details = detailRespository.findByTicketModelId(ticket1.getId());

				assertThat(details).hasSize(1).contains(detail1);

	}		
	

	
}
