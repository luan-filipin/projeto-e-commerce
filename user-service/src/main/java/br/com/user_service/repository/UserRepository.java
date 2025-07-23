package br.com.user_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import br.com.user_service.entity.User;

public interface UserRepository extends JpaRepository<User, Long>{
	
	UserDetails findByLogin(String username);
	boolean existsByLogin(String login);
	
}
