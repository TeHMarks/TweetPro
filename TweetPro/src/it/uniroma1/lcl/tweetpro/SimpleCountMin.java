package it.uniroma1.lcl.tweetpro;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import it.uniroma1.lcl.tweetpro.interfaces.HashtagStrategyInterface;

public class SimpleCountMin implements HashtagStrategyInterface
{
	//VERSIONE SEMPLIFICATA DEL COUNT MIN, SENZA LIMITAZIONE SULLA GRANDEZZA DELLA MAPPA
	@Override
	public Set<String> execute(List<String> hashtagsList, int k)
	{
		HashMap<String, Integer> hashtagsMap = new HashMap<>();
		for(String s : hashtagsList)
		{
			hashtagsMap.put(s, hashtagsMap.get(s) == null ? 1 : hashtagsMap.get(s)+1);
		}
		hashtagsMap = (HashMap<String, Integer>) hashtagsMap.entrySet().stream()
				.sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
				.limit(k).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

		return hashtagsMap.keySet();
	}
	
}
