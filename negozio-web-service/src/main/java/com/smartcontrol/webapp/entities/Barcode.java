package com.smartcontrol.webapp.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.smartcontrol.webapp.validations.EanConstraint;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "BARCODE")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Barcode 
{
	@Id
	@Column(name = "BARCODE")
	@NotNull(message = "{NotNull.Barcode.barcode.Validation}")
	@EanConstraint
	private String barcode;
	
	@Column(name = "IDTIPOART")
	@NotNull(message = "{NotNull.Barcode.idTipoArt.Validation}")
	@NotBlank(message = "{NotBlank.Barcode.idTipoArt.Validation}")
	private String idTipoArt;
	
	@ManyToOne
	@JoinColumn(name = "codart", referencedColumnName = "codart")
	@JsonBackReference
	private Articoli articolo;
}
