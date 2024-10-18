package cz.fencl.minicrm.application.model;

import lombok.Data;

@Data
public class CustomerDto {

	private long id;
	private String firstName;
	private String lastName;
	private String addressStreet;
	private Long addressPin;
	private String addressCity;
	private String birthNumber;
	private String phone;
	private String email;
	private String bank;
	private String account;
}
