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
public class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

	@ManyToOne
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private Customer customer;

	@Column(nullable = false)
	private String number;

	@Column
	private String addressStreet;
	@Column
	private String addressNumber;
	@Column
	private String addressPin;
	@Column
	private String addressCity;

	@Column
	private String parcelNumber;
	@Column // Velikost parcely v m2
	private Float parcelAreaSize;
	@Column // List vlastnictvi (LV)
	private String parcelDeed;
	@Column // Katastralni uzem
	private String cadastralLands;
	@Column // Okres
	private String district;

	@Column
	private Double panelsPower;
	@Column
	private Double batteryPower;
	@Column
	private Long startDays;
	@Column
	private Long price;
	@Column
	private Float phase1Payment;
	@Column
	private Float phase2Payment;
	@Column
	private Float phase3Payment;

	@Column
    private String status;

	@Column
	private String sod;
	@Column
	private String ssv;
	@Column
	private String pm;
}
