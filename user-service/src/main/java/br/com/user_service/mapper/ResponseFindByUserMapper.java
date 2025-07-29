package br.com.user_service.mapper;

import org.mapstruct.Mapper;

import br.com.user_service.dto.ResponseFindByUserDto;
import br.com.user_service.entity.User;


@Mapper(componentModel = "spring")
public interface ResponseFindByUserMapper {

	ResponseFindByUserDto toDto(User user);
	
	User toEntity(ResponseFindByUserDto responseFindByUserDto);
}

