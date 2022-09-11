package com.doo.webflux.web;

import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.doo.webflux.model.Customer;
import com.doo.webflux.service.CustomerService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {CustomerRouter.class, CustomerHandler.class})
@WebFluxTest
public class CustomerRouterTest {

	final static Logger logger = LoggerFactory.getLogger(CustomerRouterTest.class);
	
	@Autowired private ApplicationContext context;
	@MockBean CustomerService customerSer;
	private WebTestClient client;
	
	@BeforeEach
	void setup() {
		client = WebTestClient.bindToApplicationContext(context).build();
	}
	
	private Customer createMockCustomer(String id) {
		Customer customer = new Customer(id, "ac", "java", "CZ", null, null);
		return customer;
	}
	
	@Test
	void postCustomerTest() {
		
		final Customer customer = createMockCustomer("customerId");
		
		Mockito.when(customerSer.createCustomer(customer)).thenReturn(Mono.just(customer));
		
		client.post()
			.uri(URI.create("/customers"))
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(customer), Customer.class)
			.exchange()
			.expectStatus().isOk()
			.expectBody(Customer.class)
			.value(result -> Assertions.assertThat(result).isEqualTo(customer));
	}
	
}
