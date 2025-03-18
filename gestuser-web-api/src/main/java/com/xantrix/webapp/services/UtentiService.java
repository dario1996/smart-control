package com.xantrix.webapp.services;

import java.util.List;

import com.xantrix.webapp.models.Utenti;

public interface UtentiService
{
	public List<Utenti> SelTutti();
	
	public Utenti SelUser(String Username);
	
	public void Save(Utenti utente);
	
	public void Delete(Utenti utente);
	
	public boolean CheckExistUsername(String Username);
	
	public Utenti SelUserByUsernameAndPassword(String Username, String Email);
	
}
