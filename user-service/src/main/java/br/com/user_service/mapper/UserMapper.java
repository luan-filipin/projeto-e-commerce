package br.com.user_service.mapper;
import org.mapstruct.Mapper;

import br.com.user_service.dto.UserCreateDto;
import br.com.user_service.entity.User;


@Mapper(componentModel = "spring")
public interface UserMapper {

	UserCreateDto toDto(User user);
	
	User toEntity(UserCreateDto userCreateDto);
}
