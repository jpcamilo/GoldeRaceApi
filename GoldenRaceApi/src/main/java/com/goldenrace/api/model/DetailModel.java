package com.goldenrace.api.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "detail")
public class DetailModel {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "detailModel_generator")
	private long id;
	
	@Column(name = "Line_Identifier")
	private int line_identifier;
	
	@Column(name = "Description")
	private String description;
	
	@Column(name = "Amount")
	private int amount;
	
	
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
	@JoinColumn(name = "ticketModel_id", nullable = false)
	private TicketModel ticketModel;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getLine_identifier() {
		return line_identifier;
	}

	public void setLine_identifier(int line_identifier) {
		this.line_identifier = line_identifier;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	public TicketModel getTicketModel() {
		return ticketModel;
	}

	public void setTicketModel(TicketModel ticket) {
		this.ticketModel = ticket;
	}




	@Override
	public String toString() {
		return "DetailModel [id=" + id + ", line_identifier=" + line_identifier + ", description=" + description
				+ ", amount=" + amount + ", ticketModel=" + ticketModel + "]";
	}

		
		
	
	
}
