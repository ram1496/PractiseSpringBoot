package com.casestudy.pharma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BatchInfo {
	
	@Id
	String batchCode;
	
	@ManyToOne
    @JoinColumn(name = "medicine_code")
	MedicineMaster medicineMaster;
	
	int weight;
	double price;
	
	@ManyToOne
    @JoinColumn(name = "medicine_type_code")
	MedicineTypeMaster medicineTypeMaster;
	
	double shippingCharge;
	String careLevel;
}
