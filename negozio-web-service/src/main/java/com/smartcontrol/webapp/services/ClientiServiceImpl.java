package com.smartcontrol.webapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smartcontrol.webapp.dtos.ClientiDto;
import com.smartcontrol.webapp.entities.Clienti;
import com.smartcontrol.webapp.repository.ClientiRepository;

@Service
public class ClientiServiceImpl implements ClientiService
{
	
	private ModelMapper modelMapper;
	private ClientiRepository clientiRepository;
	
	public ClientiServiceImpl(ClientiRepository clientiRepository,
			 ModelMapper modelMapper) 
	{
		this.clientiRepository = clientiRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public List<ClientiDto> SelTutti() 
	{
		List<Clienti> clienti =  clientiRepository.findAll();
		
		List<ClientiDto> retVal = clienti
		        .stream()
		        .map(source -> modelMapper.map(source, ClientiDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}

	@Override
	public List<ClientiDto> SelByNegozio(String shopId) 
	{
		List<Clienti> clienti =  clientiRepository.findByShopId(shopId);
		
		List<ClientiDto> retVal = clienti
		        .stream()
		        .map(source -> modelMapper.map(source, ClientiDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}

	@Override
	public ClientiDto SelBycodiceCliente(String codiceCliente) 
	{
		return ConvertToDto(clientiRepository.findBycodiceCliente(codiceCliente));
	}

	@Override
	public Clienti SelBycodiceCliente2(String codiceCliente) 
	{
		return clientiRepository.findBycodiceCliente(codiceCliente);
	}

	@Override
	public void InsCliente(Clienti cliente) 
	{
		if(cliente.getCodiceCliente() == null) 
		{
			cliente.setCodiceCliente(generaCodiceCliente(cliente.getNome(), cliente.getCognome()));
		}
		
		clientiRepository.save(cliente);
	}

	@Override
	public void DelCliente(Clienti cliente) 
	{
		clientiRepository.delete(cliente);
	}
	
	// Metodo per generare il codice dipendente
	private String generaCodiceCliente(String nome, String cognome) 
	{
		String primeTreNome = (nome != null && nome.length() >= 3) ? nome.substring(0, 3).toUpperCase() : "XXX";
		String primeTreCognome = (cognome != null && cognome.length() >= 3) ? cognome.substring(0, 3).toUpperCase() : "YYY";
		String timestamp = String.valueOf(System.currentTimeMillis());  // Evita duplicati

		return primeTreNome + primeTreCognome + timestamp;
	}
	
	private ClientiDto ConvertToDto(Clienti clienti)
	{
		ClientiDto clientiDto = null;
		
		if (clienti != null)
		{
			clientiDto =  modelMapper.map(clienti, ClientiDto.class);
		}
		
		return clientiDto;
	}

}
