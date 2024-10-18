package cz.fencl.minicrm.db.persistence.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

	@ManyToOne
	@JoinColumn(name = "company_id")
	private Company company;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
    private String firstName;

	@Column(nullable = false)
	private String lastName;

	@Column(nullable = false)
	private String role;

	@Column(nullable = false)
	private long phone;

	@Column(nullable = false)
	private String address;

	@Column(nullable = false)
	private String birthDate;

}
