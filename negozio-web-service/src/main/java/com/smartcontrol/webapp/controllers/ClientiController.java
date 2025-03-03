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
import com.smartcontrol.webapp.dtos.ClientiDto;
import com.smartcontrol.webapp.dtos.InfoMsg;
import com.smartcontrol.webapp.entities.Clienti;
import com.smartcontrol.webapp.exceptions.BindingException;
import com.smartcontrol.webapp.exceptions.DuplicateException;
import com.smartcontrol.webapp.exceptions.NotFoundException;
import com.smartcontrol.webapp.services.ClientiService;

import jakarta.validation.Valid;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/clienti")
public class ClientiController 
{
	private ClientiService clientiService;
    private ResourceBundleMessageSource errMessage;
	
	public ClientiController(ClientiService clientiService, 
			ResourceBundleMessageSource errMessage)
	{
		this.clientiService = clientiService;
		this.errMessage = errMessage;
	}
	
	// ------------------- LISTA CLIENTI ------------------------------------
	@SneakyThrows
	@GetMapping(value = "/lista", produces = "application/json")
	public ResponseEntity<List<ClientiDto>> GetClienti(@RequestParam("shopId") String shopId)
	{
		log.info("****** Otteniamo i Clienti per il negozio: " + shopId + " *******");
			
		List<ClientiDto> clienti = clientiService.SelByNegozio(shopId);
			
		if (clienti.isEmpty())
		{
			String ErrMsg = String.format("Nessun cliente disponibile a sistema.");
				
			log.warning(ErrMsg);
				
			throw new NotFoundException(ErrMsg);
				 
		}
			 
		return new ResponseEntity<List<ClientiDto>>(clienti, HttpStatus.OK);
			
	}
	
	// ------------------- INSERIMENTO CLIENTE ------------------------------------
	@PostMapping(value = "/inserisci", produces = "application/json")
	@SneakyThrows
	public ResponseEntity<InfoMsg> createCliente(@Valid @RequestBody Clienti cliente, 
			@RequestParam("shopId") String shopId, BindingResult bindingResult) 
	{
		log.info("Salviamo il cliente con codice " + cliente.getCodiceCliente());
				
		//controllo validità dati dipendente
		if (bindingResult.hasErrors())
		{
			String MsgErr = SortErrors(bindingResult.getFieldErrors());
					
			log.warning(MsgErr);
					
			throw new BindingException(MsgErr);
		}

		if (clientiService.SelBycodiceCliente(cliente.getCodiceCliente()) != null)
		{
			String MsgErr = String.format("Cliente %s presente in anagrafica! "
					+ "Impossibile utilizzare il metodo POST", cliente.getCodiceCliente());
					
			log.warning(MsgErr);
					
			throw new DuplicateException(MsgErr);
		}
			
		// Assegna lo shopId al dipendente prima di salvarlo
		cliente.setShopId(shopId);
				
		clientiService.InsCliente(cliente);
				
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Inserimento Cliente eseguito con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- MODIFICA CLIENTE ------------------------------------
	@SneakyThrows
	@PutMapping(value = "/modifica", produces = "application/json")
	public ResponseEntity<InfoMsg> updateCliente(@Valid @RequestBody Clienti cliente, 
				@RequestParam("shopId") String shopId, BindingResult bindingResult)
	{
		log.info("Salviamo il cliente con codice " + cliente.getCodiceCliente());
				
		//controllo validità dati articolo
		if (bindingResult.hasErrors())
		{
			String MsgErr = SortErrors(bindingResult.getFieldErrors());
					
			log.warning(MsgErr);

			throw new BindingException(MsgErr);
		}
				
		if (clientiService.SelBycodiceCliente(cliente.getCodiceCliente()) == null)
		{
			String MsgErr = String.format("Cliente %s non presente in anagrafica! "
					+ "Impossibile utilizzare il metodo PUT", cliente.getCodiceCliente());
					
			log.warning(MsgErr);
					
			throw new NotFoundException(MsgErr);
		}
			
		// Assegna lo shopId al dipendente prima di salvarlo
		cliente.setShopId(shopId);
				
		clientiService.InsCliente(cliente);
				
		return new ResponseEntity<InfoMsg>(new InfoMsg(LocalDate.now(), 
				"Modifica Cliente Eseguita con successo!"), HttpStatus.CREATED);
	}
	
	// ------------------- ELIMINAZIONE DIPENDENTE ------------------------------------
	@SneakyThrows
	@DeleteMapping(value = "/elimina/{codart}", produces = "application/json" )
	public ResponseEntity<?> deleteCliente(@PathVariable("codart") String CodiceCliente)
	{
		log.info("Eliminiamo il cliente con codice " + CodiceCliente);
				
		Clienti cliente = clientiService.SelBycodiceCliente2(CodiceCliente);
				
		if (cliente == null)
		{
			String MsgErr = String.format("Cliente %s non presente in anagrafica!", CodiceCliente);
					
			log.warning(MsgErr);
					
			throw new NotFoundException(MsgErr);
		}
				
		clientiService.DelCliente(cliente);
				
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode responseNode = mapper.createObjectNode();
				
		responseNode.put("code", HttpStatus.OK.toString());
		responseNode.put("message", "Eliminazione Cliente " + CodiceCliente + " Eseguita Con Successo");
				
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
