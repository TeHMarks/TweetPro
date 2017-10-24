package it.uniroma1.lcl.tweetpro;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import it.uniroma1.lcl.tweetpro.interfaces.TweetInterface;

/**
 * 
 * @author tehma_000
 * Classe per la modellazione del tweet
 */
public class Tweet implements TweetInterface
{
	private long tweetID;
	private String tweetText;
	private User user;
	private int retweetCount;
	private int favouriteCount;
	private List<String> hashtags;
	private URL media_url;
	private Tweet originalTweet;
	
	public Tweet()
	{
		this.hashtags = new ArrayList<>();
		this.user = new User();
	}
	
	@Override
	public long getID() { return tweetID; }
	
	public void setID(long tweetID) { this.tweetID = tweetID; }
	
	@Override
	public User getUser() { return user; }
	
	public void setUser(User user) { this.user = user; }
	
	@Override
	public String getText() { return tweetText; }
	
	public void setText(String tweetText) { this.tweetText = tweetText; }

	@Override
	public int getLikeCount() { return favouriteCount; }
	
	public void setLikeCount(int favouriteCount) {this.favouriteCount = favouriteCount; }

	@Override
	public int getRTCount() { return retweetCount; }
	
	public void setRTCount(int retweetCount) { this.retweetCount = retweetCount; }
	
	@Override
	public boolean isRetweet() { return originalTweet == null ? false : true; }
	
	@Override
	public List<String> getHashtags() { return hashtags; }
	
	public void setHashtags(List<String> hashtags) { this.hashtags = new ArrayList<>(hashtags); }
	
	@Override
	public Tweet getOriginalTweet() { return isRetweet() ? originalTweet : null; }
	
	public void setOriginalTweet(Tweet originalTweet){ this.originalTweet = originalTweet; }
	
	@Override
	public Optional<URL> getMedia() { return Optional.ofNullable(media_url); }
	
	public void setMedia(URL media_url){ this.media_url = media_url; }
}
