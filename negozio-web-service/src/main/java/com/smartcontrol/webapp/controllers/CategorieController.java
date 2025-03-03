package com.smartcontrol.webapp.controllers;

import java.util.List;
 
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartcontrol.webapp.dtos.CategoriaDto;
import com.smartcontrol.webapp.exceptions.NotFoundException;
import com.smartcontrol.webapp.services.CategorieService;

import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping("api/cat")
public class CategorieController 
{
	private CategorieService categorieService;
	
	public CategorieController(CategorieService categorieService)
	{
		this.categorieService = categorieService;
	}
	
	@SneakyThrows
	@GetMapping(produces = "application/json")
	public ResponseEntity<List<CategoriaDto>> GetCat()
	{
		log.info("****** Otteniamo le Categorie  *******");
		
		List<CategoriaDto> categorie = categorieService.SelTutti();
		
		if (categorie.isEmpty())
		{
			String ErrMsg = String.format("Nessun elemento esistente!");
			
			log.warning(ErrMsg);
			
			throw new NotFoundException(ErrMsg);
			 
		}
		 
		return new ResponseEntity<List<CategoriaDto>>(categorie, HttpStatus.OK);
		
	}
	
}
