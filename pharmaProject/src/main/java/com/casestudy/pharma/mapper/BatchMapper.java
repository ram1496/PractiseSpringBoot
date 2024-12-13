package com.casestudy.pharma.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.casestudy.pharma.dto.BatchDTO;
import com.casestudy.pharma.entity.BatchInfo;
import com.casestudy.pharma.repository.MedicineRepository;
import com.casestudy.pharma.repository.MedicineTypeRepository;
import com.casestudy.pharma.utility.BatchIDGenerator;

@Component
public class BatchMapper {

	
	@Autowired
	private  MedicineTypeRepository medicineTypeRepository;
	
	@Autowired
	private  MedicineRepository medicineRepository;
	
	public  BatchDTO toDTO(BatchInfo batchInfo) {
		BatchDTO batchDTO = new BatchDTO();
		
		batchDTO.setBatchCode(batchInfo.getBatchCode());
		batchDTO.setMedicineCode(batchInfo.getMedicineMaster().getMedicineCode());
		batchDTO.setMedicineTypeCode(batchInfo.getMedicineTypeMaster().getMedicineTypeCode());;
		batchDTO.setWeight(batchInfo.getWeight());
		batchDTO.setPrice(batchInfo.getPrice());
		
		return batchDTO;
	}
	
	public  BatchInfo toEntity(BatchDTO batchDTO) {
		BatchInfo batchInfo = new BatchInfo();
		String batchCode = batchDTO.getBatchCode();
		if(batchCode==null|| batchCode.isBlank()) {
			batchInfo.setBatchCode(BatchIDGenerator.generateId());
		}else {
		batchInfo.setBatchCode(batchCode);
		}
		batchInfo.setMedicineMaster(medicineRepository.findByMedicineCode(batchDTO.getMedicineCode()));
		batchInfo.setWeight(batchDTO.getWeight());
		batchInfo.setPrice(batchDTO.getPrice());
		batchInfo.setMedicineTypeMaster(medicineTypeRepository.findByMedicineTypeCode(batchDTO.getMedicineTypeCode()));
		return batchInfo;
	}
}
