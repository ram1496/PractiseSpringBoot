package com.casestudy.pharma.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.casestudy.pharma.entity.BatchInfo;

public interface BatchRepository extends JpaRepository<BatchInfo, String>{
boolean existsByBatchCode(String batchCode);
}
