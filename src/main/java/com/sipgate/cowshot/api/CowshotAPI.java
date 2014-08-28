package com.nkuh.cowshot.api;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.googlecode.jsonrpc4j.JsonRpcHttpClient;
import com.nkuh.cowshot.api.response.TokenResponse;

public class CowshotAPI
{
	private String apiUrl = "http://SERVER/cowshot/api.php";
	private String pageUrl = "http://SERVER/cowshot/";
	
	public String getToken() throws Throwable
	{
		JsonRpcHttpClient client = new JsonRpcHttpClient(new URL(apiUrl));
		TokenResponse token = (TokenResponse) client.invoke("getToken", null, TokenResponse.class);
		return token.getToken();
	}

	public void putFile(String filename, String token)
	{
		File file = new File(filename);
		InputStreamReader inputStreamReader = null;
		BufferedOutputStream bos = null;
		BufferedInputStream bis = null;

		try
		{
			URL url = new URL(apiUrl + "?token=" + token);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("PUT");
	
			bos = new BufferedOutputStream(urlConnection.getOutputStream());
			bis = new BufferedInputStream(new FileInputStream(file));
	
			int i;
			// read byte by byte until end of stream
			while ((i = bis.read()) >= 0)
			{
				bos.write(i);
			}
			bos.flush();
	
			inputStreamReader = new InputStreamReader(urlConnection.getInputStream());
			inputStreamReader.read();
	
		}
		catch (IOException iox)
		{
			System.err.println("HTTP PUT failed with: " + iox.getMessage());
		}
		finally
		{
			try
			{
				if (bos != null)
				{
					bos.close();
				}
			}
			catch (IOException iox)
			{
				System.err.println("Error while closing BufferedOutputStream to " + apiUrl);
			}
			
			try
			{
				if (bis != null)
				{
					bis.close();
				}
			}
			catch (IOException iox)
			{
				System.err.println("Error while closing BufferedInputStream to " + apiUrl);
			}
			
			try
			{
				if (inputStreamReader != null)
				{
					inputStreamReader.close();
				}
			}
			catch (IOException iox)
			{
				System.err.println("Error while closing InputStream to " + apiUrl);
			}
		}
	}

	public String getPageUrl()
	{
		return pageUrl;
	}

	public void setPageUrl(String pageUrl)
	{
		this.pageUrl = pageUrl; 
	}
	
	public String getPageUrlForToken(String token)
	{
		return pageUrl + "?token=" + token;
	}
	
	public void setApiUrl(String apiUrl)
	{
		this.apiUrl = apiUrl; 
	}
}
