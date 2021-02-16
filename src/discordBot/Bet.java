package discordBot;

public class Bet {
	
	//mise
	private long bet;
	//equipe
	private String team;
	//cote
	private String odd;
	//partie 
	private String game;
	
	public Bet(long bet, String team, String odd, String game)  {
		this.bet = bet;
		this.team = team;
		this.odd = odd;
		this.game = game;
	}
	
	public long bet() {
		return this.bet;
	}
	
	public String team() {
		return this.team;
	}
	
	public String odd() {
		return this.odd;
	}
	
	public String game() {
		return this.game;
	}
}
