package com.vm.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebSecurity
@EnableWebMvc
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebConfigSecurity extends WebSecurityConfigurerAdapter{
	
	@Autowired
	private ImplementacaoUserDetailsService implementacaoUserDetailsService;
	
	@Override 
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf()
		.disable()
		.authorizeRequests()
		.antMatchers("/css/**", "/js/**", "/static/**", "/resources/**").permitAll()
		.antMatchers(HttpMethod.GET, "/").permitAll() 	
		.antMatchers(HttpMethod.GET, "/cadastrousuario").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cadastroprescritor").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cadastroformula").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cadastroinsumo").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cadastrovisitador").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cadastrovisita").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/cadastroativo").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/catalogoativos").permitAll()
		.antMatchers(HttpMethod.GET, "/catalogoativoscompleto").permitAll()
		.anyRequest().authenticated()
		.and().
		formLogin().
		permitAll()
        .loginPage("/login")
        .defaultSuccessUrl("/")
        .failureUrl("/login?error=true")
        .and()
        .logout().logoutSuccessUrl("/login")
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and()
		.exceptionHandling().accessDeniedPage("/erro.html");
		
	}
	
	
	
	
	@Override // Cria autenticação do usuário com banco de dados ou em memória
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	
	}
	
	@Override // Ignora URL especificas
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		
	}
	
	public void defaultServletHandling(DefaultServletHandlerConfigurer configurer) {
         configurer.enable();
    }
	
	
	
	
	

}
