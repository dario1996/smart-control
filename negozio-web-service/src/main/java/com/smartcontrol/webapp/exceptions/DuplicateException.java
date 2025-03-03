package com.smartcontrol.webapp.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DuplicateException extends Exception
{

	private static final long serialVersionUID = 2858641584997014510L;
	
	private String messaggio;
	
	public DuplicateException()
	{
		super();
	}
	
	public DuplicateException(String messaggio)
	{
		super(messaggio);
		this.messaggio = messaggio;
	}
}
