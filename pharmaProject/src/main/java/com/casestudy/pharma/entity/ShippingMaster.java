package com.casestudy.pharma.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@Entity
public class ShippingMaster {

@Id
char medicineTypeCode;
String weightRange;
int shippingCharge;

}
