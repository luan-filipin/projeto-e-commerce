package br.com.e_commerce.produto_server.exception;

public class CodigoNaoExisteException extends RuntimeException{
	
	private static final String CODIGO_NAO_EXISTE = "O codigo do produto não existe!";
	
	public CodigoNaoExisteException() {
		super(CODIGO_NAO_EXISTE);
	}

}
