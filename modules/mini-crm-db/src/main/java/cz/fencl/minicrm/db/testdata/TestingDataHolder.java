package cz.fencl.minicrm.db.testdata;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cz.fencl.minicrm.db.persistence.model.Company;
import cz.fencl.minicrm.db.persistence.model.Contract;
import cz.fencl.minicrm.db.persistence.model.Customer;
import cz.fencl.minicrm.db.persistence.model.Employee;
import cz.fencl.minicrm.db.persistence.model.Item;
import cz.fencl.minicrm.db.persistence.model.ItemType;
import cz.fencl.minicrm.db.persistence.repositories.CompanyRepository;
import cz.fencl.minicrm.db.persistence.repositories.ContractRepository;
import cz.fencl.minicrm.db.persistence.repositories.CustomerRepository;
import cz.fencl.minicrm.db.persistence.repositories.EmployeeRepository;
import cz.fencl.minicrm.db.persistence.repositories.ItemRepository;
import lombok.Getter;

@Component
public class TestingDataHolder {

	private static final List<String> LOREM_IPSUM = Arrays.asList(
			"Lorem ipsum dolor sit amet consectetur adipiscing elit sed do eiusmod tempor incididunt ut labore et dolore magna aliqua"
					.split(" "));

	@Autowired
	private CompanyRepository companyRepo;
	@Autowired
	private EmployeeRepository employeeRepo;
	@Autowired
	private CustomerRepository customerRepo;
	@Autowired
	private ContractRepository contractRepo;
	@Autowired
	private ItemRepository itemRepo;

	@Getter
	private Customer customer;
	@Getter
	private Company company;
	@Getter
	private Employee employee;
	@Getter
	private Contract contract;


	@PostConstruct
	public void initializeTestingData() {
		customer = new Customer();
		customer.setFirstName("František");
		customer.setLastName("Náhodný");
		customer.setAddressStreet("Sezamová");
		customer.setAddressNumber("456");
		customer.setAddressPin("45645");
		customer.setAddressCity("Horní Dolní");
		customer.setPhone("656 456 456");
		customer.setEmail("frantisek.nahodny@nahodna.cz");
		customer.setBank("Tunelari a.s.");
		customer.setAccount("123456789/1234");
		customer.setBirthNumber("123456789/1234");
		customer = customerRepo.save(customer);

		company = new Company();
		company.setAccount("0000000000/0000");
		company.setBank("Test bank");
		company.setAddress("Testovaci adresa 12345");
		company.setEmail("test@tiny-crm.cz");
		company.setIc("000 00 000");
		company.setDic("CZ0000000");
		company.setName("Nahodna company");
		company.setRegistry("registry");
		company = companyRepo.save(company);

		employee = new Employee();
		employee.setCompany(company);
		employee.setTitle("Mgr.");
		employee.setFirstName("Jan");
		employee.setLastName("Svoboda");
		employee.setPhone(700000000);
		employee.setRole("Jednatel");
		employee.setAddress("Testovaci adresa 12345");
		employee.setBirthDate("9.9.1999");
		employee = employeeRepo.save(employee);

		contract = new Contract();
		contract.setAddressStreet("Sezamová");
		contract.setAddressNumber("456");
		contract.setAddressPin("45645");
		contract.setAddressCity("Horní Dolní");
		contract.setParcelNumber("1068/89");
		contract.setParcelAreaSize(1567f);
		contract.setParcelDeed("156");
		contract.setCadastralLands("Liberec");
		contract.setDistrict("Liberec");
		contract.setPanelsPower(9.9);
		contract.setBatteryPower(3.0);
		contract.setCustomer(customer);
		contract.setEmployee(employee);
		contract.setNumber("22-1234");
		contract.setPrice(1234567l);
		contract.setStartDays(15l);
		contract.setStatus("Podpis SOD");
		contract.setPhase1Payment(50f);
		contract.setPhase2Payment(30f);
		contract.setPhase3Payment(20f);
		contract = contractRepo.save(contract);
		generateContracts(100);
		addItems();
	}
	
	private void addItems() {
	  itemRepo.save(new Item(0, ItemType.PANEL, "panel1", 177, "$", "ks", "SOLSOL"));
	  itemRepo.save(new Item(0, ItemType.PANEL, "panel2", 5082, "Kč", "ks", ""));
	  itemRepo.save(new Item(0, ItemType.PANEL, "panel3", 3900, "Kč", "ks", ""));
	  itemRepo.save(new Item(0, ItemType.PANEL, "montaz", 1800, "Kč", "ks", ""));
	  itemRepo.save(new Item(0, ItemType.BATTERY, "baterie1", 72872, "Kč", "ks", "ELKOV"));
	  itemRepo.save(new Item(0, ItemType.BATTERY, "baterie2", 59777, "Kč", "ks", "ELKOV"));
	  itemRepo.save(new Item(0, ItemType.TECHNOLOGY, "tech1", 55000, "Kč", "ks", "ELKOV"));
	  itemRepo.save(new Item(0, ItemType.TECHNOLOGY, "tech2", 2674, "Kč", "ks", "ELKOV"));
	  itemRepo.save(new Item(0, ItemType.TECHNOLOGY, "tech3", 2000, "Kč", "panel", "aaaa"));
	  itemRepo.save(new Item(0, ItemType.TECHNOLOGY, "tech4", 17500, "Kč", "ks", ""));
	  itemRepo.save(new Item(0, ItemType.TECHNOLOGY, "montaz", 20000, "Kč", "ks", ""));
	  itemRepo.save(new Item(0, ItemType.OTHER, "Marže obchodníkovi 5% - CN, nafoceni, smlouva se zakaznikem", 5, "%", "%", ""));
	  itemRepo.save(new Item(0, ItemType.OTHER, "Vedlejší náklady", 10000, "Kč", "-", ""));
	  itemRepo.save(new Item(0, ItemType.OTHER, "Revize", 7000, "Kč", "-", ""));
	  itemRepo.save(new Item(0, ItemType.OTHER, "Dotace, vyřízení dokumentů", 5000, "Kč", "-", ""));
	}

	private void generateContracts(int count) {
		for (int i = 0; i < count; i++) {
			Contract contract = new Contract();
			contract.setAddressStreet(randomString());
			contract.setAddressNumber(randomNumber(1, 5000) + "");
			contract.setAddressPin(randomNumber(10000, 50000) + "");
			contract.setAddressCity(randomString() + " " + randomString());
			contract.setParcelNumber(randomNumber(100, 5000) + "/" + randomNumber(1, 500));
			contract.setParcelAreaSize((float) randomNumber(500, 5000));
			contract.setParcelDeed(randomNumber(1, 1000) + "");
			contract.setCadastralLands(randomString());
			contract.setDistrict(randomString());
			contract.setPanelsPower((double) (randomNumber(1000, 100000) / 1000));
			contract.setBatteryPower((double) (randomNumber(1000, 10000) / 1000));
			contract.setCustomer(customer);
			contract.setEmployee(employee);
			contract.setNumber("22-" + randomNumber(1000, 10000));
			contract.setPrice((long) randomNumber(100000, 1000000));
			contract.setStartDays(15l);
			contract.setStatus(randomString());
			contract.setPhase1Payment(50f);
			contract.setPhase2Payment(30f);
			contract.setPhase3Payment(20f);
			contract = contractRepo.save(contract);
		}
	}

	private String randomString() {
		return LOREM_IPSUM.get((int) (Math.random() * LOREM_IPSUM.size()));
	}

	private int randomNumber(int min, int max) {
		return min + (int) ((max - min) * Math.random());
	}
}
