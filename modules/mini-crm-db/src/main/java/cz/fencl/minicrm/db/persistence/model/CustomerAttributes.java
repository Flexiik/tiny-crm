package cz.fencl.minicrm.db.persistence.model;

import java.util.function.BiConsumer;
import java.util.function.Function;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CustomerAttributes {
	
	FIRST_NAME(c -> c.getFirstName(), (c, v) -> c.setFirstName(v.toString()), Status.INITIAL),
	LAST_NAME(c -> c.getLastName(), (c, v) -> c.setLastName(v.toString()), Status.INITIAL),
	PHONE(c -> c.getPhone(), (c, v) -> c.setPhone(v.toString()), Status.INITIAL),
	EMAIL(c -> c.getEmail(), (c, v) -> c.setEmail(v.toString()), Status.INITIAL),
	ADDRESS_STREET(c -> c.getAddressStreet(), (c, v) -> c.setAddressStreet(v.toString()), Status.FIRST_CONTACT),
	ADDRESS_NUMBER(c -> c.getAddressNumber(), (c, v) -> c.setAddressNumber(v.toString()), Status.FIRST_CONTACT),
	ADDRESS_PIN(c -> c.getAddressPin(), (c, v) -> c.setAddressPin(v.toString()), Status.FIRST_CONTACT),
	ADDRESS_CITY(c -> c.getAddressCity(), (c, v) -> c.setAddressCity(v.toString()), Status.FIRST_CONTACT),
	BIRTH_NUMBER(c -> c.getBirthNumber(), (c, v) -> c.setBirthNumber(v.toString()), Status.CONTRACT_SIGNOFF),
	BANK(c -> c.getBank(), (c, v) -> c.setBank(v.toString()), Status.CONTRACT_SIGNOFF),
	ACCOUNT(c -> c.getAccount(), (c, v) -> c.setAccount(v.toString()), Status.CONTRACT_SIGNOFF);

	private final Function<Customer, String> getter;
	private final BiConsumer<Customer, String> setter;
	private final Status mandatoryState;

}
