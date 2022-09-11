package com.doo.webflux.web;

import java.net.URI;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.doo.webflux.model.LoginRequest;
import com.doo.webflux.model.LoginResponse;
import com.doo.webflux.service.AuthService;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {AuthRouter.class, AuthHandler.class})
@WebFluxTest
public class AuthHandlerTest {

	final static Logger logger = LoggerFactory.getLogger(CustomerRouterTest.class);
	
	@Autowired private ApplicationContext context;
	@MockBean AuthService authService;
	private WebTestClient client;
	
	@BeforeEach
	void setup() {
		client = WebTestClient.bindToApplicationContext(context).build();
	}
	
	@Test
	void loginNotFoundTest() {
		
		LoginRequest loginRequest = new LoginRequest("aaa@bbb.com", "aaa", "xxx");
		
		Mockito.when(authService.login(loginRequest)).thenReturn(Mono.empty());
		
		client.post()
			.uri(URI.create("/auth/login"))
			.accept(MediaType.APPLICATION_JSON)
			.body(Mono.just(loginRequest), LoginRequest.class)
			.exchange()
			.expectStatus().isNotFound()
			//.expectBody(Customer.class)
			//.value(result -> Assertions.assertThat(result).isEqualTo(customer));
			;
	}
	
	@Test
	void loginSuccessTest() {
		
		LoginRequest loginRequest = new LoginRequest("aaa@bbb.com", "aaa", "xxx");
		LoginResponse loginResponse = new LoginResponse("userId", "token");
		
		Mockito.when(authService.login(loginRequest)).thenReturn(Mono.just(loginResponse));
		
		client.post()
			.uri(URI.create("/auth/login"))
			.accept(MediaType.APPLICATION_JSON)
			// .body(Mono.just(loginRequest), LoginRequest.class)
			.bodyValue(loginRequest)
			.exchange()
			.expectStatus().isOk()
			.expectBody(LoginResponse.class)
			.value(result -> Assertions.assertThat(result).isEqualTo(loginResponse));
			;
	}
}
