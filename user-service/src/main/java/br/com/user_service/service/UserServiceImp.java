package br.com.user_service.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.com.user_service.dto.ResponseFindByUserDto;
import br.com.user_service.dto.UserCreateDto;
import br.com.user_service.entity.User;
import br.com.user_service.exception.LoginJaExiste;
import br.com.user_service.exception.LoginNaoExisteException;
import br.com.user_service.mapper.ResponseFindByUserMapper;
import br.com.user_service.mapper.UserMapper;
import br.com.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserServiceImp implements UserService{

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final ResponseFindByUserMapper responseFindByUserMapper;
	private final PasswordEncoder passwordEncoder;
	
	
	@Override
	public void createUser(UserCreateDto userCreateDto) {
		
		if(userRepository.existsByLogin(userCreateDto.login())) {
			throw new LoginJaExiste();
		}
		
		User user = userMapper.toEntity(userCreateDto);
		user.setPassword(passwordEncoder.encode(userCreateDto.password()));
		userRepository.save(user);
	}


	@Override
	public ResponseFindByUserDto findUserByLogin(String login) {
		User user = userRepository.findUserByLogin(login).orElseThrow(LoginNaoExisteException::new);
		return responseFindByUserMapper.toDto(user);
	}


	@Override
	public Page<ResponseFindByUserDto> retornaTodosOsUsuarios(Pageable pageable) {
		Page<User> todosOsUsuario = userRepository.findAll(pageable);
		return todosOsUsuario.map(responseFindByUserMapper::toDto);
	}
	
	
}
