package com.smartcontrol.webapp.dtos;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientiDto {

	private String codiceCliente;
	private String nome;
	private String cognome;	
	private String cellulare;
	private String email;
	private Date dataNascita;
}
