package com.smartcontrol.webapp.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartcontrol.webapp.entities.Articoli;

public interface ArticoliRepository extends JpaRepository<Articoli, String> 
{
	//Query Method
	Articoli findByCodArt(String codArt);
	
	//Query Method
	List<Articoli> findByCodStatOrderByDescrizione(String codStat);
	
	//JPQL
	@Query(value="SELECT a FROM Articoli a JOIN a.famAssort b WHERE b.id = :idCat")
	List<Articoli> selByIdCat(@Param("idCat") String idCat, Pageable pageable);
	
	//JPQL
	@Query(value="SELECT a FROM Articoli a JOIN a.barcode b WHERE b.barcode IN (:ean)")
	Articoli selByEan(@Param("ean") String ean);
	
	//SQL
	@Query(value = "SELECT * FROM ARTICOLI WHERE DESCRIZIONE LIKE :desArt", nativeQuery = true)
	List<Articoli> selByDescrizioneLike(@Param("desArt") String descrizione);	
	
}
