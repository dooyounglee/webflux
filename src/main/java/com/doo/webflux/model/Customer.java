package com.doo.webflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Value;

@Value // private, final, all-arg constructor
@Document(collection = "customers")
public class Customer {

	@Id String customerId;
	String companyName;
	String companyEmail;
	String taxId;
	Address billingAddress;
	Address shippingAddress;
	
	@JsonCreator
	public Customer(
			@JsonProperty("customerId") String customerId,
			@JsonProperty("companyName") String companyName,
			@JsonProperty("companyEmail") String companyEmail,
			@JsonProperty("taxId") String taxId,
			@JsonProperty("billingAddress") Address billingAddress,
			@JsonProperty("shippingAddress") Address shippingAddress) {
		this.customerId = customerId;
		this.companyName = companyName;
		this.companyEmail = companyEmail;
		this.taxId = taxId;
		this.billingAddress = billingAddress;
		this.shippingAddress = shippingAddress;
	}
}
