package com.xantrix.webapp.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import lombok.SneakyThrows;
 

@Configuration
@EnableWebSecurity
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JWTWebSecurityConfig extends WebSecurityConfigurerAdapter 
{

	@Autowired
	@Qualifier("CustomUserDetailsService")
	private UserDetailsService userDetailsService;
	
	@Value("${sicurezza.uri}")
	private String authenticationPath;
	
	@Value("${sicurezza.refresh}")
	private String refreshPath;

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception 
	{
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoderBean());
	}

	@Bean
	static PasswordEncoder passwordEncoderBean() 
	{
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception 
	{
		return super.authenticationManagerBean();
	}

	@Override
	@SneakyThrows
	protected void configure(HttpSecurity httpSecurity) 
	{
		httpSecurity
			.csrf(csrf -> csrf.disable())
			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.authorizeRequests(authz -> authz.anyRequest().authenticated());

	}
	
	@Override
	@SneakyThrows
	public void configure(WebSecurity webSecurity) 
	{
		webSecurity
				.ignoring().antMatchers(HttpMethod.POST, authenticationPath)
				.and()
				.ignoring().antMatchers(HttpMethod.OPTIONS, "/**")
				.and()
				.ignoring().antMatchers(HttpMethod.GET, refreshPath);
	}
	
	/*
	@Bean
	CorsConfigurationSource corsConfigurationSource() 
	{
		  List<String> allowedHeaders = new ArrayList<String>();
		  allowedHeaders.add("Authorization");
		  allowedHeaders.add("Content-Type");
		  allowedHeaders.add("Accept");
		  allowedHeaders.add("x-requested-with");
		  allowedHeaders.add("Cache-Control");
	
		  
	      CorsConfiguration configuration = new CorsConfiguration();
	      configuration.setAllowedOrigins(Arrays.asList("http://localhost:4200/"));
	      configuration.setAllowedMethods(Arrays.asList("GET","POST","OPTIONS","DELETE","PUT"));
	      configuration.setMaxAge((long) 3600);
	      configuration.setAllowedHeaders(allowedHeaders);
	      
	      
	      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
	      source.registerCorsConfiguration("/**", configuration);
	      
	      return source;
	 }
	 */
	
	
	
	
}
