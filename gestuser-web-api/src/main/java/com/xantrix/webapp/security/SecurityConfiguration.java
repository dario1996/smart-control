package com.xantrix.webapp.security;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import lombok.SneakyThrows;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration 
{
	private static String REALM = "REAME";
	 
	//authentication
	@Bean
    UserDetailsService userDetailsService() 
    {
        UserDetails user  = User
        		.withUsername("ReadUser")
                .password(new BCryptPasswordEncoder().encode("BimBumBam_2018"))
                .roles("USER")
                .build();
        UserDetails admin = User
        		.withUsername("Admin")
                .password(new BCryptPasswordEncoder().encode("MagicaBula_2018"))
                .roles("USER","ADMIN")
                .build();
        
        return new InMemoryUserDetailsManager(admin, user);
       
    }
	
	@Bean
    BCryptPasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
	
	//private static final String[] USER_MATCHER = { "/utenti/cerca/**", "/api/negozi/**"};
	private static final String[] ADMIN_MATCHER = { "/utenti/inserisci/**", "/utenti/elimina/**"};
	
	
	@Bean
	@SneakyThrows
    SecurityFilterChain securityFilterChain(HttpSecurity http) 
	{
		http
			.csrf(csrf -> csrf.disable())
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
			.httpBasic(e -> e.realmName(REALM).authenticationEntryPoint(getBasicAuthEntryPoint()))
			.authorizeHttpRequests(authz -> 
            {
				authz
				    .requestMatchers("/api/negozi/**").permitAll()
				    .requestMatchers(ADMIN_MATCHER).hasRole("ADMIN")
				    //.requestMatchers(USER_MATCHER).hasRole("USER")
				    .anyRequest().authenticated();
			});
		
		return http.build();
	}
	
	@Bean
	AuthEntryPoint getBasicAuthEntryPoint()
	{
		return new AuthEntryPoint();
	}
	
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

	
}
