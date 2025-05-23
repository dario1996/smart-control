package com.smartcontrol.webapp.entities;

import java.util.HashSet;
import java.util.Set;

import com.smartcontrol.webapp.validations.IvaConstraint;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "IVA")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Iva 
{
	@Id
	@Column(name = "IDIVA")
	@NotNull(message = "{NotNull.Iva.idIva.Validation}")
	@IvaConstraint
	private int idIva;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	@Column(name = "ALIQUOTA")
	private int aliquota;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "iva")
	private Set<Articoli> articoli = new HashSet<>();
	
}
