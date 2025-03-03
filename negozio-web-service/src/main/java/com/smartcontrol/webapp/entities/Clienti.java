package com.smartcontrol.webapp.entities;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CLIENTI")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Clienti {

	@Id
	@Column(name = "codicecliente")
	private String codiceCliente;
	
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
	
	@Temporal(TemporalType.DATE)
	@Column(name = "datanascita")
	//@NotNull(message = "{NotNull.Articoli.dataCreaz.Validation}")
	private Date dataNascita = new Date();
	
	@Column(name = "shopid")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String shopId;
}
