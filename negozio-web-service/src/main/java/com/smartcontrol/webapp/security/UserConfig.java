package com.smartcontrol.webapp.security;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Component 
@ConfigurationProperties("gestuser")
@Getter
@Setter
public class UserConfig 
{
	private String srvUrl;
	private String userId;
	private String password;
}
