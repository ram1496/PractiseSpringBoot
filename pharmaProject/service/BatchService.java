package com.casestudy.pharma.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.casestudy.pharma.dto.BatchDTO;
import com.casestudy.pharma.entity.BatchInfo;
import com.casestudy.pharma.exception.PharmaBusinessException;
import com.casestudy.pharma.mapper.BatchMapper;
import com.casestudy.pharma.repository.BatchRepository;
import com.casestudy.pharma.repository.MedicineRepository;
import com.casestudy.pharma.repository.ShippingMasterRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BatchService {

	@Autowired
	private BatchRepository batchRepository;

	@Autowired
	private MedicineRepository medicineRepository;

	@Autowired
	private ShippingMasterRepository shippingRepository;

	@Autowired
	private BatchMapper batchMapper;

	public void addBatch(BatchDTO batchDTO) {
		log.debug("Validate for duplicate batch code: " + batchDTO.getBatchCode());
		if (batchRepository.existsByBatchCode(batchDTO.getBatchCode())) {
			throw new PharmaBusinessException("511", "Batch code already exists");
		}
		log.debug("Validate where the medicine code is present in DB for : " + batchDTO.getMedicineCode());
		if (!medicineRepository.existsByMedicineCode(batchDTO.getMedicineCode())) {
			throw new PharmaBusinessException("510", "Medicine code does not exist");
		}
		
		log.debug("Validate that weight should be greater than 100 : " + batchDTO.getWeight());
		if (batchDTO.getWeight() < 100) {
			throw new PharmaBusinessException("512", "Batch weight should be greater than 100");
		}
		
		log.debug("Validate where the batch code format should be correct for : " + batchDTO.getBatchCode());
		if (!batchDTO.getBatchCode().matches("^BTC-\\d{4}$")) {
			throw new PharmaBusinessException("513", "Batch format wrong.It should be in the format “BTC-1234”");
		}

		BatchInfo batchInfo = batchMapper.toEntity(batchDTO);
		double shippingCharge = getShippingCharge(batchDTO);
		String careLevel = determineCareLevel(batchDTO.getMedicineTypeCode());
		batchInfo.setShippingCharge(shippingCharge);
		batchInfo.setCareLevel(careLevel);
		batchRepository.save(batchInfo);
	}

	private double getShippingCharge(BatchDTO batchDTO) {
		log.info("Calculating shipping charge for "+batchDTO);
		String weightRange;
		weightRange = getWeightRange(batchDTO);

		double baseCharge = shippingRepository
				.findShippingChargeByMedicineTypeCodeAndWeightRange(batchDTO.getMedicineTypeCode(), weightRange);
		if (batchDTO.isRefrigeration()) {
			
			baseCharge += baseCharge * 0.05;
		}
		log.info("Returning total charge:"+baseCharge);
		return baseCharge;
	}

	public String getWeightRange(BatchDTO batchDTO) {
		String weightRange;
		if (batchDTO.getWeight() <= 500) {
			weightRange = "W1";
		} else if (batchDTO.getWeight() <= 1000) {
			weightRange = "W2";

		} else {
			weightRange = "W3";
		}
		return weightRange;
	}

	private String determineCareLevel(char c) {
		log.debug("Determining careLevel");
		String careLevel = "";
		switch (c) {
		case 'C':
			careLevel = "Normal";
			break;
		case 'T':
			careLevel = "High";
			break;
		case 'S':
			careLevel = "Extremely High";

		}
		return careLevel;
	}

	public BatchInfo getBatchByCode(String batchCode) {
		
		return batchRepository.findById(batchCode).orElse(null);
	}

	public Page<BatchInfo> getAllBatches(int page,int size) {
		 Pageable pageable = PageRequest.of(page, size);
	        return batchRepository.findAll(pageable);
	}

	@Transactional
	public void saveRequests(List<BatchDTO> batchDTOs) {
		for(BatchDTO dto : batchDTOs ) {
			addBatch(dto);
		}
	    
	}

}
