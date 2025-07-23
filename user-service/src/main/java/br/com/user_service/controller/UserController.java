package br.com.user_service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.user_service.dto.ResponseCreateUserDto;
import br.com.user_service.dto.ResponseLoginUserDto;
import br.com.user_service.dto.UserCreateDto;
import br.com.user_service.dto.UserLoginDto;
import br.com.user_service.entity.User;
import br.com.user_service.service.TokenService;
import br.com.user_service.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	
	@PostMapping("/login")
	public ResponseEntity login (@RequestBody @Valid UserLoginDto userLoginDto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDto.login(), userLoginDto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User)auth.getPrincipal());
		return ResponseEntity.ok(new ResponseLoginUserDto(token));
	}
	
	@PostMapping("/create")
	public ResponseEntity<ResponseCreateUserDto> createUser(@RequestBody @Valid UserCreateDto userCreateDto){
		userService.createUser(userCreateDto);
		ResponseCreateUserDto response = new ResponseCreateUserDto(ResponseCreateUserDto.CREATE_WITH_SUCESS);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

}
