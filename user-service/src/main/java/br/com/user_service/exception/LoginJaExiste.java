package br.com.user_service.exception;

public class LoginJaExiste extends RuntimeException{

	private static final String LOGIN_ALREADY_EXISTS = "O Login ja existe";
	
    public LoginJaExiste() {
        super(LOGIN_ALREADY_EXISTS);
    }
}
