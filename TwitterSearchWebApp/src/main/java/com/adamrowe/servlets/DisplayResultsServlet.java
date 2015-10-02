package com.adamrowe.servlets;

import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adamrowe.models.TwitterConnection;
import com.adamrowe.models.TwitterSearchStatus;
import com.adamrowe.utilities.AuthenticationUtilities;

/*
 * Web Servlet mapped to URL /display_search
 * Displays statuses returned by filtered search
 */
@WebServlet("/display_search")
public class DisplayResultsServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Get search query parameter from jsp */
		String query = (String) request.getParameter("searchQuery");
		
		/* Get Twitter Connection from Servlet Context */
		TwitterConnection connection = (TwitterConnection) getServletContext().getAttribute("twitterConnection");
		
		if(connection == null)
		{
			connection = new TwitterConnection(AuthenticationUtilities.consumerKey, AuthenticationUtilities.consumerSecret);
			getServletContext().setAttribute("twitterConnection", connection);
		}
		/* Get search queries from database */
		Set<String> searchQueries = connection.getProvider().getSearchQueries();
		
		/* Add current query in to returned set in case a tweet hasn't been posted on that query yet */
		searchQueries.add(query);
		
		if(query != null)
		{
			/* Pass searchQuery attribute back to jsp for setting selected option */
			request.setAttribute("searchQuery", query);
		}
		
		/* Pass search queries from database back to jsp */
		request.setAttribute("searchRequests", searchQueries);
		
		/* Get statuses from database */
		Iterator<TwitterSearchStatus> statuses = connection.getProvider().getStatuses(query);
		
		/* Pass statuses from database back to jsp */
		request.setAttribute("searchResults", statuses);
		
		getServletContext().getRequestDispatcher("/WEB-INF/search_results.jsp").forward(request, response);
	}

}
