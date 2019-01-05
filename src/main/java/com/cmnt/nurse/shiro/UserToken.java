package com.cmnt.nurse.shiro;

import org.apache.shiro.authc.UsernamePasswordToken;

public class UserToken extends UsernamePasswordToken {

	private static final long serialVersionUID = -4728953497558200443L;

	public UserToken(final String username, final String password) {
		super(username, password);
	}
}