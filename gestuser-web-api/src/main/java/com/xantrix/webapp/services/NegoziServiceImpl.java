package com.xantrix.webapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xantrix.webapp.models.Negozi;
import com.xantrix.webapp.repository.NegoziRepository;

@Service
public class NegoziServiceImpl implements NegoziService
{
	@Autowired
	NegoziRepository negoziRepository;

	@Override
	public List<Negozi> SelByUsername(String username) 
	{
		return negoziRepository.findByUsername(username);
	}

}
