package com.adamrowe.servlets;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.adamrowe.models.TwitterConnection;

public class AppContextListener implements ServletContextListener 
{

	@Override
	public void contextDestroyed(ServletContextEvent evt) 
	{
		TwitterConnection connection = (TwitterConnection) evt.getServletContext().getAttribute("twitterConnection");
		if(connection != null)
		{
			connection.getTwitterStream().cleanUp();
			connection.getTwitterStream().shutdown();
		}

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		// TODO Auto-generated method stub

	}

}
