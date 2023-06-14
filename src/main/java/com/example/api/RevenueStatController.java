package com.example.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dto.BillStat;
import com.example.dto.Revenue;

@RestController
@CrossOrigin
public class RevenueStatController {
	private Calendar cal = Calendar.getInstance();
	private RestTemplate rest;
	
	@GetMapping("/monthly")
	public List<Revenue> getMonthlyRevenue() {
		
		List<Revenue> list = new ArrayList<>();
		rest = new RestTemplate();
		List<BillStat> bills = Arrays.asList(rest.getForObject("http://localhost:8082/billstat", BillStat[].class));
		Collections.sort(bills, new Comparator<BillStat>() {
			@Override
			public int compare(BillStat x, BillStat y) {
				return y.getDateCreate().compareTo(x.getDateCreate());
			}
		});
		float total = 0;
		int numBill = 0;
		cal.setTime(bills.get(0).getDateCreate());
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		for (BillStat bill : bills) {
			cal.setTime(bill.getDateCreate());
			if (cal.get(Calendar.MONTH) != month || cal.get(Calendar.YEAR) != year) {
				Revenue rev = new Revenue("Tháng " + month +"-"+year,numBill,total);
				list.add(rev); 
				month = cal.get(Calendar.MONTH);
				year = cal.get(Calendar.YEAR);
				total = bill.getTotal();
				numBill = 1;
			}
			else {
				total += bill.getTotal();
				numBill++;
			}
		}
		Revenue rev = new Revenue("Tháng " +month +"-"+year,numBill,total);
		list.add(rev);
		return list;
	}
	
	@GetMapping("/weekly")
	public List<Revenue> getWeeklyRevenue() {
		List<Revenue> list = new ArrayList<>();
		rest = new RestTemplate();
		List<BillStat> bills = Arrays.asList(rest.getForObject("http://localhost:8082/billstat", BillStat[].class));
		Collections.sort(bills, new Comparator<BillStat>() {
			@Override
			public int compare(BillStat x, BillStat y) {
				return y.getDateCreate().compareTo(x.getDateCreate());
			}
		});
		float total = 0;
		int numBill = 0;
		cal.setTime(bills.get(0).getDateCreate());
		int week = cal.get(Calendar.WEEK_OF_YEAR);
		int year = cal.get(Calendar.YEAR);
		for (BillStat bill : bills) {
			cal.setTime(bill.getDateCreate());
			if (cal.get(Calendar.WEEK_OF_YEAR) != week || cal.get(Calendar.YEAR) != year) {
				Revenue rev = new Revenue("Tuần " +week +"-"+year,numBill,total);
				list.add(rev);
				week = cal.get(Calendar.WEEK_OF_YEAR);
				year = cal.get(Calendar.YEAR);
				total = bill.getTotal();
				numBill = 1;
			}
			else {
				total += bill.getTotal();
				numBill++;
			}
		}
		Revenue rev = new Revenue("Tuần " + week +"-"+year,numBill,total);
		list.add(rev);
		return list;
	}
	
	@GetMapping("/yearly")
	public List<Revenue> getYearlyRevenue() {
		List<Revenue> list = new ArrayList<>();
		rest = new RestTemplate();
		List<BillStat> bills = Arrays.asList(rest.getForObject("http://localhost:8082/billstat", BillStat[].class));
		Collections.sort(bills, new Comparator<BillStat>() {
			@Override
			public int compare(BillStat x, BillStat y) {
				return y.getDateCreate().compareTo(x.getDateCreate());
			}
		});
		float total = 0;
		int numBill = 0;
		cal.setTime(bills.get(0).getDateCreate());
		int year = cal.get(Calendar.YEAR);
		for (BillStat bill : bills) {
			cal.setTime(bill.getDateCreate());
			if (cal.get(Calendar.YEAR) != year) {
				Revenue rev = new Revenue("Năm "+year,numBill,total);
				list.add(rev);
				year = cal.get(Calendar.YEAR);
				total = bill.getTotal();
				numBill = 1;
			}
			else {
				total += bill.getTotal();
				numBill++;
			}
		}
		Revenue rev = new Revenue("Năm " +year,numBill,total);
		list.add(rev);
		return list;
	}
}
