package com.doo.webflux.service;

import org.springframework.stereotype.Component;

import com.doo.webflux.error.AlreadyExistsException;
import com.doo.webflux.error.LoginDeniedException;
import com.doo.webflux.model.LoginRequest;
import com.doo.webflux.model.LoginResponse;
import com.doo.webflux.model.MFALoginRequest;
import com.doo.webflux.model.MFASignupResponse;
import com.doo.webflux.model.MFAUser;
import com.doo.webflux.model.SignupRequest;
import com.doo.webflux.model.SignupResponse;
import com.doo.webflux.model.User;
import com.doo.webflux.repository.UserRepository;
import com.doo.webflux.security.TokenManager;
import com.doo.webflux.security.TotpManager;
import com.doo.webflux.web.AuthHandler;

import lombok.Value;
import reactor.core.publisher.Mono;

import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
@Value
public class AuthServiceImpl implements AuthService {
	
	final static Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

	TokenManager tokenManager;
	TotpManager totpManager;
	UserRepository repository;
	
	@Override
	public Mono<SignupResponse> signup(SignupRequest request) {
		
		String email = request.getEmail().trim().toLowerCase();
		String password = request.getPassword();
		String salt = BCrypt.gensalt();
		String hash = BCrypt.hashpw(password, salt);
		String secret = totpManager.generateSecret();
		User user = new User(null, email, hash, salt);
		
		Mono<SignupResponse> response = repository.findByEmail(email)
				.defaultIfEmpty(user)
				.flatMap(result -> {
					if (result.getUserId() == null) {
						return repository.save(result)
								.flatMap(result2 -> {
									String userId = result2.getUserId();
									SignupResponse signupResponse = new SignupResponse(userId);
									return Mono.just(signupResponse);
								});
					} else {
						return Mono.error(new AlreadyExistsException());
					}
				});
		
		return response;
	}

	@Override
	public Mono<LoginResponse> login(LoginRequest request) {
		logger.debug("com.doo.webflux.service.AuthServiceImpl.login.request : {}", request);
		
		String email = request.getEmail().trim().toLowerCase();
		String password = request.getPassword();
		String code = request.getCode();
		Mono<LoginResponse> response = repository.findByEmail(email)
				.defaultIfEmpty(new User())
				.flatMap(user -> {
					logger.debug("com.doo.webflux.service.AuthServiceImpl.login.user : {}", user);
					if (user.getUserId() == null) {
						return Mono.empty();
					} else {
						String salt = user.getSalt();
						// String secret = user.getSecretKey();
						boolean passwordMatch = BCrypt.hashpw(password, salt).equalsIgnoreCase(user.getHash());
						if (passwordMatch) {
							// password matched
//                          boolean codeMatched = totpManager.validateCode(code, secret);
//                          if (codeMatched) {
//                              String token = tokenManager.issueToken(user.getUserId());
//                              LoginResponse loginResponse = new LoginResponse();
//                              loginResponse.setToken(token);
//                              loginResponse.setUserId(user.getUserId());
//                              return Mono.just(loginResponse);
//                          } else {
//                              return Mono.error(new LoginDeniedException());
//                          }
							String token = tokenManager.issueToken(user.getUserId());
							LoginResponse loginResponse = new LoginResponse(user.getUserId(), token);
							return Mono.just(loginResponse);
						} else {
							return Mono.error(new LoginDeniedException());
						}
					}
				});
		return response;
	}
	
	@Override
	public Mono<String> parseToken(String token) {
		return tokenManager.parse(token);
	}

	@Override
	public Mono<MFASignupResponse> signupMFA(SignupRequest request) {
		// generating a new user entity params
        String email = request.getEmail().trim().toLowerCase();
        String password = request.getPassword();
        String salt = BCrypt.gensalt();
        String hash = BCrypt.hashpw(password, salt);
        String secret = totpManager.generateSecret();

        MFAUser user = new MFAUser(null, email, hash, salt, secret);

        // preparing a Mono
        Mono<MFASignupResponse> response = repository.findByEmail(email)
                .defaultIfEmpty(new User()) // step 2
                .flatMap(result -> {
                    // assert, that user does not exist
                    if (result.getUserId() == null) {
                        return repository.save(result).flatMap(result2 -> {
                            // prepare token
                            String userId = result2.getUserId();
                            MFASignupResponse signupResponse = new MFASignupResponse(userId, secret);
                            return Mono.just(signupResponse);
                        });
                    } else {
                        // scenario - user already exists
                        return Mono.error(new RuntimeException());
                    }
                });
        return response;
	}

	@Override
	public Mono<LoginResponse> loginMFA(MFALoginRequest request) {
		throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	}

}
