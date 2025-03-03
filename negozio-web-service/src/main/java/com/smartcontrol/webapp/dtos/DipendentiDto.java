package com.smartcontrol.webapp.dtos;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DipendentiDto 
{
	private String codiceDipendente;
	private String nome;
	private String cognome;	
	private String cellulare;
	private String email;
	private String colore;
	private Date dataNascita;
	private Date dataAssunzione;
	
	/*private Set<BarcodeDto> barcode = new HashSet<>();
	private IngredientiDto ingredienti;
	private CategoriaDto famAssort;
	private IvaDto iva;*/
}
