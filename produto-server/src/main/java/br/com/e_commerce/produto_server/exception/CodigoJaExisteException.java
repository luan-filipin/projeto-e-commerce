package br.com.e_commerce.produto_server.exception;

public class CodigoJaExisteException extends RuntimeException{

	private static final String CODIGO_JA_EXISTE = "Este codigo ja esta cadastrado!";
	
	public CodigoJaExisteException() {
		super(CODIGO_JA_EXISTE);
	}
}
