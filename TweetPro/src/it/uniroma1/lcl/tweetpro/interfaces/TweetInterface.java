package it.uniroma1.lcl.tweetpro.interfaces;

import java.net.URL;
import java.util.List;
import java.util.Optional;

import it.uniroma1.lcl.tweetpro.Tweet;
import it.uniroma1.lcl.tweetpro.User;

public interface TweetInterface
{
	/**
	 * @return l'ID del Tweet
	 */
	long getID();
	
	/**
	 * @return l'utente che ha scritto il Tweet
	 */
	User getUser();
	
	/**
	 * @return il testo del Tweet
	 */
	String getText();
	
	/**
	 * @return il numero di like che possiede il Tweet
	 */
	int getLikeCount();
	
	/**
	 * @return il numero di retweet attribuito al tweet originale
	 */
	int getRTCount();
	
	/**
	 * @return true se il tweet in questione è un retweet
	 */
	boolean isRetweet();
	
	/** 
	 * @return la lista degli hashtag all'interno del tweet
	 */
	List<String> getHashtags();
	
	/**
	 * @return il tweet originale se il tweet che si analizza e' un retweet
	 */
	Tweet getOriginalTweet();
	
	/**
	 * @return l'URL del media se è presente nel tweet
	 */
	Optional<URL> getMedia();
}
