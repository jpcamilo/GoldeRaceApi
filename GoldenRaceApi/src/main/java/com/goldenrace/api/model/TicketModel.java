package com.goldenrace.api.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tickets")
public class TicketModel {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

    @CreationTimestamp
    private LocalDateTime creation_date ; 
	
	@Column(name = "total_mount")
	private int total_mount;
	
	@Column(name = "line_identifier")
	private String line_identifier;
	
	@Column(name = "description")
	private String description;
	
	@Column(name = "amount")
	private int amount;

	public TicketModel() {
	}

	public TicketModel(LocalDateTime creation_date, int total_mount, String line_identifier, String description, int amount) {
		this.creation_date = creation_date;
		this.total_mount = total_mount;
		this.line_identifier = line_identifier;
		this.description = description;
		this.amount = amount;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public LocalDateTime getCreation_date() {
		return creation_date;
	}

	public void setCreation_date(LocalDateTime creation_date) {
		this.creation_date = creation_date;
	}

	public int getTotal_mount() {
		return total_mount;
	}

	public void setTotal_mount(int total_mount) {
		this.total_mount = total_mount;
	}

	public String getLine_identifier() {
		return line_identifier;
	}

	public void setLine_identifier(String line_identifier) {
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

	@Override
	public String toString() {
		return "Ticket [id=" + id + ", creation_date=" + creation_date + ", total_mount=" + total_mount
				+ ", line_identifier=" + line_identifier + ", description=" + description + ", amount=" + amount + "]";
	}
	
}
