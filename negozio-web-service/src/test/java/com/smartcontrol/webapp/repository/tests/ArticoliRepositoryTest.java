package com.smartcontrol.webapp.repository.tests;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
 
import com.smartcontrol.webapp.entities.Articoli;
import com.smartcontrol.webapp.entities.Barcode;
import com.smartcontrol.webapp.entities.FamAssort;
import com.smartcontrol.webapp.entities.Ingredienti;
import com.smartcontrol.webapp.entities.Iva;
import com.smartcontrol.webapp.repository.ArticoliRepository;


@SpringBootTest()
@TestMethodOrder(OrderAnnotation.class)
public class ArticoliRepositoryTest
{
	
	@Autowired
	private ArticoliRepository articoliRepository;
	
	@Test
	@Order(1)
	public void TestInsArticolo()
	{
		Date date = new Date();
		
		//CLASSE ENTITY ARTICOLI
		Articoli articolo = new Articoli("123Test","Articolo di Test","PZ","TEST",6,1.75,"1",
				date,null,null,null,null);
		
		//CLASSE ENTITY FAMASSORT
		FamAssort famAssort = new FamAssort();
		famAssort.setId(-1);
		articolo.setFamAssort(famAssort);
		
		//CLASSE ENTITY BARCODE
		Set<Barcode> EAN = new HashSet<>();
		EAN.add(new Barcode("12345678", "CP", articolo));
		articolo.setBarcode(EAN);
		
		//CLASSE ENTITY IVA
		Iva iva = new Iva();
		iva.setIdIva(22);
		articolo.setIva(iva);
		
		//CLASSE ENTITY INGREDIENTI
		Ingredienti ingredienti = new Ingredienti();
		ingredienti.setCodArt("123Test");
		ingredienti.setInfo("Test inserimento ingredienti");
		
		articoliRepository.save(articolo);
		
		assertThat(articoliRepository.findByCodArt("123Test"))
			.extracting(Articoli::getDescrizione)
			.isEqualTo("Articolo di Test");
	}

	@Test
	@Order(2)
	public void TestFindByCodStat()
	{
		assertThat(articoliRepository.findByCodStatOrderByDescrizione("TEST"))
		.extracting(Articoli::getDescrizione)
		.contains("Articolo di Test");	 
	}
	
	
	@Test
	@Order(3)
	public void TestSelByIdCat()
	{
		List<Articoli> items = articoliRepository.selByIdCat("-1",PageRequest.of(0, 10));
		assertTrue(items.size()>0);
		assertEquals(10, items.size());
	}
		
	@Test
	@Order(4)
	public void TestFindByCodArt() throws Exception
	{
		assertThat(articoliRepository.findByCodArt("123Test"))
				.extracting(Articoli::getDescrizione)
				.isEqualTo("Articolo di Test");
				
	}
	
	@Test
	@Order(5)
	public void TestSelByEan() throws Exception
	{
		assertThat(articoliRepository.selByEan("12345678"))
			.extracting(Articoli::getDescrizione)
			.isEqualTo("Articolo di Test");
				
	}
	
	@Test
	@Order(6)
	public void TestFindByDescrizioneLike()
	{
		assertThat(articoliRepository.selByDescrizioneLike("Articolo di Test%"))
		.extracting(Articoli::getDescrizione)
		.contains("Articolo di Test");	 
	}
	
	
	@Test
	@Order(7)
	//@Disabled
	public void TestDelArt() throws Exception
	{
		
		articoliRepository.delete(articoliRepository.findByCodArt("123Test"));
		
		assertThat(articoliRepository.findByCodArt("123Test")).isNull();
				
	}
}