package br.com.user_service.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.user_service.repository.UserRepository;
import br.com.user_service.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Component
public class SecurityFilter extends OncePerRequestFilter {

	private final UserRepository userRepository;
	private final TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
	    
	    var token = this.recoverToken(request);
	    if (token != null) {
	        var login = tokenService.validadeToken(token);
	        
	        if (login != null && !login.isBlank()) {
	            UserDetails user = userRepository.findByLogin(login);
	            
	            if (user != null) {
	                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
	                SecurityContextHolder.getContext().setAuthentication(authentication);
	            }
	        }
	    }

	    filterChain.doFilter(request, response);
	}

	private String recoverToken(HttpServletRequest request) {
		var authHeader = request.getHeader("Authorization");
		if (authHeader == null) {
			return null;
		}
		return authHeader.replace("Bearer ", "");
	}

}
