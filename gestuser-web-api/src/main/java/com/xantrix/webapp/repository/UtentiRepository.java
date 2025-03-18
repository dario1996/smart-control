package com.xantrix.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xantrix.webapp.models.Negozi;
import com.xantrix.webapp.models.Utenti;
 
public interface UtentiRepository extends JpaRepository<Utenti, String> 
{
	boolean existsByUsername(String Username);
	
	public Utenti findByUsername(String Username);
	
	@Query("SELECT n FROM Utenti n WHERE n.username = :username AND n.email = :email")
	public Utenti findByUsernameAndEmail(@Param("username") String username, @Param("email") String email);
}
