package com.isenteam.betgames.bot;

public class User {
	
	private String id;
	private String name;
	private int win;
	private int lose;
	private float gain;
	
	public User(String id, String name, int win, int lose, float gain)
	{
		this.id = id;
		this.name = name;
		this.win = win;
		this.lose = lose;
		this.gain = gain;
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
	
	

}
