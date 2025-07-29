package br.com.user_service.exception;

public class LoginNaoExisteException extends RuntimeException{
	
	private static final String LOGIN_NAO_EXISTE = "O login nao existe";
	
	public LoginNaoExisteException() {
		super(LOGIN_NAO_EXISTE);
	}

}
