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
import com.smartcontrol.webapp.dtos.InfoMsg;
import com.smartcontrol.webapp.dtos.ServiziDto;
import com.smartcontrol.webapp.entities.Dipendenti;
import com.smartcontrol.webapp.entities.Servizi;
import com.smartcontrol.webapp.exceptions.BindingException;
import com.smartcontrol.webapp.exceptions.DuplicateException;
import com.smartcontrol.webapp.exceptions.NotFoundException;
import com.smartcontrol.webapp.services.ServiziService;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/servizi")
public class ServiziController 
{
	private ServiziService serviziService;
    private ResourceBundleMessageSource errMessage;
	
	public ServiziController(ServiziService serviziService, 
			ResourceBundleMessageSource errMessage)
	{
		this.serviziService = serviziService;
		this.errMessage = errMessage;
	}
	
	// ------------------- LISTA SERVIZI ------------------------------------
	@SneakyThrows
	@GetMapping(value = "/lista", produces = "application/json")
	public ResponseEntity<List<ServiziDto>> GetServizi(@RequestParam("shopId") String shopId)
	{
		log.info("****** Otteniamo i Servizi per il negozio: " + shopId + " *******");
			
		List<ServiziDto> servizi = serviziService.SelByNegozio(shopId);
			
		if (servizi.isEmpty())
		{
			String ErrMsg = String.format("Nessun servizio disponibile a sistema.");
				
			log.warning(ErrMsg);
				
			throw new NotFoundException(ErrMsg);
				 
		}
			 
		return new ResponseEntity<List<ServiziDto>>(servizi, HttpStatus.OK);
			
	}
	
	// ------------------- INSERIMENTO SERVIZIO ------------------------------------
	@PostMapping(value = "/inserisci", produces = "application/json")
	@SneakyThrows
	public ResponseEntity<InfoMsg> createServ(@Valid @RequestBody Servizi servizio, 
			@RequestParam("shopId") String shopId, BindingResult bindingResult) 
	{
		log.info("Salviamo il servizio con codice " + servizio.getServiceCode());
				
		//controllo validità dati dipendente
		if (bindingResult.hasErrors())
		{
			String MsgErr = SortErrors(bindingResult.getFieldErrors());
					
			log.warning(MsgErr);
					
			throw new BindingException(MsgErr);
		}

		if (serviziService.SelByServiceCode(servizio.getServiceCode()) != null)
		{
			String MsgErr = String.format("Servizio %s presente in anagrafica! "
					+ "Impossibile utilizzare il metodo POST", servizio.getServiceCode());
					
			log.warning(MsgErr);
					
			throw new DuplicateException(MsgErr);
		}
			
		// Assegna lo shopId al dipendente prima di salvarlo
		servizio.setShopId(shopId);
				
		serviziService.InsServizio(servizio);
				
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Inserimento Servizio eseguito con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- MODIFICA SERVIZIO ------------------------------------
	@SneakyThrows
	@PutMapping(value = "/modifica", produces = "application/json")
	public ResponseEntity<InfoMsg> updateServ(@Valid @RequestBody Servizi servizio, 
				@RequestParam("shopId") String shopId, BindingResult bindingResult)
	{
		log.info("Modifichiamo il servizio con codice " + servizio.getServiceCode());
				
		//controllo validità dati articolo
		if (bindingResult.hasErrors())
		{
			String MsgErr = SortErrors(bindingResult.getFieldErrors());
					
			log.warning(MsgErr);

			throw new BindingException(MsgErr);
		}
				
		if (serviziService.SelByServiceCode(servizio.getServiceCode()) == null)
		{
			String MsgErr = String.format("Servizio %s non presente in anagrafica! "
					+ "Impossibile utilizzare il metodo PUT", servizio.getServiceCode());
					
			log.warning(MsgErr);
					
			throw new NotFoundException(MsgErr);
		}
			
		// Assegna lo shopId al dipendente prima di salvarlo
		servizio.setShopId(shopId);
				
		serviziService.InsServizio(servizio);
				
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Modifica Servizio Eseguita con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- ELIMINAZIONE DIPENDENTE ------------------------------------
	@SneakyThrows
	@DeleteMapping(value = "/elimina/{codart}", produces = "application/json" )
	public ResponseEntity<?> deleteDip(@PathVariable("codart") String ServiceCode)
	{
		log.info("Eliminiamo il servizio con codice " + ServiceCode);
				
		Servizi servizio = serviziService.SelByServiceCode2(ServiceCode);
				
		if (servizio == null)
		{
			String MsgErr = String.format("Servizio %s non presente in anagrafica!", ServiceCode);
					
			log.warning(MsgErr);
					
			throw new NotFoundException(MsgErr);
		}
				
		serviziService.DelServizio(servizio);
				
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
				
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Articolo " + ServiceCode + " Eseguita Con Successo");
				
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
