package br.com.user_service.exception;

public class LoginAlreadyExistsException extends RuntimeException{

	private static final String LOGIN_ALREADY_EXISTS = "O Login ja existe";
	
    public LoginAlreadyExistsException() {
        super(LOGIN_ALREADY_EXISTS);
    }
}
