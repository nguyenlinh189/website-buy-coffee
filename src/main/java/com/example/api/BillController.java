package com.example.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Bill;
import com.example.model.BillDetail;
import com.example.model.Product;
import com.example.model.Provider;
import com.example.model.User;
import com.example.repo.BillDetailRepository;
import com.example.repo.BillRepository;

@RestController
@RequestMapping("/bill")
public class BillController {
	@Autowired
	private BillRepository brepo;
	
	@Autowired
	private BillDetailRepository dbrepo;
	@PostMapping("/savebill")
	private Bill addBill(@RequestBody Bill bill) {
		return brepo.save(bill);
	}
	@PostMapping("/saveBillDetail")
	private BillDetail addBillDetail(@RequestBody BillDetail billDetail) {
		return dbrepo.save(billDetail);
	}
	@GetMapping("/getAllBill")
	private List<Bill> getAllBill() {
		return brepo.findAll();
	}
	@GetMapping("/getDetailBillById")
	private List<BillDetail>getAllBillDetails(@RequestParam(name="id")int id){
		Bill bill=brepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Category", "category id", id));;
		List<BillDetail>lisBillDetails=dbrepo.findByBill(bill);
		return lisBillDetails;
	}
}
