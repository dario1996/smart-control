package com.xantrix.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.xantrix.webapp.models.Negozi;

public interface NegoziRepository extends JpaRepository<Negozi, String>
{
	@Query("SELECT n FROM Negozi n WHERE n.utente.id = :username")
	List<Negozi> findByUsername(@Param("username") String username);
}
