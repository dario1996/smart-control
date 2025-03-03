package com.smartcontrol.webapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SERVIZI")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Servizi {
	@Id
	@Column(name = "servicecode")
	private String serviceCode;
	
	@Column(name = "servicename")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String serviceName;
	
	@Column(name = "serviceprice")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String servicePrice;
	
	@Column(name = "shopid")
	//@Size(min = 6, max = 80, message = "{Size.Articoli.descrizione.Validation}")
	//@NotBlank(message = "{NotBlank.Articoli.descrizione.Validation}")
	private String shopId;
}
