//package com.doo.webflux.web;
//
//import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
//import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.MediaType;
//import org.springframework.web.reactive.function.server.RouterFunction;
//import org.springframework.web.reactive.function.server.RouterFunctions;
//import org.springframework.web.reactive.function.server.ServerResponse;
//
//@Configuration
//public class SecureRouter {
//
//	@Bean
//	public RouterFunction<ServerResponse> protectedEndpoint(protectedHandler handler) {
//		return RouterFunctions
//				.route(GET("/secured/hello").and(accept(MediaType.APPLICATION_JSON)), handler::sayHello);
//	}
//}
