package it.uniroma1.lcl.tweetpro;

public class UserBuilder {
	  
	private User user;

	  public UserBuilder() { user = new User(); }

	  public User build(){ return user; }

	  public UserBuilder setID(long ID)
	  {
		  user.setID(ID);
		  return this;
	  }
	  
	  public UserBuilder setName(String name)
	  {
		  user.setName(name);
		  return this;
	  }
	  
	  public UserBuilder setScreenName(String screenName)
	  {
		  user.setScreenName(screenName);
		  return this;
	  }
	  
	  public UserBuilder setSatusesCount(int statuses_count)
	  {
		  user.setTweetsCount(statuses_count);
		  return this;
	  }
	  
	  public UserBuilder setFavsCount(int favouritesCount)
	  {
		  user.setFavsCount(favouritesCount);
		  return this;
	  }
	  
	  public UserBuilder setFollowersCount(int followersCount)
	  {
		  user.setFollowersCount(followersCount);
		  return this;
	  }
	  
	  public UserBuilder setFriendsCount(int friendsCount)
	  {
		  user.setFriendsCount(friendsCount);
		  return this;
	  }
	  
	  public UserBuilder setVerified(boolean verifiedAccount)
	  {
		  user.setVerified(verifiedAccount);
		  return this;
	  }
}