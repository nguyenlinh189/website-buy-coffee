package com.example.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.model.Bill;
import com.example.model.BillDetail;

public interface BillDetailRepository extends JpaRepository<BillDetail, Integer>{
	List<BillDetail> findByBill(Bill bill);
}
