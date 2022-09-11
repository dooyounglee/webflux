package com.doo.webflux.model;

import lombok.Value;

@Value
@Deprecated
public class MFASignupResponse {

	String userId;
    String secretKey;
}
