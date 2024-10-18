package cz.fencl.minicrm.application.model;

import lombok.Data;

//Contract but stripped of sensitive data
@Data
public class ContractDto {
	private long id;
	private EmployeeDto employee;
	private CustomerDto customer;
	private String number;
	private String addressStreet;
	private String addressNumber;
	private String addressPin;
	private String addressCity;
	private String parcelNumber;
	// Velikost parcely v m2
	private Float parcelAreaSize;
	// List vlastnictvi (LV)
	private String parcelDeed;
	// Katastralni uzemi
	private String cadastralLands;
	// Okres
	private String district;

	private Double panelsPower;
	private Double batteryPower;
	private Long startDays;
	private String status;
	private Long price;
	private Float phase1Payment;
	private Float phase2Payment;
	private Float phase3Payment;
}
