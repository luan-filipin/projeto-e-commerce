package br.com.user_service.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.user_service.dto.ResponseCreateUserDto;
import br.com.user_service.dto.ResponseFindByUserDto;
import br.com.user_service.dto.ResponseLoginUserDto;
import br.com.user_service.dto.UserCreateDto;
import br.com.user_service.dto.UserLoginDto;
import br.com.user_service.entity.User;
import br.com.user_service.service.TokenService;
import br.com.user_service.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class UserController {
	
	private final UserService userService;
	private final AuthenticationManager authenticationManager;
	private final TokenService tokenService;
	
	@PostMapping("/users/token")
	public ResponseEntity login (@RequestBody @Valid UserLoginDto userLoginDto) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(userLoginDto.login(), userLoginDto.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var token = tokenService.generateToken((User)auth.getPrincipal());
		return ResponseEntity.ok(new ResponseLoginUserDto(token));
	}
	
	@PostMapping("/users")
	public ResponseEntity<ResponseCreateUserDto> createUser(@RequestBody @Valid UserCreateDto userCreateDto){
		userService.createUser(userCreateDto);
		ResponseCreateUserDto response = new ResponseCreateUserDto(ResponseCreateUserDto.CREATE_WITH_SUCESS);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping("/users/{login}")
	public ResponseEntity<ResponseFindByUserDto> retornaUserPeloLogin(@PathVariable @NotBlank String login){
		ResponseFindByUserDto user = userService.findUserByLogin(login);
		return ResponseEntity.ok(user);
	}
	
	@GetMapping("/users/lote")
	public ResponseEntity<Page<ResponseFindByUserDto>> retornarTodosOsUsuarios(
			@PageableDefault(page = 0, size = 20, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
		return ResponseEntity.ok(userService.retornaTodosOsUsuarios(pageable));
	}

}
