//package com.doo.webflux.web;
//
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.doo.webflux.model.Customer;
//import com.doo.webflux.service.CustomerService;
//
//import lombok.Value;
//import reactor.core.publisher.Mono;
//
//@Value
//@RestController
//@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE,
//                consumes = MediaType.APPLICATION_JSON_VALUE)
//public class CustomerRestController {
//	
//	CustomerService customerService;
//	
//	@GetMapping("/customer/{id}")
//	Mono<ResponseEntity<Customer>> getCustomerById(@PathVariable("id") String id) {
//		return customerService.findCustomerById(id)
//				.map(customer -> ResponseEntity.ok(customer));
//	}
//}
