package com.doo.webflux.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.PUT;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;

@Configuration
public class CustomerRouter {

	private final MediaType json = MediaType.APPLICATION_JSON;
	
	@Bean
	RouterFunction<ServerResponse> customerEndpoint(CustomerHandler handler) {
		return RouterFunctions
				.route(POST("/customers").and(accept(json)), handler::postCustomer)
				.andRoute(GET("/customers").and(accept(json)), handler::getAllCustomers)
				.andRoute(GET("/customer/{id}").and(accept(json)), handler::getCustomerById)
				.andRoute(DELETE("/customer/{id}").and(accept(json)), handler::deleteCustomer)
				.andRoute(PUT("/customer/{id}").and(accept(json)), handler::putCustomer);
				
	}
}
