package com.smartcontrol.webapp.services;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.smartcontrol.webapp.dtos.ServiziDto;
import com.smartcontrol.webapp.entities.Servizi;
import com.smartcontrol.webapp.repository.ServiziRepository;

@Service
public class ServiziServiceImpl implements ServiziService
{
	
	private ModelMapper modelMapper;
	private ServiziRepository serviziRepository;
	
	public ServiziServiceImpl(ServiziRepository serviziRepository,
			 ModelMapper modelMapper) 
	{
		this.serviziRepository = serviziRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public List<ServiziDto> SelTutti() 
	{
		List<Servizi> servizi =  serviziRepository.findAll();
		
		List<ServiziDto> retVal = servizi
		        .stream()
		        .map(source -> modelMapper.map(source, ServiziDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}

	@Override
	public List<ServiziDto> SelByNegozio(String shopId) 
	{
		List<Servizi> servizi =  serviziRepository.findByShopId(shopId);
		
		List<ServiziDto> retVal = servizi
		        .stream()
		        .map(source -> modelMapper.map(source, ServiziDto.class))
		        .collect(Collectors.toList());
		
		return retVal;
	}

	@Override
	public ServiziDto SelByServiceCode(String serviceCode) 
	{
		return ConvertToDto(serviziRepository.findByServiceCode(serviceCode));
	}

	@Override
	public Servizi SelByServiceCode2(String serviceCode) 
	{
		return serviziRepository.findByServiceCode(serviceCode);
	}

	@Override
	public void InsServizio(Servizi servizio) 
	{
		if(servizio.getServiceCode() == null) 
		{
			servizio.setServiceCode(generaCodiceServizio(servizio.getServiceName()));
		}
		
		serviziRepository.save(servizio);
	}

	@Override
	public void DelServizio(Servizi servizio) 
	{
		serviziRepository.delete(servizio);
	}

	private String generaCodiceServizio(String serviceName) {
	    String primeTreNome = (serviceName != null && serviceName.length() >= 3) ? 
	                          serviceName.substring(0, 3).toUpperCase() : "XXX";
	    String timestamp = String.valueOf(System.currentTimeMillis());

	    return "SER" + primeTreNome + timestamp;
	}
	
	private ServiziDto ConvertToDto(Servizi servizio)
	{
		ServiziDto serviziDto = null;
		
		if (servizio != null)
		{
			serviziDto =  modelMapper.map(servizio, ServiziDto.class);
		}
		
		return serviziDto;
	}

}
