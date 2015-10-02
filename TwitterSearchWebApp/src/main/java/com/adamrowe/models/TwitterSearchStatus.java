package com.adamrowe.models;

import java.util.Date;

import twitter4j.ExtendedMediaEntity;
import twitter4j.GeoLocation;
import twitter4j.HashtagEntity;
import twitter4j.MediaEntity;
import twitter4j.Place;
import twitter4j.RateLimitStatus;
import twitter4j.Scopes;
import twitter4j.Status;
import twitter4j.SymbolEntity;
import twitter4j.URLEntity;
import twitter4j.User;
import twitter4j.UserMentionEntity;

/*
 * TwitterSearchStatus implements Status interface
 * In an extended production build of this app, we'd extend the functionality
 *  and implement more of the properties of Twitter4j.org's Status interface.
 */
public class TwitterSearchStatus implements Status 
{
	private static final long serialVersionUID = 1L;
	long statusId;
	String userName;
	String tweetText;
	
	/*
	 * Constructor
	 * @param Long tweetId
	 */
	public TwitterSearchStatus(long statusId, String userName, String tweetText)
	{
		this.statusId = statusId;
		this.userName = userName;
		this.tweetText = tweetText;
	}

	@Override
	public long getId() 
	{
		return statusId;
	}

	@Override
	public String getText() 
	{
		return tweetText;
	}
	
	public String getUserName()
	{
		return userName;
	}

	@Override
	public int compareTo(Status arg0) { return 0; }

	@Override
	public RateLimitStatus getRateLimitStatus() { return null; }

	@Override
	public int getAccessLevel() { return 0; }

	@Override
	public UserMentionEntity[] getUserMentionEntities() { return null; }

	@Override
	public URLEntity[] getURLEntities() { return null; }

	@Override
	public HashtagEntity[] getHashtagEntities() { return null; }

	@Override
	public MediaEntity[] getMediaEntities() { return null; }

	@Override
	public ExtendedMediaEntity[] getExtendedMediaEntities() { return null; }

	@Override
	public SymbolEntity[] getSymbolEntities() { return null; }

	@Override
	public Date getCreatedAt() { return null; }

	@Override
	public String getSource() { return null; }

	@Override
	public boolean isTruncated() { return false; }

	@Override
	public long getInReplyToStatusId() { return 0; }

	@Override
	public long getInReplyToUserId() { return 0; }

	@Override
	public String getInReplyToScreenName() { return null; }

	@Override
	public GeoLocation getGeoLocation() { return null; }

	@Override
	public Place getPlace() { return null; }

	@Override
	public boolean isFavorited() { return false; }

	@Override
	public boolean isRetweeted() { return false; }

	@Override
	public int getFavoriteCount() { return 0; }

	@Override
	public User getUser() { return null; }

	@Override
	public boolean isRetweet() { return false; }

	@Override
	public Status getRetweetedStatus() { return null; }

	@Override
	public long[] getContributors() { return null; }

	@Override
	public int getRetweetCount() { return 0; }

	@Override
	public boolean isRetweetedByMe() { return false; }

	@Override
	public long getCurrentUserRetweetId() { return 0; }

	@Override
	public boolean isPossiblySensitive() { return false; }

	@Override
	public String getLang() { return null; }

	@Override
	public Scopes getScopes() { return null; }

	@Override
	public String[] getWithheldInCountries() { return null;	}
}
