package it.uniroma1.lcl.tweetpro;

import it.uniroma1.lcl.tweetpro.interfaces.UserInterface;

/**
 * 
 * @author tehma_000
 * Classe per la modellazione dell'utente
 */
public class User implements UserInterface
{
	/**
	 * @param id univoco dell'utente
	 * @param name nome dell'utente
	 * @param screenName userename dell'utente preceduto da @ 
	 * @param tweetsCount numero di tweet da parte dell'utente
	 * @param favourites_count numero di tweet su cui l'utente ha messo mi piace
	 * @param followersCount numero di seguaci dell'utente
	 * @param friendsCount numero degli amici dell'utente
	 * @param verifiedAccount boolean che indica se l'account è verificato o meno 
	 */
	private String name, screenName;
	private int statuses_count, favouritesCount, followersCount, friendsCount;
	private long ID;
	private boolean verifiedAccount;
	
	public User(){}
	
	@Override
	public long getId() { return ID; }
	
	public void setID(long ID){ this.ID = ID; }
	
	@Override
	public String getName(){ return name; }
	
	public void setName(String name){ this.name = name; }
	
	@Override
	public String getScreenName(){ return screenName; }
	
	public void setScreenName(String screenName){ this.screenName = screenName; }

	@Override
	public int getTweetsCount() { return statuses_count; }
	
	public void setTweetsCount(int statuses_count){ this.statuses_count = statuses_count;}

	@Override
	public int getFavsCount() { return favouritesCount; }
	
	public void setFavsCount(int favouritesCount){ this.favouritesCount = favouritesCount;}

	@Override
	public int getFollowersCount() { return followersCount; }
	
	public void setFollowersCount(int followersCount){ this.followersCount = followersCount; }

	@Override
	public int getFriendsCount() { return friendsCount; }
	
	public void setFriendsCount(int friendsCount){ this.friendsCount = friendsCount; }

	@Override
	public boolean isVerified() { return verifiedAccount; }
	
	public void setVerified(boolean verifiedAccount){ this.verifiedAccount = verifiedAccount; }
}
