package com.doo.webflux.service;

import com.doo.webflux.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

	Mono<Customer> createCustomer(Customer customer);
	Mono<Customer> findCustomerById(String customerId);
	Flux<Customer> findAllCustomers();
	Mono<Customer> updateCustomer(Customer customer);
	Mono<Void> removeCustomer(String id);
}
