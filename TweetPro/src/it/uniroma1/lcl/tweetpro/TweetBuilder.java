package it.uniroma1.lcl.tweetpro;

import java.net.URL;
import java.util.List;

public class TweetBuilder {
	
	private Tweet tweet;
	
	public TweetBuilder(){ tweet = new Tweet(); }
	
	public Tweet build(){ return tweet; }
	
	public TweetBuilder setID(long tweetID)
	{
		tweet.setID(tweetID);
		return this;
	}
	
	public TweetBuilder setUser(User user)
	{
		tweet.setUser(user);
		return this;
	}
	
	public TweetBuilder setText(String tweetText)
	{
		tweet.setText(tweetText);
		return this;
	}
	
	public TweetBuilder setLikeCount(int likeCount)
	{
		tweet.setLikeCount(likeCount);
		return this;
	}
	
	public TweetBuilder setRTCount(int retweetCount)
	{
		tweet.setRTCount(retweetCount);
		return this;
	}
	
	public TweetBuilder setHashtags(List<String> hashtags)
	{
		tweet.setHashtags(hashtags);
		return this;
	}
	
	public TweetBuilder setOriginalTweet(Tweet originalTweet)
	{
		tweet.setOriginalTweet(originalTweet);
		return this;
	}
	
	public TweetBuilder setMedia(URL media_url)
	{
		tweet.setMedia(media_url);
		return this;
	}
}
