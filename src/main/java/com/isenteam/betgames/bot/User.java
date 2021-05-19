package com.isenteam.betgames.bot;

public class User {
	
	private String id;
	private String name;
	private int win;
	private int lose;
	private float gain;
	private float leaderBoardScore;
	private int leaderBoardWin;
	private int leaderBoardLose;
	
	
	public User(String id, String name, int win, int lose, float gain, float leaderBoardScore, int leaderBoardLose, int leaderBoardWin)
	{
		this.id = id;
		this.name = name;
		this.win = win;
		this.lose = lose;
		this.gain = gain;
		this.leaderBoardScore = leaderBoardScore;
		this.leaderBoardWin = leaderBoardWin;
		this.leaderBoardLose = leaderBoardLose;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getWin() {
		return win;
	}

	public void setWin(int win) {
		this.win = win;
	}

	public int getLose() {
		return lose;
	}

	public void setLose(int lose) {
		this.lose = lose;
	}

	public float getGain() {
		return gain;
	}

	public void setGain(float gain) {
		this.gain = gain;
	}

	public float getLeaderBoardScore() {
		return leaderBoardScore;
	}

	public void setLeaderBoardScore(float leaderBoardScore) {
		this.leaderBoardScore = leaderBoardScore;
	}

	public int getLeaderBoardWin() {
		return leaderBoardWin;
	}

	public void setLeaderBoardWin(int leaderBoardWin) {
		this.leaderBoardWin = leaderBoardWin;
	}

	public int getLeaderBoardLose() {
		return leaderBoardLose;
	}

	public void setLeaderBoardLose(int leaderBoardLose) {
		this.leaderBoardLose = leaderBoardLose;
	}
	
	

}
