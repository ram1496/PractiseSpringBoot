package com.casestudy.pharma.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.casestudy.pharma.entity.ShippingMaster;

public interface ShippingMasterRepository extends JpaRepository<ShippingMaster, Character>{

	@Query("SELECT sm.shippingCharge FROM ShippingMaster sm WHERE sm.medicineTypeCode = :medicineTypeCode AND sm.weightRange = :weightRange")
	int findShippingChargeByMedicineTypeCodeAndWeightRange(@Param("medicineTypeCode") char medicineTypeCode, @Param("weightRange") String weightRange);

}
