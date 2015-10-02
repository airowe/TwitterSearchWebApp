package com.adamrowe.servlets;

import java.io.IOException;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adamrowe.models.TwitterConnection;
import com.adamrowe.utilities.AuthenticationUtilities;
/*
 * Web Servlet mapped to URL /search_twitter
 * Starts filtering on a search term and forwards to search_results.jsp to give user ability to see results
 */
@WebServlet("/search_twitter")
public class SearchServlet extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request,response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		/* Get search query parameter from jsp */
		String query = request.getParameter("query");
		
		/* Create Twitter Connection if one does not exist */
		TwitterConnection connection = (TwitterConnection) getServletContext().getAttribute("twitterConnection");
		if(connection == null)
		{
			connection = new TwitterConnection(AuthenticationUtilities.consumerKey, AuthenticationUtilities.consumerSecret);
			getServletContext().setAttribute("twitterConnection", connection);
		}
		
		/* Get search queries from database */
		Set<String> searchQueries = connection.getProvider().getSearchQueries();
		
		if(query != null)
		{
			/* Filter tweets based on search query */
			connection.filterTweets(query);
			
			/* Pass search query back to jsp */
			request.setAttribute("searchQuery", query);		
			
			/* Add current query in to returned set in case a tweet hasn't been posted on that query yet */
			searchQueries.add(query);	
		}
		
		/* Pass search queries in database back to jsp */
		request.setAttribute("searchRequests", searchQueries);
		
		request.getRequestDispatcher("/WEB-INF/search_results.jsp").forward(request, response);
	}

}
