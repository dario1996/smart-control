package com.smartcontrol.webapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.smartcontrol.webapp.entities.FamAssort;

public interface CategoriaRepository extends JpaRepository<FamAssort, Integer>
{

}
