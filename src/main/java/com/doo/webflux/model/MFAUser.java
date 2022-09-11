package com.doo.webflux.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Value;

@Value
@Document(collection = "mfa_users")
@Deprecated
public class MFAUser {

	@Id String userId;
    String email;
    String hash;
    String salt;
    String secretKey;
}
