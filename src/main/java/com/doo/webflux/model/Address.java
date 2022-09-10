package com.doo.webflux.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value // private, final, all-arg constructor
public class Address {

	String addressLine;
	String postalCode;
	String city;
	
	@JsonCreator
	public Address(
			@JsonProperty("addressLine") String addressLine,
			@JsonProperty("postalCode") String postalCode,
			@JsonProperty("city") String city) {
		this.addressLine = addressLine;
		this.postalCode = postalCode;
		this.city = city;
	}
}
