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

	private String done;
	 
	private String status;
	
	public Bet(long bet, String team, float teamValue, String game, String userName, String gameId, String userId) {
		this.bet = bet;
		this.team = team;
		this.odd = teamValue;
		this.game = game;
		this.userName = userName;
		this.gameId = gameId;
		this.userId = userId;
	}
	
	public Bet(long bet, String team, float teamValue, String userName, String gameId, String userId, String doneToSet, String statusToSet) {
		this.bet = bet;
		this.team = team;
		this.odd = teamValue;
		this.userName = userName;
		this.gameId = gameId;
		this.userId = userId;
		this.done = doneToSet;
		this.status = statusToSet;
	}


	public long getBet() {
		return bet;
	}

	public void setBet(long bet) {
		this.bet = bet;
	}

	public String getTeam() {
		return team;
	}

	public void setTeam(String team) {
		this.team = team;
	}

	public float getOdd() {
		return odd;
	}

	public void setOdd(float odd) {
		this.odd = odd;
	}

	public String getGame() {
		return game;
	}

	public void setGame(String game) {
		this.game = game;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	public String getDone() {
		return done;
	}

	public String getStatus() {
		return status;
	}
	
	
}
