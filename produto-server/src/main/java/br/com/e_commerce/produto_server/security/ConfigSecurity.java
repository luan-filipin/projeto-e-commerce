package br.com.e_commerce.produto_server.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class ConfigSecurity {
	
	private static final String PRODUTOS_ENDPOINT = "/api/produtos/**";

	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
            	.requestMatchers(HttpMethod.PUT, "/api/produtos/estoque/movimentar/**").permitAll()
                .requestMatchers(HttpMethod.POST, "/api/produtos").permitAll()
                .requestMatchers(HttpMethod.GET, PRODUTOS_ENDPOINT).permitAll()
                .requestMatchers(HttpMethod.PUT, PRODUTOS_ENDPOINT).permitAll()
                .requestMatchers(HttpMethod.DELETE, PRODUTOS_ENDPOINT).permitAll()
                .requestMatchers("/error").permitAll()
                .anyRequest().authenticated()
            )
            .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }
}
