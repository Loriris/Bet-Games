package discordBot;


public class Participant
{
	private String name;
	private String championName;
	private int sort1;
	private int sort2;
	private int team;
	
	public Participant(String nameToSet, String championToSet, int teamToSet, int sort1ToSet, int sort2ToSet)
	{
		this.name = nameToSet;
		this.championName = championToSet;
		this.sort1 = sort1ToSet;
		this.sort2 = sort2ToSet;
		this.team = teamToSet;
		
	}

	public String getName()
	{
		return this.name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getChampion()
	{
		return this.championName;
	}

	public void setChampion(String champion)
	{
		this.championName = champion;
	}

	public int getSort1()
	{
		return this.sort1;
	}

	public void setSort1(int sort1)
	{
		this.sort1 = sort1;
	}

	public int getSort2()
	{
		return this.sort2;
	}

	public void setSort2(int sort2)
	{
		this.sort2 = sort2;
	}

	public int getTeam()
	{
		return this.team;
	}

	public void setTeam(int team)
	{
		this.team = team;
	}
}