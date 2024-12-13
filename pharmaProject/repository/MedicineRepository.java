package com.casestudy.pharma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.pharma.entity.MedicineMaster;

public interface MedicineRepository extends JpaRepository<MedicineMaster, String>{
	MedicineMaster findByMedicineCode(String medicineCode);
	boolean existsByMedicineCode(String medicineCode);
}
