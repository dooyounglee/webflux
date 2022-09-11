package com.doo.webflux.web;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.doo.webflux.service.AuthService;

import lombok.AllArgsConstructor;
import reactor.core.publisher.Mono;

@AllArgsConstructor
@Component
public class AuthFunction implements HandlerFilterFunction<ServerResponse, ServerResponse> {

private AuthService authService;
	
	@Override
	public Mono<ServerResponse> filter(ServerRequest request,
			HandlerFunction<ServerResponse> handlerFunction) {
		
		List<String> headers =request.headers().header("Authorization");
		if (headers.isEmpty()) return ServerResponse.status(403).build();
		
		String token = headers.get(0);
		
		return authService.parseToken(token)
				.flatMap(res -> ServerResponse.status(403).build());
	}
}
