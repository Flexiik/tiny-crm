package cz.fencl.minicrm.application.model;

import lombok.Data;

//Employee but stripped of sensitive data
@Data
public class EmployeeDto {

	private long id;
	private String title;
	private String firstName;
	private String lastName;
	private String role;
	private long phone;
}
