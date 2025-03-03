package com.smartcontrol.webapp.entities;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "DIPENDENTI")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Dipendenti {
	
	@Id
	@Column(name = "codicedipendente")
	private String codiceDipendente;
	
	@Column(name = "nome")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String nome;
	
	@Column(name = "cognome")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String cognome;
	
	@Column(name = "cellulare")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String cellulare;
	
	@Column(name = "email")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String email;
	
	@Column(name = "colore")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String colore;
	
	@Column(name = "shopid")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String shopId;
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datanascita")
	//@NotNull(message = "{NotNull.Articoli.dataCreaz.Validation}")
	private Date dataNascita = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "dataassunzione")
	//@NotNull(message = "{NotNull.Articoli.dataCreaz.Validation}")
	private Date dataAssunzione = new Date();
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datacreazione")
	//@NotNull(message = "{NotNull.Articoli.dataCreaz.Validation}")
	private Date dataCreaz = new Date();
	
	/*@ManyToOne
	@JoinColumn(name = "idnegozio",  referencedColumnName = "idnegozio")
	@Valid
	private Negozi negozio;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dipendente", orphanRemoval = true)
	@JsonManagedReference
	@Valid
	private Set<Appuntamenti> appuntamenti = new HashSet<>();*/
}
