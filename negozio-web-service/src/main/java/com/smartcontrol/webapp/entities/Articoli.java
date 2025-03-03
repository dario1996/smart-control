package com.smartcontrol.webapp.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "ARTICOLI")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Articoli 
{
	@Id
	@Column(name = "codart")
	@Size(min = 5, max = 20, message = "{Size.Articoli.codArt.Validation}")
	private String codArt;
	
	@Column(name = "descrizione")
	@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String descrizione;	
	
	@Column(name = "um")
	@NotBlank(message = "{NotBlank.Articoli.um.Validation}")
	private String um;
	
	@Column(name = "codstat")
	private String codStat;
	
	@Column(name = "pzcart")
	@Max(value = 99, message = "{Max.Articoli.pzCart.Validation}")
	@Min(value = 1, message = "{Min.Articoli.pzCart.Validation}")
	private Integer pzCart;
	
	@Column(name = "pesonetto")
	@Max(value = 999, message = "{Max.Articoli.pesoNetto.Validation}")
	@DecimalMin(value = "0.01", message = "{Min.Articoli.pesoNetto.Validation}")
	private double pesoNetto;
	
	@Column(name = "idstatoart")
	@NotNull(message = "{NotNull.Articoli.idStatoArt.Validation}")
	private String idStatoArt;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datacreazione")
	@NotNull(message = "{NotNull.Articoli.dataCreaz.Validation}")
	private Date dataCreaz;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "articolo", orphanRemoval = true)
	@JsonManagedReference
	@Valid
	private Set<Barcode> barcode = new HashSet<>();
	
	@OneToOne(cascade = CascadeType.ALL, mappedBy = "articolo", orphanRemoval = true)
	private Ingredienti ingredienti;
	
	@ManyToOne
	@JoinColumn(name = "idiva",  referencedColumnName = "idiva")
	@Valid
	private Iva iva;
	
	@ManyToOne
	@JoinColumn(name = "idfamass", referencedColumnName = "id")
	@Valid
	private FamAssort famAssort;
	
}
