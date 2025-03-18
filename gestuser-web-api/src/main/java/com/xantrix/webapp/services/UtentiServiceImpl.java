package com.xantrix.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.xantrix.webapp.models.Utenti;
import com.xantrix.webapp.repository.UtentiRepository;
 
@Service
//@Transactional(readOnly = true)
public class UtentiServiceImpl implements UtentiService
{

	@Autowired
	UtentiRepository utentiRepository;
	
	@Override
	public List<Utenti> SelTutti()
	{
		return utentiRepository.findAll();
	}

	@Override
	public Utenti SelUser(String username)
	{
		return utentiRepository.findByUsername(username);
	}


	@Override
	public void Save(Utenti utente)
	{
		utentiRepository.save(utente);
	}

	@Override
	public void Delete(Utenti utente)
	{
		utentiRepository.delete(utente);
	}

	@Override
	public boolean CheckExistUsername(String Username) 
	{
		return utentiRepository.existsByUsername(Username);
	}

	@Override
	public Utenti SelUserByUsernameAndPassword(String Username, String Email) 
	{
		return utentiRepository.findByUsernameAndEmail(Username, Email);
	}

}
