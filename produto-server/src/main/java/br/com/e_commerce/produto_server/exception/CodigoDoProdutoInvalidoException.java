package br.com.e_commerce.produto_server.exception;

public class CodigoDoProdutoInvalidoException extends RuntimeException{

	private static final String CODIGO_DO_PRODUTO_INVALIDO = "O código no corpo da requisição não pode ser diferente do código da URL.";
	
	public CodigoDoProdutoInvalidoException() {
		super(CODIGO_DO_PRODUTO_INVALIDO);
	}

}
