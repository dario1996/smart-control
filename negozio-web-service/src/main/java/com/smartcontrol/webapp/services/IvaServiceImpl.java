package com.smartcontrol.webapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


import com.smartcontrol.webapp.dtos.IvaDto;
import com.smartcontrol.webapp.entities.Iva;
import com.smartcontrol.webapp.repository.IvaRepository;


@Service
public class IvaServiceImpl implements IvaService
{
	private ModelMapper modelMapper;
	private IvaRepository ivaRepository;
	
	public IvaServiceImpl(IvaRepository ivaRepository,
			 ModelMapper modelMapper) 
	{
		this.ivaRepository = ivaRepository;
		this.modelMapper = modelMapper;
	}
	

	@Override
	public List<IvaDto> SelTutti() 
	{
		List<Iva> iva =  ivaRepository.findAll();
		
		List<IvaDto> retVal = iva
		        .stream()
		        .map(source -> modelMapper.map(source, IvaDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}

}
