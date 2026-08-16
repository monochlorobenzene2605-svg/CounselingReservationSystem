package com.example.demo.config;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
				.authorizeHttpRequests((auth) -> auth
						.requestMatchers("/login", "/style/login.css").permitAll()
						.requestMatchers("/student/**").hasRole("STUDENT")
						.requestMatchers("/counselor/**").hasRole("COUNSELOR")
						.anyRequest().authenticated())
				.formLogin(form -> form
						.loginPage("/login")
						.successHandler((req, res, auth) -> {
                            String today = LocalDate.now()
                                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

							boolean isStudent = auth.getAuthorities()
									.stream()
									.anyMatch(a -> a.getAuthority()
											.equals("ROLE_STUDENT"));

							if (isStudent) {
								res.sendRedirect("/student/timeline" + "?date=" + today);
							} else {
								res.sendRedirect("/counselor/timeline" + "?date=" + today);
							}
						}))
				.logout(logout -> logout
					    .logoutUrl("/logout")
					    .logoutSuccessUrl("/login?logout")
					    .invalidateHttpSession(true)
					    .deleteCookies("JSESSIONID")
					    .permitAll()
				);

		return http.build();
	}
}
