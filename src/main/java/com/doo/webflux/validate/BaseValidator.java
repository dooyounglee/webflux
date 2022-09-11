package com.doo.webflux.validate;

import reactor.core.publisher.Mono;

public interface BaseValidator <T> {

	Mono<T> validate (T data);
}
