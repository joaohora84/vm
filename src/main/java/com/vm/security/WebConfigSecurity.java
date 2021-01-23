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
		.antMatchers("/css/**", "/js/**", "/img/**", "/static/**", "/resources/**").permitAll()
		.antMatchers(HttpMethod.GET,"/", "/index", "/cadastroclinica", "/cadastrousuario", "/cadastroprescritor", "/cadastroformula", "/cadastroinsumo",
				"/cadastrovisitador", "/cadastrovisita", "/cadastroativo", "/cadastroativoformula", "/agenda", "/cadastrosecretaria", "/listarusuarios",
				"/listarprescritores", "/listarativos", "/listarformulas", "/listarvisitadores", "/listarvisitas", "/listarprescritores", "/listarclinicas",
				"/listarsecretarias", "/prescritor_relatorios", "/formula_relatorios", "/visitador_relatorios", "/visita_relatorios").hasRole("ADMIN")
		.antMatchers(HttpMethod.GET, "/indexcatalogo", "/catalogoativos", "/catalogoativoscompleto").permitAll()
		.anyRequest().authenticated()
		.and().
		formLogin().
		permitAll()
        .loginPage("/login")
        .defaultSuccessUrl("/indexcatalogo")
        .failureUrl("/login?error=true")
        .and()
        .logout().logoutSuccessUrl("/login")
        .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
		.and()
		.exceptionHandling().accessDeniedPage("/erro.html");
		
	}
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(implementacaoUserDetailsService)
		.passwordEncoder(new BCryptPasswordEncoder());
	
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**");
		
	}
	
	public void defaultServletHandling(DefaultServletHandlerConfigurer configurer) {
         configurer.enable();
    }
	
	
	
	
	

}
