package br.com.user_service.service;

import br.com.user_service.dto.UserCreateDto;

public interface UserService {
	void createUser(UserCreateDto userCreateDto);
}
