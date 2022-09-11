package com.doo.webflux.validate;

import com.doo.webflux.model.Customer;

import am.ik.yavi.builder.ValidatorBuilder;
import am.ik.yavi.core.ConstraintViolations;
import am.ik.yavi.core.Validator;
import reactor.core.publisher.Mono;

public class CustomerValidator implements BaseValidator<Customer>{

	private final Validator<Customer> validator;
	
	public CustomerValidator() {
		validator = ValidatorBuilder.of(Customer.class)
				.constraint(Customer::getCompanyEmail, "companyEmail", c -> c.notNull().email())
				.constraint(Customer::getCompanyName, "companyName", c -> c.notNull())
				.constraint(Customer::getTaxId, "taxId", c -> c.pattern(""))
				.build();
	}
	
	@Override
	public Mono<Customer> validate(Customer model) {
		ConstraintViolations violations = validator.validate(model);
		if (violations.isValid()) {
			return Mono.just(model);
		} else {
			return Mono.error(new ValidationException(violations.violations()));
		}
	}

	
}
