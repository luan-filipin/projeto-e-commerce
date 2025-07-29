package br.com.user_service.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import br.com.user_service.dto.ResponseFindByUserDto;
import br.com.user_service.dto.UserCreateDto;

public interface UserService {
	
	void createUser(UserCreateDto userCreateDto);
	
	ResponseFindByUserDto findUserByLogin(String loing);
	
	Page<ResponseFindByUserDto> retornaTodosOsUsuarios(Pageable pageable);
	
}
