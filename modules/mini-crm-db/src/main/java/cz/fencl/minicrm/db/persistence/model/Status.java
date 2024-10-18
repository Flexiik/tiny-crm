package cz.fencl.minicrm.db.persistence.model;

import lombok.Getter;

@Getter
public enum Status {
	
	INITIAL, FIRST_CONTACT, INITIAL_PRICE_OFFER, REALIZATION_PLACE_ANALYSIS, FINAL_PRICE_OFFER, CONTRACT_SIGNOFF
}
