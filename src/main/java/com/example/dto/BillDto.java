package com.example.dto;

import java.util.List;

import com.example.model.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BillDto {
	private int id;
	private String addressShip;
	private String phoneShip;
	private String nameShip;
	private String paymentType;
	private User user;
	private List<DetailBillDTO> detailBills;
}
