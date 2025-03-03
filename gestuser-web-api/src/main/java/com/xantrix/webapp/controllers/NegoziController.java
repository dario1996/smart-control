package com.xantrix.webapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.xantrix.webapp.models.Negozi;
import com.xantrix.webapp.services.NegoziService;

import lombok.extern.java.Log;

@Log
@RestController
@RequestMapping(value = "/api/negozi")
public class NegoziController 
{
	@Autowired
	NegoziService negoziService;
	
	@GetMapping("/{username}")
    public ResponseEntity<List<Negozi>> getNegoziPerUtente(@PathVariable String username) {
        List<Negozi> negozi = negoziService.SelByUsername(username);
        return ResponseEntity.ok(negozi);
    }
}
