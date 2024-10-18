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
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(nullable = false)
	private String name;
	@Column(nullable = false)
	private String ic;
	@Column(nullable = false)
	private String dic;
	@Column(nullable = false)
	private String address;
	@Column(nullable = false)
	private String registry; // Obchodni rejstrik
	@Column(nullable = false)
	private String bank;
	@Column(nullable = false)
	private String account; // cislo uctu
	@Column(nullable = false)
	private String email;

}
