package com.casestudy.pharma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.pharma.entity.MedicineTypeMaster;

public interface MedicineTypeRepository extends JpaRepository<MedicineTypeMaster, String>{
	
	MedicineTypeMaster findByMedicineTypeCode(char medicineTypeCode);
	boolean existsByMedicineTypeCode(char medicineTypeCode);
	

}
