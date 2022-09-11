package com.doo.webflux.security;

@Deprecated
public interface TotpManager {

	String generateSecret ();

    boolean validateCode (String code, String secret);
}
