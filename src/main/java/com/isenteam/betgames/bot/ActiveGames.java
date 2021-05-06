package com.isenteam.betgames.bot;

public class ActiveGames {
	
	private String id, type, player;
	
	public ActiveGames(String idToSet, String typeToSet, String playerToSet)
	{
		this.id = idToSet;
		this.type = typeToSet;
		this.player = playerToSet;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
}


