package com.casestudy.pharma.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import jakarta.validation.constraints.*;

@Getter
@Setter
@AllArgsConstructor 
@NoArgsConstructor
public class BatchDTO {

    @NotNull(message = "Batch code cannot be null")
	String batchCode;
    
    @NotNull(message = "Medicine code cannot be null")
    @Size(min = 1, max = 8, message = "Medicine code must be between 1 and 8 characters")
	String medicineCode;
    
    @NotNull(message = "Medicine type code cannot be null")
    char medicineTypeCode;
    
    @NotNull(message = "Price cannot be null")
    double price;
    
    @NotNull(message = "Weight cannot be null")
    @Min(value = 100, message = "Weight must be at least 100")
	int weight;
    
    
    @NotNull(message = "Refrigeration cannot be null")
	boolean Refrigeration;


	@Override
	public String toString() {
		return "BatchDTO [batchCode=" + batchCode + ", medicineCode=" + medicineCode + ", medicineTypeCode="
				+ medicineTypeCode + ", price=" + price + ", weight=" + weight + ", Refrigeration=" + Refrigeration
				+ "]";
	}
	
	
	
}