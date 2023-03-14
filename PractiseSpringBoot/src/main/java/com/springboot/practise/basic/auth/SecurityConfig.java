package com.springboot.practise.basic.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.AllArgsConstructor;

@Configuration
@AllArgsConstructor
public class SecurityConfig{
	
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		.csrf().disable()
		.cors().disable()
		.authorizeHttpRequests()
		.requestMatchers(HttpMethod.DELETE,"users/*/todos/").hasRole("ADMIN")
		.requestMatchers(HttpMethod.POST).hasAnyRole("ADMIN","USER")
		.requestMatchers(HttpMethod.GET).permitAll()
		.anyRequest().authenticated()
		.and()
		.httpBasic()
		.and()
		.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		return http.build();
    }
	
	@Bean
	public UserDetailsService users() {
		
		
		UserDetails admin = User.builder()
				.username("ram")
				.password(passwordEncoder.encode("ram"))
				.roles("ADMIN")
				.build();
		
		UserDetails user = User.builder()
				.username("user")
				.password(passwordEncoder.encode("user-pass"))
				.roles("USER")
				.build();
		
		UserDetails getter = User.builder()
				.username("getUser")
				.password(passwordEncoder.encode("user-pass"))
				.roles("USER")
				.build();
		return new InMemoryUserDetailsManager(admin,user,getter);
	} 
}


