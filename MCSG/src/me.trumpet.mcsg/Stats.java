package me.trumpet.mcsg;

import org.bukkit.entity.Player;

public class Stats {
	
	int chestsOpened;
	int gamesPlayed;
	int playersKilled;
	int gamesWon;
	int deathMatches;
	int kdRatio;
	static int deaths;
	
	public int getChestsOpened()
	{
		return chestsOpened;
	}
	
	public int getGamesPlayed()
	{
		return gamesPlayed;
	}
	
	
	public int getPlayersKilled()
	{
		return playersKilled;
	}
	
	public int getGamesWon()
	{
		return gamesWon;
	}
	
	
	public int getDeathMatchesAt()
	{
		return deathMatches;
	}
	
	public int getKDRatio()
	{
		if(deaths==0)
		{
			return playersKilled;
		}
		else
		{
			return (playersKilled/deaths);
		}
	}
	
	//fghdfghdfghdfgh
	public void increaseChestsOpened()
	{
		chestsOpened++;
	}
	
	public void increaseGamesPlayed()
	{
		gamesPlayed++;
	}
	
	
	public void increasePlayersKilled()
	{
		playersKilled++;
	}
	
	public void increaseGamesWon()
	{
		gamesWon++;
	}
	
	
	public void increaseDeathMatchesAt()
	{
		deathMatches++;
	}
	
	public void increaseKDRatio()
	{
		kdRatio++;
	}
	
	public void increaseDeaths()
	{
		deaths++;
	}
	
	public String getStats(Player p)
	{
		return "§0---§2"+p.getName()+"§7'§fs stats\n"+
		"§fGames Played§7: §e"+ gamesPlayed+"\n"+
		"§fPlayer Kills§7: §e"+ playersKilled+"\n"+
		"§fGames Won§7: §e"+ gamesWon+"\n"+
		"§fDeathMatches§7: §e"+ deathMatches+"\n"+
		"§fKill / Death Ratio§7: §e"+ kdRatio;
		
	}
	
	public void resetStats()
	{
		chestsOpened=0;
		gamesPlayed=0;
		playersKilled=0;
		gamesWon=0;
		deathMatches=0;
		kdRatio=0;
	}
	
	
}

