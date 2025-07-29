package br.com.user_service.dto;

import br.com.user_service.entity.UserRole;

public record ResponseFindByUserDto(Long id, String login, UserRole role) {

}
