package br.com.user_service.dto;

import jakarta.validation.constraints.NotBlank;

public record UserLoginDto(
		@NotBlank(message = "O campo login é obrigatorio") String login,
		@NotBlank(message = "O campo senha é obrigatoria") String password)  {

}
