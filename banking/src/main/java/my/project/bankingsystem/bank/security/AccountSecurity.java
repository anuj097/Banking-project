package my.project.bankingsystem.bank.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
//@EnableMethodSecurity
public class AccountSecurity {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	// either we can use this or we can use the user name and password from
	// properties file
	@Bean
	public UserDetailsService userDetailsService() {
		UserDetails user = User.withUsername("Admin").password(passwordEncoder().encode("Password"))
				// .roles("ADMIN")
				.build();
		UserDetails user2 = User.withUsername("Customer").password(passwordEncoder().encode("Password"))
				// .roles("CUSTOM")
				.build();

		return new InMemoryUserDetailsManager(user, user2);

		// return CustomUserDetailsService(); -----> to return user details from db
		// <-----

	}
/*
	@Bean
	public SecurityFilterChain fileChain(HttpSecurity http) throws Exception {

		http
				// Disable CSRF protection (commonly for stateless applications)
				.csrf(c -> c.disable())

				// Define the authorization rules
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/accounts/accountBalance", "/accounts/accountDetail").permitAll() // Public
																											// endpoints
																											// - no need
																											// of
																											// authorization
						.anyRequest().authenticated() // Any other request requires authentication
				)

				// Enable HTTP Basic authentication
				.httpBasic(httpBasic -> httpBasic.realmName("This is a Bank Portal")
						.authenticationEntryPoint(restAuthenticationEntryPoint()));

//			http.csrf().disable()
//			.authorizeRequests()
//			.requestMatchers("/create")
//			.permitAll()
//			.requestMatchers("//allProduct")
//			.permitAll()
//			.anyRequest()
//			.authenticated()
//			.and()
//			.httpBasic();

		// httpSecurity.oauth2Login().loginPage("/create").permitAll()

		return http.build();
	} 

	@Bean
	public AuthenticationEntryPoint restAuthenticationEntryPoint() {
		return (request, response, authException) -> {
			response.setContentType("application/json");
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.getWriter().write("{\"error\": \"Unauthorized\", \"message\": \"Authentication required\"}");
		};
	} */
	
	@Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
        return builder.getAuthenticationManager();
    }

}
