package it.uniroma1.lcl.tweetpro;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.stream.Collectors;
import java.net.MalformedURLException;
import java.net.URL;

import it.uniroma1.lcl.tweetpro.interfaces.ParsingException;
import it.uniroma1.lcl.tweetpro.interfaces.TweetCorpusInterface;


public class TweetCorpus implements TweetCorpusInterface
{
	private File tweets;
	private int k;

	public TweetCorpus(File tweets) { this.tweets = tweets; }

	@Override
	public Iterator<Tweet> iterator()
	{
		try { return new TweetIterator(); }
		catch (ParsingException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	private class TweetIterator implements Iterator<Tweet>
	{
		Scanner sc;
		public TweetIterator() throws ParsingException
		{
			try { sc = new Scanner(new FileInputStream(tweets), "UTF-8"); }
			catch(IOException e){ throw new ParsingException(e); }
		}

		@Override
		public boolean hasNext() { return sc.hasNextLine(); }

		@Override
		public Tweet next()
		{
			if(!hasNext()) { return null; }
			return tweetCreator(sc.nextLine());
		}

		private Tweet tweetCreator(String singleTweet) { return tweetParser(singleTweet); }
	}

	static TweetCorpus parseFile(File file) { return new TweetCorpus(file); }

	private User userParser(String[] splittedUser)
	{
		UserBuilder ub = new UserBuilder();
		for(int i = 0; i < splittedUser.length; i++)
		{
			String[] field = splittedUser[i].split(":");
			switch(field[0])
			{
				case "\"id\"":{
					long userID = Long.parseLong(field[1].replace(",", ""));
					ub.setID(userID);
					break;
				}

				case "\"name\"":{
					if(field[1].contains("{"))
					{
						int count = 0;
						int position = i;
						do
						{
							int opn = (int)splittedUser[i].chars().filter(ch -> ch == '{').count();
							int cls = (int)splittedUser[i].chars().filter(ch -> ch == '}').count();
							count += opn - cls;
							i++;
						} while(count != 0);
						field[1] = String.join("", Arrays.copyOfRange(splittedUser, position, i));
						field[1] = field[1].replace("\"name\":", "");
					}
					else
					{
						field[1] = String.join(":", Arrays.copyOfRange(field, 1, field.length));
					}
					String name = field[1].substring(1, field[1].length()-2);
					ub.setName(name);
					break;
				}

				case "\"screen_name\"":{
					String screenName = field[1].substring(1, field[1].length()-2);
					ub.setScreenName(screenName);
					break;
				}

				case "\"followers_count\"":{
					int followersCount = Integer.parseInt(field[1].replace(",", ""));
					ub.setFollowersCount(followersCount);
					break;
				}

				case "\"favourites_count\"":{
					int favouritesCount = Integer.parseInt(field[1].replace(",", ""));
					ub.setFavsCount(favouritesCount);
					break;
				}

				case "\"friends_count\"":{
					int friendsCount = Integer.parseInt(field[1].replace(",", ""));
					ub.setFriendsCount(friendsCount);
					break;
				}

				case "\"veriffied\"":{
					boolean verifiedAccount = field[1].replace(",", "").equals("true");
					ub.setVerified(verifiedAccount);
					break;
				}

				case "\"statuses_count\"": {
					int statuses_count = Integer.parseInt(field[1].replace(",", ""));
					ub.setSatusesCount(statuses_count);
					break;
				}
			}
		}
		return ub.build();
	}

	private Tweet tweetParser(String singleTweet)
	{
		TweetBuilder tb = new TweetBuilder();
		singleTweet = singleTweet.replaceAll("\\{", "\\{\n").replaceAll("\\}", "\n\\}")
				.replaceAll(",\"", ",\n\"");
//		System.out.println(singleTweet);
		String[] splittedTweet = singleTweet.split("\n");
		String[] splittedUser;
		boolean flag = false;
		for(int i = 0; i < splittedTweet.length; i++)
		{
			String[] field = splittedTweet[i].split(":");
			switch(field[0])
			{
				case "\"id\"":{
					if(!flag)
					{
						long tweetID = Long.parseLong(field[1].replace(",", ""));
						flag = true;
						tb.setID(tweetID);
					}
					break;
				}

				case "\"text\"":{
					field[1] = String.join(":", Arrays.copyOfRange(field, 1, field.length));
//					System.out.println(splittedTweet[i]);
//					System.out.println(field[1]);
					String tweetText = field[1].substring(1, field[1].length()-2);
//					System.out.println(tweetText);
					tb.setText(tweetText);
					break;
				}

				case "\"user\"":{
					//FINCHE NON FINISCE L'OGGETTO USER SALVO I CAMPI IN UN ARRAY
					int position = i;
					while(!splittedTweet[i].equals("},")) { i++; }
					splittedUser = Arrays.copyOfRange(splittedTweet, position, i);
					//QUANDO TROVO LA CHIUSURA PASSO AL METODO SOLO L'ARRAY CONTENENTE I CAMPI DELLO USER
					tb.setUser(userParser(splittedUser));
					break;
				}

				case "\"retweet_count\"":{
					int retweetCount = Integer.parseInt(field[1].replace(",", ""));
					tb.setRTCount(retweetCount);
					break;
				}

				case "\"favourite_count\"":{
					int favouriteCount = Integer.parseInt(field[1].replaceAll(",", ""));
					tb.setLikeCount(favouriteCount);
					break;
				}

				case "\"retweeted_status\"":{
					int count = 1;
					int position = i+1;
					while(count != 0)
					{
						i++;
						int opn = (int)splittedTweet[i].chars().filter(ch -> ch == '{').count();
						int cls = (int)splittedTweet[i].chars().filter(ch -> ch == '}').count();
						count += opn - cls;
					}
					String origTweet = String.join("", Arrays.copyOfRange(splittedTweet, position, i));
					tb.setOriginalTweet(tweetParser(origTweet));
					break;
				}

				case "\"hashtags\"":{
					List<String> hl = new ArrayList<>();
					if(!field[1].contains("]"))
						while(!splittedTweet[i].equals("}],"))
						{
							String[] hashtagField = splittedTweet[i].split(":");
							if(hashtagField[0].contains("text"))
							{
								hl.add(hashtagField[1].substring(1, hashtagField[1].length()-2).toLowerCase());
							}
							i++;
						}
//					System.out.println(hl);
					tb.setHashtags(hl);
					break;
				}

				case "\"media\"":{
					while(!splittedTweet[i].contains("media_url")) i++;
					try {
						URL imgURL = new URL(splittedTweet[i].substring(13, splittedTweet[i].length()-2).replace("\\",""));
						tb.setMedia(imgURL);
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
				}
			}
		}
		return tb.build();
	}

	@Override
	public int getTweetCount(){
		int count = 0;
		Scanner sc;
		try {
			sc = new Scanner(new FileInputStream(tweets), "UTF-8");
			while(sc.hasNextLine())
			{
				if(!sc.nextLine().equals("")) count++;
			}
		} catch(IOException e) { e.printStackTrace(); }
		return count;
	}

	@Override
	public List<Tweet> getTweet(User utente) {
		Scanner sc;
		List<Tweet> tweetList = new ArrayList<>();
		try {
			sc = new Scanner(new FileInputStream(tweets), "UTF-8");
			while(sc.hasNextLine())
			{
				Tweet tw = tweetParser(sc.nextLine());
				if(utente.getID() == tw.getUser().getID()){ tweetList.add(tw); }
				else if(tw.isRetweet() && utente.getID() == tw.getOriginalTweet().getUser().getID())
				{
					tweetList.add(tw);
				}
			}
		} catch(IOException e) { e.printStackTrace(); }
		return tweetList;
	}

	@Override
	public List<String> getTopHashtags(int k) {
		this.k = k;
		List<String> topHashtags = countMin(getHashtagsList()).stream().collect(Collectors.toList());
		return topHashtags;
	}

	private Set<String> countMin(List<String> hashtagsList)
	{
		HashMap<String, Integer> hashtagsMap = new HashMap<>();
		int mapSize = k * 100;
		for(String s : hashtagsList)
		{
			if(hashtagsMap.containsKey(s) || hashtagsMap.size() < mapSize)
			{
				hashtagsMap.put(s, hashtagsMap.get(s) == null ? 1 : hashtagsMap.get(s)+1);
			}
			else
			{				
				int oldValue = hashtagsMap.remove(getMinKey(hashtagsMap));
				hashtagsMap.put(s, oldValue++);
			}
		}
		hashtagsMap = (HashMap<String, Integer>) hashtagsMap.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.limit(k).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		return hashtagsMap.keySet();
	}
	
	//VERSIONE SEMPLIFICATA DEL COUNT MIN, SENZA LIMITAZIONE SULLA GRANDEZZA DELLA MAPPA
	@SuppressWarnings("unused")
	private Set<String> simpleCountMin(List<String> hashtagsList)
	{
		HashMap<String, Integer> hashtagsMap = new HashMap<>();
		for(String s : hashtagsList)
		{
			hashtagsMap.put(s, hashtagsMap.get(s) == null ? 1 : hashtagsMap.get(s)+1);
		}
		hashtagsMap = (HashMap<String, Integer>) hashtagsMap.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
		
		return hashtagsMap.keySet();
	}
	
	// PRENDO LA STRINGA CHE HA NUMERO DI OCCORRENZE PIU BASSO
	private String getMinKey(HashMap<String, Integer> hashtagsMap)
	{
		return hashtagsMap.entrySet().stream().min(Comparator.comparingInt(Map.Entry::getValue)).get().getKey();
	}
	
	private List<String> getHashtagsList()
	{
		Scanner sc;
		List<String> hashtagsList = new ArrayList<>();
		try {
			sc = new Scanner(new FileInputStream(tweets), "UTF-8");
			while(sc.hasNextLine())
			{
				Tweet tw = tweetParser(sc.nextLine());
				hashtagsList.addAll(tw.getHashtags());
			}
		} catch(IOException e) { e.printStackTrace(); }
		return hashtagsList;
	}

	@Override
	public int getUniqueUsersCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
