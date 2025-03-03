package com.smartcontrol.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontrol.webapp.entities.Dipendenti;

public interface DipendentiRepository extends JpaRepository<Dipendenti, String> 
{
	//Query Method
	Dipendenti findBycodiceDipendente(String codiceDipendente);
	
	//Query Method
	List<Dipendenti> findByShopId(String shopId);
}
