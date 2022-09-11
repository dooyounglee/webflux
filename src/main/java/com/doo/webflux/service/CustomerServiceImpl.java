package com.doo.webflux.service;

import org.springframework.stereotype.Component;

import com.doo.webflux.model.Customer;
import com.doo.webflux.repository.CustomerRepository;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component("CustomerSer")
@Value
public class CustomerServiceImpl implements CustomerService {

	private CustomerRepository customerRepository;
	
	@Override
	public Mono<Customer> createCustomer(Customer customer) {
		return customerRepository.save(customer);
	}
	
	@Override
	public Mono<Customer> findCustomerById(String customerId) {
		return customerRepository.findById(customerId);
	}
	
	@Override
	public Flux<Customer> findAllCustomers() {
		return customerRepository.findAll();
	}
	
	@Override
	public Mono<Customer> updateCustomer(Customer customer) {
		return customerRepository.save(customer);
	}

	@Override
	public Mono<Void> removeCustomer(String id) {
		return customerRepository.deleteById(id);
	}
}
