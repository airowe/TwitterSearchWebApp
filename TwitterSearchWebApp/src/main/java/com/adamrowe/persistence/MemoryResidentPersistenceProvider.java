package com.adamrowe.persistence;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.adamrowe.models.TwitterSearchStatus;

public class MemoryResidentPersistenceProvider implements PersistenceProviderInterface
{
	private LinkedHashMap<String,LinkedHashSet<TwitterSearchStatus>> statuses = new LinkedHashMap<String,LinkedHashSet<TwitterSearchStatus>>();
	
	/*
	 * (non-Javadoc)
	 * @see com.adamrowe.persistence.PersistenceProviderInterface#storeStatus(java.lang.String, com.adamrowe.models.TwitterSearchStatus)
	 * Store Status in memory resident LinkedHashMap (for testing and when database is not available)
	 * @param searchQuery - search term
	 * @param TwitterSearchStatus - status returned from search
	 */
	@Override
	public void storeStatus(String searchQuery, TwitterSearchStatus status) 
	{
		LinkedHashSet<TwitterSearchStatus> statusSet;
		if(!statuses.containsKey(searchQuery))
		{
			statusSet = new LinkedHashSet<TwitterSearchStatus>();
		}
		else
		{
			statusSet = statuses.get(searchQuery);
		}
		statusSet.add(status);
		statuses.put(searchQuery, statusSet);		
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adamrowe.persistence.PersistenceProviderInterface#getStatuses(java.lang.String)
	 * @return all statuses on a given search query
	 * @param searchQuery - search term 
	 */
	@Override
	public Iterator<TwitterSearchStatus> getStatuses(String searchQuery)
	{
		LinkedHashSet<TwitterSearchStatus> statusSet = statuses.get(searchQuery);
		return statusSet.iterator();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.adamrowe.persistence.PersistenceProviderInterface#getSearchQueries()
	 * @return all distinct search queries
	 */
	@Override
	public Set<String> getSearchQueries()
	{
		return statuses.keySet();
	}

}
