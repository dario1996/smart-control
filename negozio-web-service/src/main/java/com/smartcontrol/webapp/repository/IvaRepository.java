package com.smartcontrol.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontrol.webapp.entities.Iva;

public interface IvaRepository extends JpaRepository<Iva, Integer> 
{
	Iva findByIdIva(int idIva);
}
