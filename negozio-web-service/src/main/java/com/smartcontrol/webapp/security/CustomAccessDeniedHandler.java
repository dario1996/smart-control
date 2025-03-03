package com.smartcontrol.webapp.security;


import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.SneakyThrows;
import lombok.extern.java.Log;

@Component
@Log
public class CustomAccessDeniedHandler implements AccessDeniedHandler
{

	@Override
	@SneakyThrows
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) 
	{
		 ObjectMapper objectMapper = new ObjectMapper();
		 
		 String errMsg = "Priviligi Insufficenti. Impossibile Proseguire.";
			
		 HttpStatus httpStatus = HttpStatus.FORBIDDEN; // 403
		 response.setStatus(httpStatus.value());
		 
		 log.warning(errMsg);
		
		 //Map<String, Object> data = new HashMap<>();
		 
		 response
	         .getOutputStream()
	         .println(objectMapper.writeValueAsString(errMsg));
		
	}

}
