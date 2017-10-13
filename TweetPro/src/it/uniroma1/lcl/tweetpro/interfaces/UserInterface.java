package it.uniroma1.lcl.tweetpro.interfaces;

public interface UserInterface
{
	/**
	 * @return l'id dell'utente
	 */
	long getId();
	
	/**
	 * @return il nome pubblico dell'utente
	 */
	String getName();
	
	/**
	 * @return il nome univoco dell'utente
	 */
	String getScreenName();
	
	/**
	 * @return il numero di tweet che ha scritto l'utente
	 */
	int getTweetsCount();
	
	/**
	 * @return il numero di tweet che sono piaciuti all'utente
	 */
	int getFavsCount();
	
	/**
	 * @return il numero di utente che segue lo USER
	 */
	int getFollowersCount();
	
	/**
	 * @return il numero di utenti che lo USER segue
	 */
	int getFriendsCount();
	
	/**
	 * @return true se l'utente è verificato
	 */
	boolean isVerified();
}
