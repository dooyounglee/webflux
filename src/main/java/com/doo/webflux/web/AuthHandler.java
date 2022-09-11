package com.doo.webflux.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.doo.webflux.error.LoginDeniedException;
import com.doo.webflux.model.LoginRequest;
import com.doo.webflux.model.LoginResponse;
import com.doo.webflux.model.SignupRequest;
import com.doo.webflux.model.SignupResponse;
import com.doo.webflux.service.AuthService;

import reactor.core.publisher.Mono;

@Component
public class AuthHandler {

	final static Logger logger = LoggerFactory.getLogger(AuthHandler.class);
	
	private MediaType json = MediaType.APPLICATION_JSON;
	private AuthService authService;
	
	public AuthHandler (AuthService authService) {
		this.authService = authService;
	}
	
	public Mono<ServerResponse> signup(ServerRequest request) {
		logger.debug("com.doo.webflux.web.AuthHandler.signup");
		
		Mono<SignupRequest> body = request.bodyToMono(SignupRequest.class);
		logger.debug("com.doo.webflux.web.AuthHandler.signup.body : {}", body);
		Mono<SignupResponse> result = body.flatMap(authService::signup);
		logger.debug("com.doo.webflux.web.AuthHandler.signup.result : {}", result);
		return result.flatMap(data -> ServerResponse.ok().contentType(json).bodyValue(data))
				.onErrorResume(error -> ServerResponse.badRequest().build());
	}
	
	public Mono<ServerResponse> login(ServerRequest request) {
		logger.debug("com.doo.webflux.web.AuthHandler.login");
		logger.debug("com.doo.webflux.web.AuthHandler.login.request : {}", request);
		
		Mono<LoginRequest> body = request.bodyToMono(LoginRequest.class);
		logger.debug("com.doo.webflux.web.AuthHandler.login.body : {}", body);
		body.map(e -> {
			logger.debug("com.doo.webflux.web.AuthHandler.login.e : {}", e);
			return e;
		});
		Mono<LoginResponse> result = body.flatMap(authService::login);
		logger.debug("com.doo.webflux.web.AuthHandler.login.result : {}", result);
		return result.flatMap(data -> ServerResponse.ok().contentType(json).bodyValue(data))
				.switchIfEmpty(ServerResponse.notFound().build())
				.onErrorResume(error -> {
					logger.debug("com.doo.webflux.web.AuthHandler.login : {}", "onErrorResume error");
					logger.debug("com.doo.webflux.web.AuthHandler.login error instanceof LoginDeniedException : {}", error instanceof LoginDeniedException);
					if (error instanceof LoginDeniedException) {
						logger.debug("com.doo.webflux.web.AuthHandler.login : {}", "onErrorResume error if");
						return ServerResponse.badRequest().build();
					}
					return ServerResponse.status(500).build();
				});
	}
	
	public Mono<ServerResponse> test(ServerRequest request) {
		logger.debug("com.doo.webflux.web.AuthHandler.test.request : {}", request);
		
		return ServerResponse.ok().bodyValue("fdsafdsa");
	}
}
