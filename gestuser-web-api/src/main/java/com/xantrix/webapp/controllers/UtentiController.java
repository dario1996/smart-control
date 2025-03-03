package com.xantrix.webapp.controllers;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.xantrix.webapp.exception.BindingException;
import com.xantrix.webapp.exception.NotFoundException;
import com.xantrix.webapp.models.Utenti;
import com.xantrix.webapp.services.UtentiService;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/api/utenti")
public class UtentiController
{
	@Autowired
	UtentiService utentiService;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private ResourceBundleMessageSource errMessage;
	
	@GetMapping(value = "/cerca/tutti")
	public List<Utenti> getAllUser()
	{
		log.info("Otteniamo tutti gli utenti");

		return utentiService.SelTutti();
	}
	
	@GetMapping(value = "/cerca/userid/{username}")
	@SneakyThrows
	public Utenti getUtente(@PathVariable("username") String Username) 
	{
		log.info("Otteniamo l'utente " + Username);
		
		Utenti utente = utentiService.SelUser(Username);
		
		if (utente == null)
		{
			String ErrMsg = String.format("L'utente %s non e' stato trovato!", Username);
			
			log.warning(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
		}
		else
		{ 
			log.info(String.format("L'utente %s e' stato trovato!", Username));
		}
		
		return utente;
	}
	
	// ------------------- INSERIMENTO / MODIFICA UTENTE ------------------------------------
	@PostMapping(value = "/inserisci", produces = "application/json")
	@SneakyThrows
	public ResponseEntity<InfoMsg> addNewUser(@Valid @RequestBody Utenti utente, 
	    BindingResult bindingResult) {

	    Utenti checkUtente = utentiService.SelUser(utente.getUsername());

	    if (bindingResult.hasErrors()) {
	        String MsgErr = errMessage.getMessage(bindingResult.getFieldError(), LocaleContextHolder.getLocale());
	        log.warning(MsgErr);
	        throw new BindingException(MsgErr);
	    }
	    
	    // Controlla se l'username esiste già
	    if (utentiService.CheckExistUsername(utente.getUsername())) {
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
	                .body(new InfoMsg(LocalDate.now(), "Username già in uso!"));
	    }

	    if (checkUtente != null) {
	        log.info("Modifica Utente");
	        utente.setUsername(checkUtente.getUsername());

	        // Mantiene i dati non aggiornati
	        if (utente.getPassword() == null || utente.getPassword().isBlank()) {
	            utente.setPassword(checkUtente.getPassword()); // Evita di codificare una password già codificata
	        } else {
	            utente.setPassword(passwordEncoder.encode(utente.getPassword())); // Codifica solo se cambiata
	        }

	    } else {
	        log.info("Inserimento Nuovo Utente");
	        utente.setPassword(passwordEncoder.encode(utente.getPassword())); // Sempre codificata all'inizio
	    }

	    // Gestione lista ruoli (da List<String> a array per PostgreSQL)
	    if (utente.getRuoli() != null) {
	        utente.setRuoli(Arrays.asList(utente.getRuoli().toArray(new String[0])));
	    }


	    // Associa i negozi all'utente
	    if (utente.getNegozi() != null) {
	        utente.getNegozi().forEach(negozio -> negozio.setUtente(utente));
	    }

	    utentiService.Save(utente);

	    return new ResponseEntity<>(
	        new InfoMsg(LocalDate.now(), 
	        String.format("Inserimento Utente %s Eseguito Con Successo", utente.getUsername())),
	        HttpStatus.CREATED
	    );
	}


	// ------------------- ELIMINAZIONE UTENTE ------------------------------------
	@DeleteMapping(value = "/elimina/{id}")
	@SneakyThrows
	public ResponseEntity<?> deleteUser(@PathVariable("id") String UserId)
	{
		log.info("Eliminiamo l'utente con id " + UserId);

		Utenti utente = utentiService.SelUser(UserId);

		if (utente == null)
		{
			String MsgErr = String.format("Utente %s non presente in anagrafica! ",UserId);
			
			log.warning(MsgErr);
			
			throw new NotFoundException(MsgErr);
		}

		utentiService.Delete(utente);
		
		HttpHeaders headers = new HttpHeaders();
		ObjectMapper mapper = new ObjectMapper();

		headers.setContentType(MediaType.APPLICATION_JSON);

		ObjectNode responseNode = mapper.createObjectNode();

		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Utente " + UserId + " Eseguita Con Successo");

		return new ResponseEntity<>(responseNode, headers, HttpStatus.OK);
	}
}
