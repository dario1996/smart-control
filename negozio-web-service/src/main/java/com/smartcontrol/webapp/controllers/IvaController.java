package com.smartcontrol.webapp.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontrol.webapp.dtos.IvaDto;
import com.smartcontrol.webapp.exceptions.NotFoundException;
import com.smartcontrol.webapp.services.IvaService;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/iva")
@CrossOrigin("http://localhost:4200")
public class IvaController 
{
	private IvaService ivaService;
	
	public IvaController(IvaService ivaService)
	{
		this.ivaService = ivaService;
	}
	
	@SneakyThrows
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<IvaDto>> GetIva()
	{
		log.info("****** Otteniamo le aliquote IVA  *******");
		
		List<IvaDto> iva = ivaService.SelTutti();
		
		if (iva.isEmpty())
		{
			String ErrMsg = String.format("Nessun elemento esistente!");
			
			log.warning(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
			 
		}
		 
		return new ResponseEntity<List<IvaDto>>(iva, HttpStatus.OK);
		
	}
}
