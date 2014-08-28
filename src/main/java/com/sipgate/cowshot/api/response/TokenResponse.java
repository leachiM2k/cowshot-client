package com.nkuh.cowshot.api.response;

public class TokenResponse
{
	private String token;
	
	public TokenResponse()
	{
	}
	
	public TokenResponse(String token)
	{
		setToken(token);
	}

	public String getToken()
	{
		return token;
	}

	public void setToken(String token)
	{
		this.token = token;
	}
}
