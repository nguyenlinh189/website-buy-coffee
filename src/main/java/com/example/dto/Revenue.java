package com.example.dto;

public class Revenue {
	private String time;
	private int numBill;
	private float total;
	
	public Revenue() {
	}

	public Revenue(String time, int numBill, float total) {
		super();
		this.time = time;
		this.numBill = numBill;
		this.total = total;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public int getNumBill() {
		return numBill;
	}

	public void setNumBill(int numBill) {
		this.numBill = numBill;
	}

	public float getTotal() {
		return total;
	}

	public void setTotal(float total) {
		this.total = total;
	}
	
	
}
