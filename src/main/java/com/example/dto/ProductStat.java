package com.example.dto;

public class ProductStat {
	private int num;
	private int id;
	private String name;
	private float price;
	private int amountsold;
	
	public ProductStat() {
	}

	public ProductStat(int num, int id, String name, float price, int amountsold) {
		this.num = num;
		this.id = id;
		this.name = name;
		this.price = price;
		this.amountsold = amountsold;
	}

	
	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getAmountsold() {
		return amountsold;
	}

	public void setAmountsold(int amountsold) {
		this.amountsold = amountsold;
	}
	
	
}
