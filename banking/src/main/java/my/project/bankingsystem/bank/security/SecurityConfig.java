package my.project.bankingsystem.bank.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import my.project.bankingsystem.bank.jwtsecurity.JWTAuthenticationEntryPoint;
import my.project.bankingsystem.bank.jwtsecurity.JWTAuthenticationFilter;

@Configuration
public class SecurityConfig {
	
	@Autowired
	private JWTAuthenticationEntryPoint point;
	
	@Autowired
	private JWTAuthenticationFilter filter;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception { // Using JWT 
		
		http.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.disable())
			.authorizeHttpRequests(auth -> auth.requestMatchers("/auth/login", "/accounts/accountBalance", "/accounts/accountDetail").permitAll()
			.anyRequest().authenticated())
			.exceptionHandling(ex-> ex.authenticationEntryPoint(point))
			.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		return http.build();
	}
}
