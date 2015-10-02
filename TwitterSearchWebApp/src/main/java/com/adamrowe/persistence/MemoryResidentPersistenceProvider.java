package com.adamrowe.persistence;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;

import com.adamrowe.models.TwitterSearchStatus;

public class MemoryResidentPersistenceProvider implements PersistenceProviderInterface
{
	private LinkedHashMap<String,LinkedHashSet<TwitterSearchStatus>> statuses = new LinkedHashMap<String,LinkedHashSet<TwitterSearchStatus>>();

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
	
	public Iterator<TwitterSearchStatus> getStatuses(String searchQuery)
	{
		LinkedHashSet<TwitterSearchStatus> statusSet = statuses.get(searchQuery);
		return statusSet.iterator();
	}
	
	public Set<String> getSearchQueries()
	{
		return statuses.keySet();
	}

}
