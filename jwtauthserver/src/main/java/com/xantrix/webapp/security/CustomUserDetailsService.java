package com.xantrix.webapp.security;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.client.support.BasicAuthenticationInterceptor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@Service("CustomUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService
{
	@Autowired
	private UserConfig Config;
	
	@Override
	@SneakyThrows
	public UserDetails loadUserByUsername(String userId) 
	{
		String ErrMsg = "";
		
		if (userId == null || userId.length() < 5) 
		{
			ErrMsg = "Nome utente assente o non valido";
			
			log.warning(ErrMsg);
			
	    	throw new UsernameNotFoundException(ErrMsg); 
		} 
		
		Utenti utente = this.GetHttpValue(userId);
		
		if (utente == null)
		{
			ErrMsg = String.format("Utente %s non Trovato!!", userId);
			
			log.warning(ErrMsg);
			
			throw new UsernameNotFoundException(ErrMsg);
		}
		
		UserBuilder builder = null;
		builder = org.springframework.security.core.userdetails.User.withUsername(utente.getUsername());
		builder.disabled((utente.getAttivo().equals("Si") ? false : true));
		builder.password(utente.getPassword());
		
		String[] profili = utente.getRuoli()
				 .stream().map(a -> "ROLE_" + a).toArray(String[]::new);
		
		builder.authorities(profili);
		
		return builder.build();
		
		
	}
	
	private Utenti GetHttpValue(String userId)
	{
		URI url = null;

		try 
		{
			String SrvUrl = Config.getSrvUrl();

			url = new URI(SrvUrl + userId);
		} 
		catch (URISyntaxException e) 
		{
			 
			e.printStackTrace();
		}
		
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.getInterceptors().add(new BasicAuthenticationInterceptor(Config.getUserId(), Config.getPassword()));
		
		Utenti utente = null;

		try 
		{
			utente = restTemplate.getForObject(url, Utenti.class);	
		} 
		catch (Exception e) 
		{
			String errMsg = String.format("Connessione al servizio di autenticazione non riuscita o servizio assente!!");
			
			log.warning(errMsg);
			
		}
		
		return utente;
		
	}
	
	
	
	
	
	
	
	
}
	