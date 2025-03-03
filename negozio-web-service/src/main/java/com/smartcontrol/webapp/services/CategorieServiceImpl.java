package com.smartcontrol.webapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smartcontrol.webapp.dtos.CategoriaDto;
import com.smartcontrol.webapp.entities.FamAssort;

import com.smartcontrol.webapp.repository.CategoriaRepository;

@Service
public class CategorieServiceImpl implements CategorieService
{
	private ModelMapper modelMapper;
	private CategoriaRepository categoriaRepository;
	
	public CategorieServiceImpl(CategoriaRepository categoriaRepository,
			 ModelMapper modelMapper) 
	{
		this.categoriaRepository = categoriaRepository;
		this.modelMapper = modelMapper;
	}
	
	@Override
	public List<CategoriaDto> SelTutti() 
	{
		List<FamAssort> categorie =  categoriaRepository.findAll();
		
		List<CategoriaDto> retVal = categorie
		        .stream()
		        .map(source -> modelMapper.map(source, CategoriaDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}

}
