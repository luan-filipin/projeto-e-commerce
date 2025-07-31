package br.com.e_commerce.produto_server.exception;

public class QuantidadeInsuficienteException extends RuntimeException{
	
	
	private static final String QUANTIDADE_INSUFICIENTE = "O codigo do produto não existe!";
	
	public QuantidadeInsuficienteException() {
		super(QUANTIDADE_INSUFICIENTE);
	}

}
