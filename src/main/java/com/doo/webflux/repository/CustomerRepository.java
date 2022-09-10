package com.doo.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Component;

import com.doo.webflux.model.Customer;

@Component
public interface CustomerRepository extends ReactiveMongoRepository<Customer, String> {

}
