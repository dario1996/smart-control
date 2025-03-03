package com.smartcontrol.webapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smartcontrol.webapp.dtos.ArticoliDto;
import com.smartcontrol.webapp.dtos.DipendentiDto;
import com.smartcontrol.webapp.entities.Articoli;
import com.smartcontrol.webapp.entities.Dipendenti;
import com.smartcontrol.webapp.repository.DipendentiRepository;

@Service
public class DipendentiServiceImpl implements DipendentiService
{
	private ModelMapper modelMapper;
	private DipendentiRepository dipendentiRepository;
	
	public DipendentiServiceImpl(DipendentiRepository dipendentiRepository,
			 ModelMapper modelMapper) 
	{
		this.dipendentiRepository = dipendentiRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public List<DipendentiDto> SelTutti() 
	{
		List<Dipendenti> dipendenti =  dipendentiRepository.findAll();
		
		List<DipendentiDto> retVal = dipendenti
		        .stream()
		        .map(source -> modelMapper.map(source, DipendentiDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}
	
	@Override
	public List<DipendentiDto> SelByNegozio(String shopId) {
        List<Dipendenti> dipendenti =  dipendentiRepository.findByShopId(shopId);
		
		List<DipendentiDto> retVal = dipendenti
		        .stream()
		        .map(source -> modelMapper.map(source, DipendentiDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}

	@Override
	public DipendentiDto SelByCodiceDipendente(String codiceDipendente) 
	{
		return ConvertToDto(dipendentiRepository.findBycodiceDipendente(codiceDipendente));
	}
	
	@Override
	public Dipendenti SelByCodiceDipendente2(String codiceDipendente) 
	{
		return dipendentiRepository.findBycodiceDipendente(codiceDipendente);
	}
	
	@Override
	public void DelDipendente(Dipendenti dipendente) 
	{
		dipendentiRepository.delete(dipendente);
	}
	
	private DipendentiDto ConvertToDto(Dipendenti dipendenti)
	{
		DipendentiDto dipendentiDto = null;
		
		if (dipendenti != null)
		{
			dipendentiDto =  modelMapper.map(dipendenti, DipendentiDto.class);
		}
		
		return dipendentiDto;
	}

	@Override
	public void InsDipendente(Dipendenti dipendente) 
	{
		if(dipendente.getCodiceDipendente() == null) 
		{
			dipendente.setCodiceDipendente(generaCodiceDipendente(dipendente.getNome(), dipendente.getCognome()));
		}
		
		dipendentiRepository.save(dipendente);
	}
	
	// Metodo per generare il codice dipendente
	private String generaCodiceDipendente(String nome, String cognome) 
	{
	    String primeTreNome = (nome != null && nome.length() >= 3) ? nome.substring(0, 3).toUpperCase() : "XXX";
	    String primeTreCognome = (cognome != null && cognome.length() >= 3) ? cognome.substring(0, 3).toUpperCase() : "YYY";
	    String timestamp = String.valueOf(System.currentTimeMillis());  // Evita duplicati

	    return primeTreNome + primeTreCognome + timestamp;
	}
	
	/*private List<DipendentiDto> ConvertToDto(List<Dipendenti> dipendenti)
	{
		List<DipendentiDto> retVal = dipendenti
		        .stream()
		        .map(source -> modelMapper.map(source, DipendentiDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}*/
}
