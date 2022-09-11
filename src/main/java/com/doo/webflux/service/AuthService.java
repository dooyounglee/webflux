package com.doo.webflux.service;

import com.doo.webflux.model.LoginRequest;
import com.doo.webflux.model.LoginResponse;
import com.doo.webflux.model.MFALoginRequest;
import com.doo.webflux.model.MFASignupResponse;
import com.doo.webflux.model.SignupRequest;
import com.doo.webflux.model.SignupResponse;

import reactor.core.publisher.Mono;

public interface AuthService {

	Mono<SignupResponse> signup (SignupRequest request);

    Mono<LoginResponse> login (LoginRequest request);

    @Deprecated
    Mono<MFASignupResponse> signupMFA (SignupRequest request);

    @Deprecated
    Mono<LoginResponse> loginMFA (MFALoginRequest request);
    
    Mono<String> parseToken (String token);
}
