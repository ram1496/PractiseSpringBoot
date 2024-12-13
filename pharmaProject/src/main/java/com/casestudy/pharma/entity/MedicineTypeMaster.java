package com.casestudy.pharma.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MedicineTypeMaster {
	
	@Id
	char medicineTypeCode;
	String medicineTypeName;
	
	@OneToMany(mappedBy = "medicineTypeMaster", cascade = CascadeType.ALL)
	List<BatchInfo> batchInfo= new ArrayList<>();
}
