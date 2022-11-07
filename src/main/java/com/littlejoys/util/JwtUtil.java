package com.littlejoys.util;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;

@Component
public class JwtUtil {
	private static final String SECRET_KEY = "Secretkeytolearn";
	private static final int TOKEN_VALIDTY = 3600 * 5;	
	
	private Claims getAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	
}
