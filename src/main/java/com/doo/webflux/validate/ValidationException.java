package com.doo.webflux.validate;

import java.util.List;

import am.ik.yavi.core.ConstraintViolation;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class ValidationException extends RuntimeException {

	@Getter
    final List<ConstraintViolation> errors;
}
