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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.smartcontrol.webapp.dtos.ArticoliDto;
import com.smartcontrol.webapp.dtos.InfoMsg;
import com.smartcontrol.webapp.entities.Articoli;
import com.smartcontrol.webapp.exceptions.BindingException;
import com.smartcontrol.webapp.exceptions.DuplicateException;
import com.smartcontrol.webapp.exceptions.NotFoundException;
import com.smartcontrol.webapp.services.ArticoliService;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/articoli")
//@CrossOrigin("http://localhost:4200/")
public class ArticoliController 
{
	private ArticoliService articoliService;
	private ResourceBundleMessageSource errMessage;
	
	public ArticoliController(
			ArticoliService articoliService,
			ResourceBundleMessageSource errMessage)
	{
		this.articoliService = articoliService;
		this.errMessage = errMessage;
	}
	
	@SneakyThrows
	@GetMapping(value = "/test", produces = "application/json")
	public ResponseEntity<InfoMsg> testAuth()
	{
		return new ResponseEntity<InfoMsg>( 
				new InfoMsg(LocalDate.now(),"Test Autenticazione OK!"), HttpStatus.OK);
	}
	
	@SneakyThrows
	@GetMapping(value = "/cerca/barcode/{ean}", produces = "application/json")
	public ResponseEntity<ArticoliDto> listArtByEan(@PathVariable("ean") String Ean) 
	{
		ArticoliDto articolo = articoliService.SelByBarcode(Ean);
		
		if (articolo == null)
		{
			String ErrMsg = String.format("Il barcode %s non e' stato trovato!", Ean);
			
			log.warning(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
		}
		
		return new ResponseEntity<ArticoliDto>(articolo, HttpStatus.OK);
	}
	
	@SneakyThrows
	@GetMapping(value = "/cerca/codice/{codart}", produces = "application/json")
	public ResponseEntity<ArticoliDto> listArtByCodArt(@PathVariable("codart") String CodArt)
	{
		log.info("****** Otteniamo l'articolo con codice " + CodArt + " *******");
		
		ArticoliDto articolo = articoliService.SelByCodArt(CodArt);
		
		if (articolo == null)
		{
			String ErrMsg = String.format("L'articolo con codice %s non e' stato trovato!", CodArt);
			
			log.warning(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
		}
		
		return new ResponseEntity<ArticoliDto>(articolo, HttpStatus.OK);
	}
	
	@SneakyThrows
	@GetMapping(value = "/cerca/descrizione/{filter}", produces = "application/json")
	public ResponseEntity<List<ArticoliDto>> listArtByDesc(@PathVariable("filter") String Filter)
	{
		log.info("****** Otteniamo gli articoli con Descrizione: " + Filter + " *******");
		
		List<ArticoliDto> articoli = articoliService.SelByDescrizione(Filter);
		
		if (articoli.isEmpty())
		{
			String ErrMsg = String.format("Non e' stato trovato alcun articolo avente descrizione %s", Filter);
			
			log.warning(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
			
		}
		
		return new ResponseEntity<List<ArticoliDto>>(articoli, HttpStatus.OK);
		
	}
	
	// ------------------- INSERIMENTO ARTICOLO ------------------------------------
	@PostMapping(value = "/inserisci", produces = "application/json")
	@SneakyThrows
	public ResponseEntity<InfoMsg> createArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult) 
	{
		log.info("Salviamo l'articolo con codice " + articolo.getCodArt());
		
		//controllo validità dati articolo
		if (bindingResult.hasErrors())
		{
			String MsgErr = SortErrors(bindingResult.getFieldErrors());
			
			log.warning(MsgErr);
			
			throw new BindingException(MsgErr);
		}

		if (articoliService.SelByCodArt(articolo.getCodArt()) != null)
		{
			String MsgErr = String.format("Articolo %s presente in anagrafica! "
					+ "Impossibile utilizzare il metodo POST", articolo.getCodArt());
			
			log.warning(MsgErr);
			
			throw new DuplicateException(MsgErr);
		}
		
		articoliService.InsArticolo(articolo);
		
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Inserimento Articolo eseguito con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- MODIFICA ARTICOLO ------------------------------------
	@SneakyThrows
	@PutMapping(value = "/modifica", produces = "application/json")
	public ResponseEntity<InfoMsg> updateArt(@Valid @RequestBody Articoli articolo, BindingResult bindingResult)
	{
		log.info("Modifichiamo l'articolo con codice " + articolo.getCodArt());
		
		//controllo validità dati articolo
		if (bindingResult.hasErrors())
		{
			String MsgErr = SortErrors(bindingResult.getFieldErrors());
			
			log.warning(MsgErr);

			throw new BindingException(MsgErr);
		}
		
		if (articoliService.SelByCodArt(articolo.getCodArt()) == null)
		{
			String MsgErr = String.format("Articolo %s non presente in anagrafica! "
					+ "Impossibile utilizzare il metodo PUT", articolo.getCodArt());
			
			log.warning(MsgErr);
			
			throw new NotFoundException(MsgErr);
		}
		
		articoliService.InsArticolo(articolo);
		
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Modifica Articolo Eseguita con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- ELIMINAZIONE ARTICOLO ------------------------------------
	@SneakyThrows
	@DeleteMapping(value = "/elimina/{codart}", produces = "application/json" )
	public ResponseEntity<?> deleteArt(@PathVariable("codart") String CodArt)
	{
		log.info("Eliminiamo l'articolo con codice " + CodArt);
		
		Articoli articolo = articoliService.SelByCodArt2(CodArt);
		
		if (articolo == null)
		{
			String MsgErr = String.format("Articolo %s non presente in anagrafica!",CodArt);
			
			log.warning(MsgErr);
			
			throw new NotFoundException(MsgErr);
		}
		
		articoliService.DelArticolo(articolo);
		
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
		
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Articolo " + CodArt + " Eseguita Con Successo");
		
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
