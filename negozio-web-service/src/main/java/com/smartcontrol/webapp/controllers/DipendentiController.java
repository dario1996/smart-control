package com.smartcontrol.webapp.controllers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartcontrol.webapp.dtos.CategoriaDto;
import com.smartcontrol.webapp.dtos.DipendentiDto;
import com.smartcontrol.webapp.dtos.InfoMsg;
import com.smartcontrol.webapp.entities.Articoli;
import com.smartcontrol.webapp.entities.Dipendenti;
import com.smartcontrol.webapp.exceptions.BindingException;
import com.smartcontrol.webapp.exceptions.DuplicateException;
import com.smartcontrol.webapp.exceptions.NotFoundException;
import com.smartcontrol.webapp.services.DipendentiService;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/dipendenti")
public class DipendentiController 
{
    private DipendentiService dipendentiService;
    private ResourceBundleMessageSource errMessage;
	
	public DipendentiController(DipendentiService dipendentiService, 
			ResourceBundleMessageSource errMessage)
	{
		this.dipendentiService = dipendentiService;
		this.errMessage = errMessage;
	}
	
	// ------------------- LISTA DIPENDENTI ------------------------------------
	@SneakyThrows
	@GetMapping(value = "/lista", produces = "application/json")
	public ResponseEntity<List<DipendentiDto>> GetDipendenti(@RequestParam("shopId") String shopId)
	{
		log.info("****** Otteniamo i Dipendenti per il negozio: " + shopId + " *******");
		
		List<DipendentiDto> dipendenti = dipendentiService.SelByNegozio(shopId);
		
		if (dipendenti.isEmpty())
		{
			String ErrMsg = String.format("Nessun dipendente disponibile a sistema.");
			
			log.warning(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
			 
		}
		 
		return new ResponseEntity<List<DipendentiDto>>(dipendenti, HttpStatus.OK);
		
	}
	
	// ------------------- INSERIMENTO DIPENDENTE ------------------------------------
	@PostMapping(value = "/inserisci", produces = "application/json")
	@SneakyThrows
	public ResponseEntity<InfoMsg> createDip(@Valid @RequestBody Dipendenti dipendente, 
			@RequestParam("shopId") String shopId, BindingResult bindingResult) 
	{
		log.info("Salviamo l'articolo con codice " + dipendente.getCodiceDipendente());
			
		//controllo validità dati dipendente
		if (bindingResult.hasErrors())
		{
			String MsgErr = SortErrors(bindingResult.getFieldErrors());
				
			log.warning(MsgErr);
				
			throw new BindingException(MsgErr);
		}

		if (dipendentiService.SelByCodiceDipendente(dipendente.getCodiceDipendente()) != null)
		{
			String MsgErr = String.format("Articolo %s presente in anagrafica! "
					+ "Impossibile utilizzare il metodo POST", dipendente.getCodiceDipendente());
				
			log.warning(MsgErr);
				
			throw new DuplicateException(MsgErr);
		}
		
		// Assegna lo shopId al dipendente prima di salvarlo
	    dipendente.setShopId(shopId);
			
		dipendentiService.InsDipendente(dipendente);
			
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Inserimento Articolo eseguito con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- MODIFICA DIPENDENTE ------------------------------------
	@SneakyThrows
	@PutMapping(value = "/modifica", produces = "application/json")
	public ResponseEntity<InfoMsg> updateDip(@Valid @RequestBody Dipendenti dipendente, 
				@RequestParam("shopId") String shopId, BindingResult bindingResult)
	{
		log.info("Modifichiamo l'articolo con codice " + dipendente.getCodiceDipendente());
			
		//controllo validità dati articolo
		if (bindingResult.hasErrors())
		{
			String MsgErr = SortErrors(bindingResult.getFieldErrors());
				
			log.warning(MsgErr);

			throw new BindingException(MsgErr);
		}
			
		if (dipendentiService.SelByCodiceDipendente(dipendente.getCodiceDipendente()) == null)
		{
			String MsgErr = String.format("Dipendente %s non presente in anagrafica! "
					+ "Impossibile utilizzare il metodo PUT", dipendente.getCodiceDipendente());
				
			log.warning(MsgErr);
				
			throw new NotFoundException(MsgErr);
		}
		
		// Assegna lo shopId al dipendente prima di salvarlo
	    dipendente.setShopId(shopId);
			
		dipendentiService.InsDipendente(dipendente);
			
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Modifica Dipendente Eseguita con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- ELIMINAZIONE DIPENDENTE ------------------------------------
	@SneakyThrows
	@DeleteMapping(value = "/elimina/{codart}", produces = "application/json" )
	public ResponseEntity<?> deleteDip(@PathVariable("codart") String CodiceDipendente)
	{
		log.info("Eliminiamo il dipendente con codice " + CodiceDipendente);
			
		Dipendenti dipendente = dipendentiService.SelByCodiceDipendente2(CodiceDipendente);
			
		if (dipendente == null)
		{
			String MsgErr = String.format("Articolo %s non presente in anagrafica!", CodiceDipendente);
				
			log.warning(MsgErr);
				
			throw new NotFoundException(MsgErr);
		}
			
		dipendentiService.DelDipendente(dipendente);
			
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
			
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Articolo " + CodiceDipendente + " Eseguita Con Successo");
			
		return new ResponseEntity<>(responseNode, new HttpHeaders(), HttpStatus.OK);
		
	}
		
	private String SortErrors(List<FieldError> errors)
	{
		String msgErr = "";
		
		List<String> valErrors = new ArrayList<String>();
		
		for (FieldError item : errors)
		{
			valErrors.add(errMessage.getMessage(item, LocaleContextHolder.getLocale()) + ". ");
		}
		
		Collections.sort(valErrors);
		
		for(String str: valErrors) msgErr += str;
		
		return msgErr;
	}
}
