package com.suhmoraes.cadastrodeemprego.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.suhmoraes.cadastrodeemprego.repository.UserRepository;
import com.suhmoraes.cadastrodeemprego.springsecurity.services.SSUserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter{

	@Bean
	public static BCryptPasswordEncoder passwordEnconder() {
		return new BCryptPasswordEncoder();		
	}
	
	@Autowired
	private SSUserDetailsService userDetailsService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception{
		return new SSUserDetailsService(userRepository);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception{
		http.authorizeRequests()
		.antMatchers("/", "h2-console/**").permitAll()
		.antMatchers("/admin").access("hasAuthority('ADMIN')")
		.anyRequest().authenticated()
		.and().formLogin().loginPage("/login").permitAll()
		.and()
		.logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.logoutSuccessUrl("/login").permitAll()
		.and()
		.httpBasic();
	http.csrf().disable();
	http.headers().frameOptions().disable();
	}
	
	//Autenticação do Usuário	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception{
		auth.inMemoryAuthentication()		
			.withUser("david").password(passwordEnconder().encode("davi2021"))
			.authorities("ADMIN")
			.and()
			.withUser("user")		
			.password(passwordEnconder().encode("password")).authorities("USER");
	}
}
