package com.casestudy.pharma.service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.casestudy.pharma.dto.BatchDTO;
import com.casestudy.pharma.exception.PharmaBusinessException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CSVService {

    public List<BatchDTO> parseCSVFile(MultipartFile file) {
        List<BatchDTO> requests = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .parse(reader);

            for (CSVRecord record : records) {
            	log.info("Processing csv record "+record);
                BatchDTO batchDTO = new BatchDTO();
//                batchDTO.setBatchCode(record.get("batchCode"));
                batchDTO.setBatchCode(record.get(0));
        		batchDTO.setMedicineCode(record.get("medicineCode"));
        		batchDTO.setMedicineTypeCode(record.get("medicineTypeCode").charAt(0));
        		batchDTO.setWeight(Integer.parseInt(record.get("weight")));
        		batchDTO.setPrice(Double.parseDouble(record.get("price")));
        		batchDTO.setRefrigeration(Boolean.parseBoolean(record.get("refrigeration")));
        		
                requests.add(batchDTO);
            }
        } catch (IOException e) {
            throw new PharmaBusinessException("520","Failed to parse CSV file");
        }
        return requests;
    }
}