package com.xantrix.webapp.services;

import java.util.List;

import com.xantrix.webapp.models.Negozi;

public interface NegoziService 
{
	public List<Negozi> SelByUsername(String username);
}
