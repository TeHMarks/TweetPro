package it.uniroma1.lcl.tweetpro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.uniroma1.lcl.tweetpro.interfaces.HashtagStrategyInterface;

public class AdvancedCountMin implements HashtagStrategyInterface
{

	@Override
	public Set<String> execute(List<String> hashtagsList, int k) 
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
	
}
