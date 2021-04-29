package com.isenteam.betgames.bot;


public class Bet {
	
	//mise
	private long bet;
	//equipe
	private String team;
	//cote
	private float odd;
	//partie 
	private String game;
	//gambler
	private String user;
	
	private String gameId;
	
	public Bet(long bet, String team, float teamValue, String game, String user, String gameId) {
		this.bet = bet;
		this.team = team;
		this.odd = teamValue;
		this.game = game;
		this.user = user;
		this.gameId = gameId;
	}
	
	public long bet() {
		return this.bet;
	}
	
	public String team() {
		return this.team;
	}
	
	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public float odd() {
		return this.odd;
	}
	
	public String game() {
		return this.game;
	}
	
	public String user() {
		return this.user;
	}
}
