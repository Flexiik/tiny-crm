package cz.fencl.minicrm.db.persistence.model;

import java.util.function.BiConsumer;
import java.util.function.Function;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ContractAttributes {

	ADDRESS_STREET(c -> c.getAddressStreet(), (c, v) -> c.setAddressStreet(v.toString()), Status.FIRST_CONTACT),
	ADDRESS_NUMBER(c -> c.getAddressNumber(), (c, v) -> c.setAddressNumber(v.toString()), Status.FIRST_CONTACT), 
	ADDRESS_PIN(c -> c.getAddressPin(), (c, v) -> c.setAddressPin(v.toString()), Status.FIRST_CONTACT), 
	ADDRESS_CITY(c -> c.getAddressCity(), (c, v) -> c.setAddressCity(v.toString()), Status.FIRST_CONTACT),
	PARCEL_NUMBER(c -> c.getParcelNumber(), (c, v) -> c.setParcelNumber(v.toString()), Status.REALIZATION_PLACE_ANALYSIS), 
	PARCEL_AREA_SIZE(c -> c.getParcelAreaSize(), (c, v) ->  c.setParcelAreaSize((Float)v), Status.REALIZATION_PLACE_ANALYSIS),
	PARCEL_DEED(c -> c.getParcelDeed(), (c, v) -> c.setParcelDeed(v.toString()), Status.REALIZATION_PLACE_ANALYSIS),
	CADASTRAL_LANDS(c -> c.getCadastralLands(), (c, v) -> c.setCadastralLands(v.toString()), Status.REALIZATION_PLACE_ANALYSIS),
	DISTRICT(c -> c.getDistrict(), (c, v) -> c.setDistrict(v.toString()), Status.FIRST_CONTACT),
	PANELS_POWER(c -> c.getPanelsPower(), (c, v) -> c.setPanelsPower((Double) v), Status.INITIAL_PRICE_OFFER),
	BATTERY_POWER(c -> c.getBatteryPower(), (c, v) -> c.setBatteryPower((Double) v), Status.INITIAL_PRICE_OFFER),
	START_DAYS(c -> c.getStartDays(), (c, v) -> c.setStartDays((Long) v), Status.CONTRACT_SIGNOFF),
	PRICE(c -> c.getPrice(), (c, v) -> c.setPrice((Long) v), Status.INITIAL_PRICE_OFFER),
	PHASE1_PAYMENT(c -> c.getPhase1Payment(), (c, v) -> c.setPhase1Payment((Float) v), Status.INITIAL_PRICE_OFFER),
	PHASE2_PAYMENT(c -> c.getPhase2Payment(), (c, v) -> c.setPhase2Payment((Float) v), Status.INITIAL_PRICE_OFFER),
	PHASE3_PAYMENT(c -> c.getPhase3Payment(), (c, v) -> c.setPhase3Payment((Float) v), Status.INITIAL_PRICE_OFFER),
	SOD(c -> c.getSod(), (c, v) -> c.setSod(v.toString()), Status.CONTRACT_SIGNOFF),
	SSV(c -> c.getSsv(), (c, v) -> c.setSsv(v.toString()), Status.CONTRACT_SIGNOFF),
	PM(c -> c.getPm(), (c, v) -> c.setPm(v.toString()), Status.CONTRACT_SIGNOFF);
	
	private final Function<Contract, ?> getter;
	private final BiConsumer<Contract, ?> setter;
	private final Status mandatoryState;
}
