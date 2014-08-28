package com.nkuh.cowshot.app;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.awt.event.ActionListener;
import java.net.URL;
import org.apache.commons.cli.*;

import com.nkuh.cowshot.api.CowshotAPI;

public class Application
{
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		if (SystemTray.isSupported())
		{
			Application application = new Application();
			application.parseCommandLine(args);
			application.start();
		}
		else
		{
			System.out.println("systemtray is not supported");
		}
		System.out.println("Started");
	}

	protected CowshotAPI api; 

	public Application()
	{
		api = new CowshotAPI();
	}

	public void start()
	{
		SystemTray tray = SystemTray.getSystemTray();
		URL iconUrl = this.getClass().getResource("/icon.gif");
		Image image = Toolkit.getDefaultToolkit().getImage(iconUrl);
		
		PopupMenu popup = new PopupMenu();
		MenuItem defaultItem = new MenuItem("Exit");
		MenuItem spanel = new MenuItem("Status");

		ActionListener exitListener = new ExitListener();
		defaultItem.addActionListener(exitListener);

		popup.add(spanel);
		popup.addSeparator();
		popup.add(defaultItem);
		TrayIcon trayIcon = new TrayIcon(image, "Nkuh Screenshot Tool", popup);
		trayIcon.setImageAutoSize(true);
		trayIcon.addActionListener(new TrayIconListener());
		trayIcon.addMouseListener(new TrayMouseListener(api));
		
		try
		{
			tray.add(trayIcon);
		}
		catch (AWTException e)
		{
			System.err.println("TrayIcon could not be added.");
		}
	}
	
	public void parseCommandLine(String[] args)
	{
		CommandLineParser parser = new GnuParser();
		CommandLine line = null;
		Options options = new Options();
		options.addOption("api", true, "api host");
		options.addOption("www", true, "web page");
		options.addOption("help", false, "print help page");

		try
		{
			line = parser.parse(options, args);
		}
		catch (ParseException e)
		{
			// oops, something went wrong
	        System.err.println( "Parsing failed.  Reason: " + e.getMessage() );
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("cowshot.jar", options);
			System.exit(1);
		}
		
		if(line.hasOption("api"))
		{
			System.out.println("Setting api host to '"+line.getOptionValue("api")+"'");
			api.setApiUrl( line.getOptionValue("api") );
		}
		if(line.hasOption("www"))
		{
			System.out.println("Setting www host to '"+line.getOptionValue("www")+"'");
			api.setApiUrl( line.getOptionValue("www") );
		}
		if(line.hasOption("help"))
		{
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("cowshot.jar", options);
			System.exit(1);
		}		
	}

}
