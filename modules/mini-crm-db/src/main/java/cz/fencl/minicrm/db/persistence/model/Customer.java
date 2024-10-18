package cz.fencl.minicrm.db.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column
	private String firstName;

	@Column
	private String lastName;

	@Column
	private String addressStreet;

	@Column
	private String addressNumber;

	@Column
	private String addressPin;

	@Column
	private String addressCity;

	@Column
	private String birthNumber; // Rodne cislo

	@Column
	private String phone;

	@Column
	private String email;

	@Column
	private String bank;

	@Column
	private String account;
}
