 package com.isenteam.betgames.bot;

import java.util.Arrays;

import com.isenteam.betgames.API.InfoAPI;
import com.isenteam.betgames.bdd.Mongo;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class BetCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args, teamName;
	private float coteEq1, coteEq2;
	private String regionServer;
	
	public BetCommand(GuildMessageReceivedEvent event, String[] args, 
			String[] teamName, float coteEq1, float coteEq2, String regionServer) {
		
		this.event = event;
		this.args = args;
		this.teamName = teamName;
		this.coteEq1 = coteEq1;
		this.coteEq2 = coteEq2;
		this.regionServer = regionServer;	
	}
	
	public void BetComm() 
	{
		ShowMessage mess = new ShowMessage(event);
		
		long money;
		
		// Check how many arguments were passed in, we need 4 args
	    if(this.args.length < 4)
	    {
	    	mess.showMess("🔴 Please try again by checking whether you have entered "
	    			+ "the team and/or the amount to bet (see #info).", 0xCA0707);
	    }
	    
	    if(this.args.length > 4)
	    {
	    	mess.showMess("🔴 Please try again, you have entered too many arguments (see #info).", 0xCA0707);
	    }
	    
	    if(this.args.length == 4)
	    {
	    	
	    	InfoAPI infos = new InfoAPI(this.args[3]);
	    	infos.PartyInfoMongo();
	    	
	    	Mongo mongo = new Mongo("Party");
		    if(Boolean.compare(mongo.searchForExistingParty(this.args[3]), false) == 0)
		    {
		    	mess.showMess("🔴 The ID entered isn't valid, check the available "
		    			+ "games with the command #games.", 0xCA0707);
		    }
		    else 
		    {
		    	if(Arrays.stream(this.teamName).anyMatch(this.args[1]::equals) == false)
				{
		    		mess.showMess("🔴 The selected team isn't valid, enter #teams to see available teams.", 0xCA0707);
				}
		    	
		    	for(int i = 0; i<this.teamName.length; i++) 
				{
					if(this.args[1].equalsIgnoreCase(this.teamName[i]) && this.args[2].isEmpty() == false)
					{
						money = Integer.parseInt(this.args[2]);
						if(money <=0 || money>100000)
						{
							mess.showMess("🔴 The selected amount isn't valid, "
									+ "values must be between 1 and 100,000.", 0xCA0707);
						}
						else
						{
			    			mess.showMess("🟢 Validated Bet.", 0x27AE1E);
							
							// perform an action to save the amount of money that was bet
							
							// we get the odd of each teams with cal
							CalculCote cal = new CalculCote(infos);
							try {
								cal.calcul();
							} catch (UnirestException e) {
								e.printStackTrace();
							}
							
							this.coteEq1 = cal.coteEq1;
							this.coteEq2 = cal.coteEq2;
							float [] teamValue = {this.coteEq1, this.coteEq2};
							
							Bet monPari = new Bet(money,this.teamName[i],teamValue[i], this.regionServer, 
									this.event.getAuthor().getName(), infos.getPartyInfo().get("gameId").getAsString(), 
									this.event.getAuthor().getId());
							Mongo col = new Mongo("Bets");
							col.insert(monPari);

							sendResult(this.event.getAuthor(), "Your bet on the game " + 
							infos.getPartyInfo().get("gameId").getAsString() + " was successfully recorded.");						
						}
					}
				}
		    }
	    } 
	}
	
	// send a private message to the gambler to inform him if he has won or lost
	public static void sendResult(User user, String content) 
	{
		user.openPrivateChannel().queue(channel -> {
			channel.sendMessage(content).queue();
			});
	}	
}