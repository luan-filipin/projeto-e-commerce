package br.com.user_service.service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import br.com.user_service.entity.User;


@Service
public class TokenService {

	private String secretKey = "secret";
	
	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretKey);
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(user.getLogin())
					.withExpiresAt(genExpirationDate())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException exception) {
			throw new RuntimeException("Error while generation token", exception);
		}
	}
	
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

	private Instant genExpirationDate() {
		// TODO Auto-generated method stub
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
