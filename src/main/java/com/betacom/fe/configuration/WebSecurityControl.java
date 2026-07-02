package com.betacom.fe.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class WebSecurityControl {
	
	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests((requests) -> requests
				.requestMatchers("/admin", "/admin/**").hasRole("ADMIN")
				.requestMatchers("/login").permitAll()
				.anyRequest().authenticated()				
				)
				.formLogin((form) -> form
						.loginPage("/login")
						.permitAll()
						)
				.logout((logout) -> logout.permitAll());
		return http.build();
	}
	@Bean
	PasswordEncoder getPaswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	UserDetailsService userDetailsService() {
		
		List<UserDetails> userDetailsList = new ArrayList<UserDetails>();
		
		UserDetails user = 
				User.withUsername("user")
					.password(getPaswordEncoder().encode("user").toString())
					.roles("USER")
					.build();
							
		UserDetails admin = 
				User.withUsername("admin")
					.password(getPaswordEncoder().encode("admin").toString())
					.roles("ADMIN")
					.build();
		
		userDetailsList.add(user);
		userDetailsList.add(admin);
		
		return new InMemoryUserDetailsManager(userDetailsList);
		
	}
}
