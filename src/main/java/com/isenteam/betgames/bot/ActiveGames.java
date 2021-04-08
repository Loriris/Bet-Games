package com.isenteam.betgames.bot;

public class ActiveGames {
	
	private String id, type;
	
	public ActiveGames(String idToSet, String typeToSet)
	{
		this.id = idToSet;
		this.type = typeToSet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}


