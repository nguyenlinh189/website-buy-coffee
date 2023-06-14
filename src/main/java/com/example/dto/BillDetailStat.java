package com.example.dto;

public class BillDetailStat {
	private int num;
	private String name;
	private int quantity;
	private float total;
	
	
	public BillDetailStat() {
	}

	public BillDetailStat(int num, String name, int quantity, float total) {
		this.num = num;
		this.name = name;
		this.quantity = quantity;
		this.total = total;
	}
	

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	
}
