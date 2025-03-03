package com.smartcontrol.webapp.services;

import java.util.List;

import com.smartcontrol.webapp.dtos.ClientiDto;
import com.smartcontrol.webapp.entities.Clienti;

public interface ClientiService 
{
	public List<ClientiDto> SelTutti();
	
	public List<ClientiDto> SelByNegozio(String shopId);
	
	public ClientiDto SelBycodiceCliente(String codiceCliente);
	
	public Clienti SelBycodiceCliente2(String codiceCliente);

	public void InsCliente(Clienti cliente);
	
	public void DelCliente(Clienti cliente);
}
