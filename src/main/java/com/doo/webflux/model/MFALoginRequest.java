package com.doo.webflux.model;

import lombok.Value;

@Value
@Deprecated
public class MFALoginRequest {

	String email;
    String password;
    String code;
}
