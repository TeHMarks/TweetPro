package it.uniroma1.lcl.tweetpro.interfaces;

import java.io.File;
import java.util.List;

import it.uniroma1.lcl.tweetpro.Tweet;
import it.uniroma1.lcl.tweetpro.TweetCorpus;
import it.uniroma1.lcl.tweetpro.User;

public interface TweetCorpusInterface extends Iterable<Tweet>
{
	/**
	 * 
	 * @param file contenente i tweets
	 * @return il file nel formato JSON di Twitter
	 */
	static TweetCorpus parseFile(File file) { return new TweetCorpus(file); }
	
	/**
	 * @return numero di tweet contenuti nel corpus
	 */
	int getTweetCount();
	
	/**
	 * @param utente, l'utente del quale si vogliono sapere i tweet che ha scritto
	 * @return i tweet che ha scritto l'utente passato in input
	 */
	List<Tweet> getTweet(User utente);
	
	/**
	 * @param k numero di hashtags
	 * @return k hashtags piu frequenti nel corpus
	 */
	List<String> getTopHashtags(int k);
	
	/**
	 * @return il numero di utenti unici nel corpus
	 */
	int getUniqueUsersCount();
	
}
