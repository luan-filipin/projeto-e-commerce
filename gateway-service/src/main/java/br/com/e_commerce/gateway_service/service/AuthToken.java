package br.com.e_commerce.gateway_service.service;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class AuthToken {

    private String secretKey = "secret"; // mesma chave que no auth-service

	public String validadeToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			return JWT.require(algorithm)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException exception) {
			return "";
		}
	}
}
