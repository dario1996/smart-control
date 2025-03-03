package com.smartcontrol.webapp.services;

import java.util.List;

import com.smartcontrol.webapp.dtos.ArticoliDto;
import com.smartcontrol.webapp.entities.Articoli;


public interface ArticoliService 
{
	public ArticoliDto SelByBarcode(String barcode);
	
	public ArticoliDto SelByCodArt(String codart);
	
	public Articoli SelByCodArt2(String codart);
	
	public List<ArticoliDto> SelByDescrizione(String descrizione);
	
	public void DelArticolo(Articoli articolo);
	
	public void InsArticolo(Articoli articolo);
	
}
