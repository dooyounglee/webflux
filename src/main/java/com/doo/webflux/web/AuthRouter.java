package com.doo.webflux.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class AuthRouter {

	private final MediaType json = MediaType.APPLICATION_JSON;
	
	@Bean
	public RouterFunction<ServerResponse> authEndpoint (AuthHandler handler) {
		return RouterFunctions
				.route(POST("/auth/signup").and(accept(json)), handler::signup)
				.andRoute(POST("/auth/login").and(accept(json)), handler::login)
				// .andRoute(POST("/auth/mfalogin").and(accept(json)), handler::loginMFA)
				// .andRoute(POST("/auth/mfasignup").and(accept(json)), handler::signupMFA)
				.andRoute(GET("/auth/test").and(accept(json)), handler::test)
				;
	}
}
