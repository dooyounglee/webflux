package com.doo.webflux.service;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.doo.webflux.model.Customer;
import com.doo.webflux.repository.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerService customerService;
	
	@Test
	void createCustomerTest() {
		
		final Customer customer = new Customer("customerId", "Acme", "jana.fdA", "CZ!321", null, null);
		
		Mockito.when(customerRepository.save(customer)).thenReturn(Mono.just(customer));
		
		StepVerifier.create(customerService.createCustomer(customer))
			.assertNext(result -> Assertions.assertThat(result).isEqualTo(customer))
			.verifyComplete();
	}
	
	@Test
	void findAllCustomersTest() {
		List<Customer> customers = List.of(
				new Customer("customerId1", "Acme", "java", "ZA", null, null),
				new Customer("customerId2", "Acme", "java", "ZA", null, null),
				new Customer("customerId3", "Acme", "java", "ZA", null, null),
				new Customer("customerId4", "Acme", "java", "ZA", null, null),
				new Customer("customerId5", "Acme", "java", "ZA", null, null),
				new Customer("customerId6", "Acme", "java", "ZA", null, null)
				);
		
		Mockito.when(customerRepository.findAll()).thenReturn(Flux.fromIterable(customers));
		
		StepVerifier.create(customerService.findAllCustomers())
			.expectNextCount(6)
			.verifyComplete();
	}
	
	@Test
	void findCustomerByIdFoundTest() {
		
		final Customer customer = new Customer("customerId", "Acme", "jana.fdA", "CZ!321", null, null);
		final String customerId = customer.getCustomerId();
		
		Mockito.when(customerRepository.findById(customerId)).thenReturn(Mono.just(customer));
		
		StepVerifier.create(customerService.findCustomerById(customerId))
		.assertNext(result -> Assertions.assertThat(result).isNotNull())
		.verifyComplete();
	}
	
	@Test
	void findCustomerByIdNotFoundTest() {
		
		final String customerId = "1";
		
		Mockito.when(customerRepository.findById(customerId)).thenReturn(Mono.empty());
		
		StepVerifier.create(customerService.findCustomerById(customerId))
		.verifyComplete();
	}
}
