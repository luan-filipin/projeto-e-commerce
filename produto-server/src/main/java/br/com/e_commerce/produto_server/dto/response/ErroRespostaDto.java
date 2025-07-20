package br.com.e_commerce.produto_server.dto.response;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class ErroRespostaDto {

	private String message;
	private int status;
	private String path;
	private Instant timestamp;
	private List<ErroCampoDto> errors;
	
	public ErroRespostaDto(String message, int status, String path) {
		this.message = message;
		this.status = status;
		this.path = path;
		this.timestamp = Instant.now();
	}
	
	public ErroRespostaDto(String message, int status, String path, List<ErroCampoDto> errors) {
		this(message, status, path);
		this.errors = errors;
	}
}
