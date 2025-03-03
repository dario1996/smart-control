package com.smartcontrol.webapp.services;

import java.util.List;

import com.smartcontrol.webapp.dtos.DipendentiDto;
import com.smartcontrol.webapp.entities.Articoli;
import com.smartcontrol.webapp.entities.Dipendenti;


public interface DipendentiService 
{
	public List<DipendentiDto> SelTutti();
	
	public List<DipendentiDto> SelByNegozio(String shopId);
	
	public DipendentiDto SelByCodiceDipendente(String codiceDipendente);
	
	public Dipendenti SelByCodiceDipendente2(String codiceDipendente);

	public void InsDipendente(Dipendenti dipendente);
	
	public void DelDipendente(Dipendenti dipendente);
}
