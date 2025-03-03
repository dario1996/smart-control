package com.smartcontrol.webapp.services;

import java.util.List;

import com.smartcontrol.webapp.dtos.ServiziDto;
import com.smartcontrol.webapp.entities.Servizi;

public interface ServiziService 
{
	public List<ServiziDto> SelTutti();
	
	public List<ServiziDto> SelByNegozio(String shopId);
	
	public ServiziDto SelByServiceCode(String serviceCode);
	
	public Servizi SelByServiceCode2(String serviceCode);

	public void InsServizio(Servizi servizio);
	
	public void DelServizio(Servizi servizio);
}
