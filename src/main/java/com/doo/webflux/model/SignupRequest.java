package com.doo.webflux.model;

import lombok.Data;

@Data
public class SignupRequest {

	private String email;
    private String password;
}
