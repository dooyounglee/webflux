package com.doo.webflux.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.doo.webflux.model.Customer;
import com.doo.webflux.service.CustomerService;
import com.doo.webflux.validate.CustomerValidator;

import lombok.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Value
@Component
public class CustomerHandler {

	final static Logger logger = LoggerFactory.getLogger(CustomerHandler.class);
	
	CustomerService customerService;
	
	Mono<ServerResponse> postCustomer(ServerRequest request) {
		logger.debug("com.doo.webflux.web.CustomerHandler.postCustomer");
		
		Mono<Customer> body = request.bodyToMono(Customer.class);
		Mono<Customer> result = body.flatMap(b -> customerService.createCustomer(b));
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON).body(result, Customer.class);
	}
	
	Mono<ServerResponse> putCustomer(ServerRequest request) {
		
		CustomerValidator validator = new CustomerValidator();
		
		return request.bodyToMono(Customer.class)
				.flatMap(validator::validate) // validate
				.flatMap(customerService::updateCustomer)
				.flatMap(result -> ServerResponse.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.bodyValue(result))
				.onErrorResume(err -> ServerResponse.badRequest().build()) // validate
				;
	}
	
	Mono<ServerResponse> deleteCustomer(ServerRequest request) {
		
		String id = request.pathVariable("id");
		Mono<Void> result = customerService.removeCustomer(id);
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON).body(result, Void.class);
	}
	
	Mono<ServerResponse> getCustomerById(ServerRequest request) {
		
		String id = request.pathVariable("id");
		return customerService.findCustomerById(id)
				.flatMap(result -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(result))
				.switchIfEmpty(ServerResponse.notFound().build());
	}
	
	Mono<ServerResponse> getAllCustomers(ServerRequest request) {
		logger.debug("com.doo.webflux.web.CustomerHandler.getAllCustomers");
		
		Flux<Customer> result = customerService.findAllCustomers();
		
		return ServerResponse.ok()
				.contentType(MediaType.APPLICATION_JSON).body(result, Customer.class);
	}
	
}
