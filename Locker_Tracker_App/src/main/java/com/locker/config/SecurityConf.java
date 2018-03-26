package com.locker.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableGlobalMethodSecurity(securedEnabled = true)
@Configuration
public class SecurityConf extends WebSecurityConfigurerAdapter {
	
	@Autowired
	public void configureAuth(AuthenticationManagerBuilder auth) throws Exception {
		auth
		  .inMemoryAuthentication()
		     .withUser("Gyula@something.com") 
		     .password("pass")
		     .roles("USER");
	}
	
	@Override
	protected void configure(HttpSecurity httpSec) throws Exception {
	   httpSec
		  .authorizeRequests()
		      .antMatchers("/db/**").permitAll()
		      .anyRequest().authenticated()
		      .and()
		  .formLogin()
		      .loginPage("/login")
		      .permitAll();
	   
	   httpSec.csrf().disable();
	   httpSec.headers().frameOptions().disable();
	}
	
}
