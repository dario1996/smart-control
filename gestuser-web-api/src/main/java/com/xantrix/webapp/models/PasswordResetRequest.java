package com.xantrix.webapp.models;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetRequest 
{
	private String username;
	private String email;
	private String password;
	private String newPassword;
	private String resetToken;
}
