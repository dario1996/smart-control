package com.xantrix.webapp.models;

import com.fasterxml.jackson.annotation.JsonBackReference;

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
@Table(name = "NEGOZI")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Negozi {

	@Id
	@Column(name = "shopid")
	private String shopId;
	
	@Column(name = "shopname")
	private String shopName;
	
	@Column(name = "shopcity")
	private String shopCity;
	
	@Column(name = "shopaddress")
	private String shopAddress;
	
	@ManyToOne
	@JoinColumn(name = "username", referencedColumnName = "username")
	@JsonBackReference
	private Utenti utente;
}
