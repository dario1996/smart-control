package com.smartcontrol.webapp;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.spi.MappingContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.smartcontrol.webapp.dtos.ArticoliDto;
import com.smartcontrol.webapp.dtos.BarcodeDto;
import com.smartcontrol.webapp.entities.Articoli;
import com.smartcontrol.webapp.entities.Barcode;

@Configuration
public class ModelMapperConfig 
{
    @Bean
    ModelMapper modelMapper()
    {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.addMappings(articoliMapping);

        modelMapper.addMappings(new PropertyMap<Barcode, BarcodeDto>()
        {
            @Override
            protected void configure()
            {
                map().setIdTipoArt(source.getIdTipoArt());
            }
        });

        modelMapper.addConverter(articoliConverter);

        return modelMapper;
    }
	
	PropertyMap<Articoli, ArticoliDto> articoliMapping = new PropertyMap<Articoli,ArticoliDto>() 
	{
	      protected void configure() 
	      {
	         map().setData(source.getDataCreaz());
	         map().setStatus(source.getIdStatoArt());
	      }
	};
	
	Converter<String, String> articoliConverter = new Converter<String, String>() 
	{
		  @Override
		  public String convert(MappingContext<String, String> context) 
		  {
			  return context.getSource() == null ? "" : context.getSource().trim();
		  }
	};
}
