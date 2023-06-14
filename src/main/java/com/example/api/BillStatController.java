package com.example.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.dto.BillDetailStat;
import com.example.dto.BillStat;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Bill;
import com.example.model.BillDetail;
import com.example.repo.BillDetailRepository;
import com.example.repo.BillRepository;


@RestController
@CrossOrigin("*")
public class BillStatController {
	private RestTemplate rest = new RestTemplate(); 
	@Autowired
	private BillRepository brepo;
	@Autowired
	private BillDetailRepository drepo;
	
	@GetMapping("/billstat")
	public List<BillStat> getBillStat() {
		
		List<Bill> bills = brepo.findAll();
		List<BillStat> billStats = new ArrayList<>();
		
		for (Bill bill : bills) {
			float total = 0;
			BillStat billStat = new BillStat();
			billStat.setId(bill.getId());
			billStat.setCustomer(bill.getNameShip());
			billStat.setDateCreate(bill.getDateCreate());
			billStat.setStatus(bill.getStatus());
			List<BillDetail> details = drepo.findByBill(bill);
			for (BillDetail billDetail : details) {
				total += billDetail.getProduct().getPrice() * billDetail.getQuantity();
			}
			billStat.setTotal(total);
			billStats.add(billStat);
		}
		
		Collections.sort(billStats, new Comparator<BillStat>() {
			@Override
			public int compare(BillStat x, BillStat y) {
				return y.getDateCreate().compareTo(x.getDateCreate());
			}
		});
		return billStats;
	}
	
	@GetMapping("billstat/{id}")
	public List<BillDetailStat> getBillDetail(@PathVariable int id) {
		List<BillDetailStat> stats = new ArrayList<>();
		Bill bill=brepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", id));;
		List<BillDetail>details=drepo.findByBill(bill);
		int num = 1;
		for (BillDetail detail : details) {
			BillDetailStat stat = new BillDetailStat();
			stat.setNum(num);
			stat.setName(detail.getProduct().getName());
			stat.setQuantity(detail.getQuantity());
			stat.setTotal(detail.getProduct().getPrice()*detail.getQuantity());
			stats.add(stat);
			num++;
		}
		return stats;
	}
}
