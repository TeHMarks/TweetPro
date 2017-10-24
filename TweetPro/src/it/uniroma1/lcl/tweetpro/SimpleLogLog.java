package it.uniroma1.lcl.tweetpro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.uniroma1.lcl.tweetpro.interfaces.UniqueUserStrategyInterface;

public class SimpleLogLog implements UniqueUserStrategyInterface
{
	private Set<Long> idUserSet;

	@Override
	public int execute(List<Long> idUserList)
	{
		idUserSet = new HashSet<>(idUserList);
		return idUserSet.size();
	}
	
}
