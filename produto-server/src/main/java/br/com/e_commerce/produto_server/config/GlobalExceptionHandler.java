package br.com.e_commerce.produto_server.config;


import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.e_commerce.produto_server.dto.response.ErroCampoDto;
import br.com.e_commerce.produto_server.dto.response.ErroRespostaDto;
import br.com.e_commerce.produto_server.exception.CodigoJaExisteException;
import br.com.e_commerce.produto_server.exception.CodigoNaoExisteException;
import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<ErroRespostaDto> handlerGeneric(RuntimeException ex, HttpServletRequest request) {
		ErroRespostaDto erro = new ErroRespostaDto(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(erro);
	}

	@ExceptionHandler(CodigoJaExisteException.class)
	public ResponseEntity<ErroRespostaDto> handlerCodigoJaExiste(CodigoJaExisteException ex,
			HttpServletRequest request) {
		ErroRespostaDto erro = new ErroRespostaDto(ex.getMessage(), HttpStatus.CONFLICT.value(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.CONFLICT).body(erro);
	}

	@ExceptionHandler(CodigoNaoExisteException.class)
	public ResponseEntity<ErroRespostaDto> handlerCodigoNaoExiste(CodigoNaoExisteException ex,
			HttpServletRequest request) {
		ErroRespostaDto erro = new ErroRespostaDto(ex.getMessage(), HttpStatus.NOT_FOUND.value(),
				request.getRequestURI());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(erro);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErroRespostaDto> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpServletRequest request){
		List<ErroCampoDto> fieldErrors = ex.getBindingResult().getFieldErrors().stream()
				.map(error -> new ErroCampoDto(error.getField(), error.getDefaultMessage()))
				.toList();
		
		ErroRespostaDto error = new ErroRespostaDto(
				"Erro na validação",
				HttpStatus.BAD_REQUEST.value(), 
				request.getRequestURI(),
				fieldErrors);
		return ResponseEntity.badRequest().body(error);
	}

	
}
