package com.adamrowe.persistence;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import com.adamrowe.models.TwitterSearchStatus;

public class DerbyPersistenceProvider implements PersistenceProviderInterface
{
	/*static
	{
		try
		{
			//Load the JDBC Driver for Derby into the JVM
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		}
		catch(Exception exc)
		{
			throw new RuntimeException(exc);
		}
	}*/
	
	private final Connection getConnection()
	{
		Connection connection = null;
		
		try 
		{
			Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
			connection = getDatabaseConnection("twittersearch/embeddeddb");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public void createSchema()
	{
		try 
		{			
			Connection connection = getConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("CREATE TABLE statuses (id  BIGINT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1) PRIMARY KEY, search_query VARCHAR(255) NOT NULL, status_id BIGINT NOT NULL, twitter_handle VARCHAR(255) NOT NULL, tweet_text VARCHAR(255) NOT NULL)");
			statement.close();
			connection.close();
		} 
		catch(SQLException exc) 
		{
			if(!exc.getSQLState().equals("X0Y32"))
			{
				exc.printStackTrace();
			}
		}		
	}
	   
	protected String getDerbyConnectionString(String databaseName)
	{
		String connectionString = "jdbc:derby:" + databaseName + ";create=true";
		return connectionString;
	}
	   
	protected Connection getDatabaseConnection(String databaseName) throws SQLException
	{
		return DriverManager.getConnection(getDerbyConnectionString(databaseName));
	}

	@Override
	public void storeStatus(String searchQuery, TwitterSearchStatus status) 
	{
		try 
        {		
			Connection connection = getConnection();				
        	PreparedStatement statusInsertStatement = connection.prepareStatement("INSERT INTO statuses (search_query,status_id,twitter_handle,tweet_text) VALUES (?,?,?,?)");
        	statusInsertStatement.setString(1, searchQuery);
        	statusInsertStatement.setLong(2, status.getId());
			statusInsertStatement.setString(3, status.getUserName());
			statusInsertStatement.setString(4, status.getText());	        
	        statusInsertStatement.executeUpdate();
	        statusInsertStatement.close();
	        connection.close();

		} 
        catch (SQLException e) 
        {
			e.printStackTrace();
		}
		
	}

	@Override
	public Iterator<TwitterSearchStatus> getStatuses(String searchQuery) 
	{
		PreparedStatement statusesStatement;
		try 
		{
			Connection connection = getConnection();	
			statusesStatement = connection.prepareStatement("SELECT status_id,twitter_handle,tweet_text FROM statuses WHERE search_query = ?");
	        statusesStatement.setString(1, searchQuery);
	        
	        List<TwitterSearchStatus> statusesList = new ArrayList<TwitterSearchStatus>();
	        ResultSet rs = statusesStatement.executeQuery();
	        rs.setFetchSize(500);
	        
	        while(rs.next())
	        {
	            String twitterHandle = rs.getString("twitter_handle");
	            String tweetText = rs.getString("tweet_text");
	            long statusId = rs.getLong("status_id");
	            
	            TwitterSearchStatus twitterSearchStatus = new TwitterSearchStatus(statusId, twitterHandle, tweetText);
	            statusesList.add(twitterSearchStatus);
	        }
	        
	        rs.close();
	        statusesStatement.close();
	        connection.close();
	        return statusesList.iterator();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public Set<String> getSearchQueries() 
	{
		try 
		{
			Connection connection = getConnection();	
			PreparedStatement searchRequestsStatement = connection.prepareStatement("SELECT distinct search_query FROM statuses");
			ResultSet rs = searchRequestsStatement.executeQuery();
	        rs.setFetchSize(500);
	        
	        Set<String> searchQueries = new LinkedHashSet<String>();
	        
	        while(rs.next())
	        {
	        	String searchQuery = rs.getString("search_query");
	        	searchQueries.add(searchQuery);
	        }
	        
	        rs.close();
	        searchRequestsStatement.close();
	        connection.close();
	        
	        return searchQueries;
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return null;
	}
}
