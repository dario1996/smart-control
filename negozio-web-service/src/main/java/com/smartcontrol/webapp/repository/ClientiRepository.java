package com.smartcontrol.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontrol.webapp.entities.Clienti;

public interface ClientiRepository extends JpaRepository<Clienti, String>
{
	//Query Method
	Clienti findBycodiceCliente(String codiceCliente);
		
	//Query Method
	List<Clienti> findByShopId(String shopId);
}
