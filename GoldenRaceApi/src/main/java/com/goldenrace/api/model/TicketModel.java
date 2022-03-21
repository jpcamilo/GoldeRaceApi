package com.goldenrace.api.model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

@Entity
@Table(name = "tickets")
public class TicketModel {

	@Id
	  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ticketModel_generator")
	private long id;

    @CreationTimestamp
    private LocalDateTime creation_date ; 
	
	@Column(name = "total_mount")
	private int total_mount;
	 
	public TicketModel() {
	}

	public TicketModel(LocalDateTime creation_date, int total_mount) {
		this.creation_date = creation_date;
		this.total_mount = total_mount;
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

	
	
}
