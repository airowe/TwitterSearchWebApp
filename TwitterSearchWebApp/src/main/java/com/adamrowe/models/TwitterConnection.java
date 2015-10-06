package com.adamrowe.models;

import com.adamrowe.persistence.DerbyPersistenceProvider;
import com.adamrowe.utilities.AuthenticationUtilities;

import twitter4j.*;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

/*
 * @author arowe
 * TwitterConnection class creates a TwitterStream instance and filters tweets based on a provided query term
 */
public class TwitterConnection
{
   private static TwitterStream twitterStream;
   private DerbyPersistenceProvider provider;
   
   /*
    * Constructor utilizing dependency injection to create the TwitterStream
    * @param String consumerKey -- future builds will prompt for this or authenticate on twitter
    * @param String consumerSecret -- future builds will prompt for this or authenticate on twitter
    */
   public TwitterConnection(String consumerKey, String consumerSecret)
   {
	   if(twitterStream == null)
	   {
		   ConfigurationBuilder builder = new ConfigurationBuilder();
		   builder.setOAuthConsumerKey(consumerKey);
		   builder.setOAuthConsumerSecret(consumerSecret);
		   builder.setOAuthAccessToken(AuthenticationUtilities.accessToken);
		   builder.setOAuthAccessTokenSecret(AuthenticationUtilities.accessSecret);
		   Configuration configuration = builder.build();
		   TwitterStreamFactory factory = new TwitterStreamFactory(configuration);
		   twitterStream = factory.getInstance();
	   }
	   
	   if(provider == null)
	   {
		   provider = new DerbyPersistenceProvider();
		   provider.createSchema();
	   }
   }
   
   /*
    * Method to filter tweets based on given searchQuery
    * @param String searchQuery -- the term with which to filter tweets
    */
   public void filterTweets(final String searchQuery)
   {
	   /* Setup FilterQuery on search term */
	  FilterQuery tweetFilterQuery = new FilterQuery();
	  tweetFilterQuery.track(new String[]{searchQuery});
	  
	  StatusListener listener = new StatusListener()
	  {
	        public void onStatus(Status status) 
	        {
	        	TwitterSearchStatus twitterSearchStatus = new TwitterSearchStatus(status.getId(), status.getUser().getScreenName(), status.getText());
	        	provider.storeStatus(searchQuery, twitterSearchStatus);
	        }
	        public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {}
	        public void onTrackLimitationNotice(int numberOfLimitedStatuses) 
	        {
	            twitterStream.cleanUp();
	            twitterStream.shutdown();	        	
	        }
	        public void onException(Exception ex) 
	        {
	            ex.printStackTrace();
	            twitterStream.cleanUp();
	            twitterStream.shutdown();
	        }
			public void onScrubGeo(long arg0, long arg1) {}
			public void onStallWarning(StallWarning stallWarning) 
			{
	            twitterStream.cleanUp();
	            twitterStream.shutdown();
			}
	    };
	    
	    twitterStream.addListener(listener);
	  
	    /* Start listening on all public tweets with given FilterQuery */
	    twitterStream.filter(tweetFilterQuery);
   }
   
   public DerbyPersistenceProvider getProvider()
   {
	   return provider;
   }
   
   public TwitterStream getTwitterStream()
   {
	   return twitterStream;
   }
}
