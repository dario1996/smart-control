package com.smartcontrol.webapp.controller.tests;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.IOException;

import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

import com.smartcontrol.webapp.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


@ContextConfiguration(classes = Application.class)
@SpringBootTest
@TestMethodOrder(OrderAnnotation.class)
public class SelectArtTest
{
	private MockMvc mockMvc;
		
	@Autowired
	private WebApplicationContext wac;
	
	@BeforeEach
	public void setup() throws JSONException, IOException
	{
		mockMvc = MockMvcBuilders
				.webAppContextSetup(wac)
				.build();	
	}
	
	String JsonData =  
			"{\n" + 
			"    \"codart\": \"002000301\",\n" + 
			"    \"descrizione\": \"ACQUA ULIVETO 15 LT\",\n" + 
			"    \"um\": \"PZ\",\n" + 
			"    \"codstat\": \"\",\n" + 
			"    \"pzcart\": 6,\n" + 
			"    \"peso\": 1.5,\n" + 
			"    \"status\": \"1\",\n" + 
			"    \"data\": \"2010-06-14\",\n" + 
			"    \"barcode\": [\n" + 
			"        {\n" + 
			"            \"barcode\": \"8008490000021\",\n" + 
			"            \"idTipoArt\": \"CP\"\n" + 
			"        }\n" + 
			"    ],\n" + 
			"    \"famAssort\": {\n" + 
			"        \"id\": 1,\n" + 
			"        \"descrizione\": \"DROGHERIA ALIMENTARE\"\n" + 
			"    },\n" + 
			"    \"ingredienti\": null,\n" + 
			"    \"iva\": {\n" + 
			"        \"idIva\": 22,\n" + 
			"        \"descrizione\": \"IVA RIVENDITA 22%\",\n" + 
			"        \"aliquota\": 22\n" + 
			"    }\n" + 
			"}";
	
	@Test
	@Order(1)
	public void listArtByEan() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/articoli/cerca/barcode/8008490000021")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				 //articoli
				.andExpect(jsonPath("$.codart").exists())
				.andExpect(jsonPath("$.codart").value("002000301"))
				.andExpect(jsonPath("$.descrizione").exists())
				.andExpect(jsonPath("$.descrizione").value("ACQUA ULIVETO 15 LT"))
				.andExpect(jsonPath("$.um").exists())
				.andExpect(jsonPath("$.um").value("PZ"))
				.andExpect(jsonPath("$.codstat").exists())
				.andExpect(jsonPath("$.codstat").value(""))
				.andExpect(jsonPath("$.pzcart").exists())
				.andExpect(jsonPath("$.pzcart").value("6"))
				.andExpect(jsonPath("$.peso").exists())
				.andExpect(jsonPath("$.peso").value("1.5"))
				.andExpect(jsonPath("$.status").exists())
				.andExpect(jsonPath("$.status").value("1"))
				.andExpect(jsonPath("$.data").exists())
				.andExpect(jsonPath("$.data").value("2010-06-14"))
				 //barcode
				.andExpect(jsonPath("$.barcode[0].barcode").exists())
				.andExpect(jsonPath("$.barcode[0].barcode").value("8008490000021")) 
				.andExpect(jsonPath("$.barcode[0].idTipoArt").exists())
				.andExpect(jsonPath("$.barcode[0].idTipoArt").value("CP")) 
				 //famAssort
				.andExpect(jsonPath("$.famAssort.id").exists())
				.andExpect(jsonPath("$.famAssort.id").value("1")) 
				.andExpect(jsonPath("$.famAssort.descrizione").exists())
				.andExpect(jsonPath("$.famAssort.descrizione").value("DROGHERIA ALIMENTARE")) 
				 //ingredienti
				.andExpect(jsonPath("$.ingredienti").isEmpty())
				 //Iva
				.andExpect(jsonPath("$.iva.idIva").exists())
				.andExpect(jsonPath("$.iva.idIva").value("22")) 
				.andExpect(jsonPath("$.iva.descrizione").exists())
				.andExpect(jsonPath("$.iva.descrizione").value("IVA RIVENDITA 22%"))
				.andExpect(jsonPath("$.iva.aliquota").exists())
				.andExpect(jsonPath("$.iva.aliquota").value("22"))	
				
				.andDo(print());
	}
	
	private String Barcode = "8008490002138";
	
	@Test
	@Order(2)
	public void errlistArtByEan() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/articoli/cerca/barcode/" + Barcode)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(404))
				.andExpect(jsonPath("$.message").value("Il barcode " + Barcode + " non e' stato trovato!"))
				.andDo(print());
	}
	
	@Test
	@Order(3)
	public void listArtByCodArt() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/articoli/cerca/codice/002000301")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(JsonData)) 
				.andReturn();
	}
	
	private String CodArt = "002000301b";
	
	@Test
	@Order(4)
	public void errlistArtByCodArt() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/articoli/cerca/codice/" + CodArt)
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(404))
				.andExpect(jsonPath("$.message").value("L'articolo con codice " + CodArt + " non e' stato trovato!"))
				.andDo(print());
	}
	
	private String JsonData2 = "[" + JsonData + "]";

	@Test
	@Order(5)
	public void listArtByDesc() throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/articoli/cerca/descrizione/ACQUA ULIVETO 15")
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$", hasSize(1)))
				.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
				.andExpect(content().json(JsonData2)) 
				.andReturn();
	}
	
	@Test
	@Order(6)
	public void errlistArtByDesc()  throws Exception
	{
		mockMvc.perform(MockMvcRequestBuilders.get("/api/articoli/cerca/descrizione/ABC1234")
				.contentType(MediaType.APPLICATION_JSON)
				.content(JsonData)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.code").value(404))
				.andExpect(jsonPath("$.message").value("Non e' stato trovato alcun articolo avente descrizione ABC1234"))
				.andDo(print());
	}
}
