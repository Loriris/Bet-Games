package discordBot;


public class Participant
{
	private String name;
	private int Champion;
	private int Sort1;
	private int Sort2;
	private int team;
	
	public Participant(String nameToSet, int championToSet, int teamToSet, int sort1ToSet, int sort2ToSet)
	{
		this.name = nameToSet;
		this.Champion = championToSet;
		this.Sort1 = sort1ToSet;
		this.Sort2 = sort2ToSet;
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

	public int getChampion()
	{
		return this.Champion;
	}

	public void setChampion(int champion)
	{
		this.Champion = champion;
	}

	public int getSort1()
	{
		return this.Sort1;
	}

	public void setSort1(int sort1)
	{
		this.Sort1 = sort1;
	}

	public int getSort2()
	{
		return this.Sort2;
	}

	public void setSort2(int sort2)
	{
		this.Sort2 = sort2;
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