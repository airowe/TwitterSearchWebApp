package com.adamrowe.persistence;

import java.util.*;

import com.adamrowe.models.TwitterSearchStatus;

public interface PersistenceProviderInterface
{
	public void storeStatus(String searchQuery, TwitterSearchStatus status);
	public Iterator<TwitterSearchStatus> getStatuses(String searchQuery);
	public Set<String> getSearchQueries();
}
