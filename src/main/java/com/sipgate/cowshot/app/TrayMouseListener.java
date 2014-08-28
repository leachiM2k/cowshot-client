package com.nkuh.cowshot.app;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import com.nkuh.cowshot.api.CowshotAPI;

public class TrayMouseListener implements MouseListener
{
	private CowshotAPI api;
	
	public TrayMouseListener(CowshotAPI api)
	{
		this.api = api;
	}
	
	public void mouseClicked(MouseEvent arg0)
	{
		try
		{
			File file = File.createTempFile("nkuhshot", ".png");
			String tmpfile = file.getAbsolutePath();
			Process p = Runtime.getRuntime().exec("import " + tmpfile);
			p.waitFor();
			//openUrl("file://" + tmpfile);
			String token = api.getToken();
			api.putFile(tmpfile, token);
			openUrl(api.getPageUrlForToken(token));
			file.delete();
		}
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (URISyntaxException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (InterruptedException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (Throwable e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected void openUrl(String url) throws IOException, URISyntaxException
	{
		if (java.awt.Desktop.isDesktopSupported())
		{
			java.awt.Desktop desktop = java.awt.Desktop.getDesktop();

			if (desktop.isSupported(java.awt.Desktop.Action.BROWSE))
			{
				java.net.URI uri = new java.net.URI(url);
				desktop.browse(uri);
			}
		}
	}

	public void mouseEntered(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public void mouseExited(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public void mousePressed(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

	public void mouseReleased(MouseEvent arg0)
	{
		// TODO Auto-generated method stub

	}

}
