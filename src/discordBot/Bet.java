package discordBot;

import net.dv8tion.jda.api.entities.User;

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
	
	public Bet(long bet, String team, float teamValue, String game, String user)  {
		this.bet = bet;
		this.team = team;
		this.odd = teamValue;
		this.game = game;
		this.user = user;
	}
	
	public long bet() {
		return this.bet;
	}
	
	public String team() {
		return this.team;
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
