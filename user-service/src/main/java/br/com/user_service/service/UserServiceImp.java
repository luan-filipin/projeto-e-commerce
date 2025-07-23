package br.com.user_service.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.user_service.dto.UserCreateDto;
import br.com.user_service.entity.User;
import br.com.user_service.exception.LoginAlreadyExistsException;
import br.com.user_service.mapper.UserMapper;
import br.com.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService{

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public void createUser(UserCreateDto userCreateDto) {
		
		if(userRepository.existsByLogin(userCreateDto.login())) {
			throw new LoginAlreadyExistsException();
		}
		
		User user = userMapper.toEntity(userCreateDto);
		user.setPassword(passwordEncoder.encode(userCreateDto.password()));
		userRepository.save(user);
	}
}
