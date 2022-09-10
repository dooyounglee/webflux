package com.doo.webflux.service;

import org.springframework.stereotype.Component;

import com.doo.webflux.model.Customer;
import com.doo.webflux.repository.CustomerRepository;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@Value
public class CustomerService {

	CustomerRepository customerRepository;
	
	public Mono<Customer> createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	public Mono<Customer> findCustomerById(String customerId) {
		return customerRepository.findById(customerId);
	}
	
	public Flux<Customer> findAllCustomers() {
		return customerRepository.findAll();
	}
}
