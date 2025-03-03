package com.smartcontrol.webapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontrol.webapp.entities.Servizi;

public interface ServiziRepository extends JpaRepository<Servizi, String>
{
	//Query Method
	Servizi findByServiceCode(String serviceCode);
	
	//Query Method
	List<Servizi> findByShopId(String shopId);
}
