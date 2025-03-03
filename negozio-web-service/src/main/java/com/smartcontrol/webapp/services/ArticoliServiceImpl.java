package com.smartcontrol.webapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smartcontrol.webapp.dtos.ArticoliDto;
import com.smartcontrol.webapp.entities.Articoli;
import com.smartcontrol.webapp.repository.ArticoliRepository;

@Service
public class ArticoliServiceImpl implements ArticoliService
{
	
	
	private ModelMapper modelMapper;
	private ArticoliRepository articoliRepository;
	
	public ArticoliServiceImpl(
			ArticoliRepository articoliRepository,
			ModelMapper modelMapper) 
	{
		this.articoliRepository = articoliRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public ArticoliDto SelByBarcode(String barcode) 
	{
		return ConvertToDto(articoliRepository.selByEan(barcode));
	}

	@Override
	public ArticoliDto SelByCodArt(String codart) 
	{
		return ConvertToDto(articoliRepository.findByCodArt(codart));
	}
	
	@Override
	public Articoli SelByCodArt2(String codart) 
	{
		return articoliRepository.findByCodArt(codart);
	}

	@Override
	public List<ArticoliDto> SelByDescrizione(String descrizione) 
	{
		String filter = "%" + descrizione.toUpperCase() + "%";
		
		return ConvertToDto(articoliRepository.selByDescrizioneLike(filter));
	}
	
	@Override
	public void DelArticolo(Articoli articolo) 
	{
		articoliRepository.delete(articolo);
	}

	@Override
	public void InsArticolo(Articoli articolo) 
	{
		articolo.setDescrizione(articolo.getDescrizione().toUpperCase());
		
		articoliRepository.save(articolo);
	}
	
	private ArticoliDto ConvertToDto(Articoli articoli)
	{
		ArticoliDto articoliDto = null;
		
		if (articoli != null)
		{
			articoliDto =  modelMapper.map(articoli, ArticoliDto.class);
		}
		
		return articoliDto;
	}
	
	private List<ArticoliDto> ConvertToDto(List<Articoli> articoli)
	{
		List<ArticoliDto> retVal = articoli
		        .stream()
		        .map(source -> modelMapper.map(source, ArticoliDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}

}
