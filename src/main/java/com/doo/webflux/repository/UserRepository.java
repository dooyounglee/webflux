package com.doo.webflux.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.doo.webflux.model.User;

import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveMongoRepository<User, String>{

	Mono<User> findByEmail (String email);
}
