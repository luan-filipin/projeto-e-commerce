package br.com.user_service.dto;

public record ResponseCreateUserDto (String message){
	public static final String CREATE_WITH_SUCESS = "Usuario criado com sucesso!";
	
    public ResponseCreateUserDto() {
        this(CREATE_WITH_SUCESS);
    }
}
