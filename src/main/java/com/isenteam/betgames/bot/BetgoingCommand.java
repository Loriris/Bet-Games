package com.isenteam.betgames.bot;

import java.util.ArrayList;

import com.isenteam.betgames.bdd.Mongo;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class BetgoingCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	
	public BetgoingCommand(GuildMessageReceivedEvent event, String[] args) {
		
		this.event = event;
		this.args = args;	
		
	}
	
	public void betGoing() 
	{ 
		ShowMessage mess = new ShowMessage(event);
		
		//in betgoing there must be only one arg, if there are several args return an error
		if(this.args.length > 1) 
		{
			mess.showMess("🔴 Please try again, you have entered too many arguments (see #info).", 0xCA0707);
		}
		else if(this.args.length == 1)
		{
			Mongo mongo = new Mongo("Bets");
			ArrayList<Bet> tab = mongo.displayBetNotDone(this.event.getAuthor().getId()); 
			
			if(tab.size() == 0)
			{
				BetCommand.sendResult(this.event.getAuthor(), "🔴 There are no current bets.");
			}

			//boucle sur l'array list
			for(int i = 0; i < tab.size(); i++)
			{
				BetCommand.sendResult(this.event.getAuthor(), "💬 There is a bet of " + tab.get(i).getBet() 
				+ " on the team " + tab.get(i).getTeam() + " (odd : " + tab.get(i).getOdd() 
				+ ") for the game " + tab.get(i).getGameId() + ".");
			}
		}	
	}
}
