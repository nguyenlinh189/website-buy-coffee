package com.example.dto;

import java.util.Date;

public class BillStat {
	private int id;
	private String customer;
	private Date dateCreate;
	private float total;
	private int status;
	
	
	public BillStat() {
	}

	public BillStat(int id, String customer, Date dateCreate, float total) {
		super();
		this.id = id;
		this.customer = customer;
		this.dateCreate = dateCreate;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCustomer() {
		return customer;
	}

	public void setCustomer(String customer) {
		this.customer = customer;
	}

	public Date getDateCreate() {
		return dateCreate;
	}

	public void setDateCreate(Date dateCreate) {
		this.dateCreate = dateCreate;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
}
