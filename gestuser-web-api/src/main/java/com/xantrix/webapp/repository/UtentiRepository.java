package com.xantrix.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.xantrix.webapp.models.Utenti;
 
public interface UtentiRepository extends JpaRepository<Utenti, String> 
{
	boolean existsByUsername(String Username);
	
	public Utenti findByUsername(String Username);
}
