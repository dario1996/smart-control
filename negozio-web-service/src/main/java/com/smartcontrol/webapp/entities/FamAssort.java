package com.smartcontrol.webapp.entities;

import java.util.HashSet;
import java.util.Set;

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
@Table(name = "FAMASSORT")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FamAssort 
{
	@Id
	@Column(name = "ID")
	@NotNull(message = "{NotNull.FamAssort.id.Validation}")
	private int id;
	
	@Column(name = "DESCRIZIONE")
	private String descrizione;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "famAssort")
	private Set<Articoli> articoli = new HashSet<>();
}
