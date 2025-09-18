package com.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.api.security.JwtRequestFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Autowired
	private JwtRequestFilter jwtRequestFilter;

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http, JwtRequestFilter jwtRequestFilter) throws Exception {
		http
		//.csrf(csrf ->	csrf.ignoringRequestMatchers("/h2-console/**"))
		.csrf(AbstractHttpConfigurer::disable)
		.headers(headers ->	headers.frameOptions(frame -> frame.sameOrigin()))
		.authorizeHttpRequests(auth -> auth
				.requestMatchers("/h2-console/**","/swagger-ui/**", "/v3/api-docs/**").permitAll()
				.requestMatchers("/exchange/get/**","/exchange/getAllExchanges").hasAnyRole("USER")
				.requestMatchers("/exchange/save","/exchange/updateExchange","/exchange/delete/**","/exchange/deleteAll").hasRole("ADMIN") 
				.anyRequest().authenticated()
				)
		.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}

	
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
}




