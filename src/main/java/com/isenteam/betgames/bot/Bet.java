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
	private String userName;
	
	private String gameId;
	
	private String userId;
	
	public Bet(long bet, String team, float teamValue, String game, String userName, String gameId, String userId) {
		this.bet = bet;
		this.team = team;
		this.odd = teamValue;
		this.game = game;
		this.userName = userName;
		this.gameId = gameId;
		this.userId = userId;
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
	
	public String userName() {
		return this.userName;
	}
	
	public String userId() {
		return this.userId;
	}
}
