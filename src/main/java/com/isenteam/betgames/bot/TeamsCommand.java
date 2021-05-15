package com.isenteam.betgames.bot;

import com.isenteam.betgames.API.InfoAPI;
import com.isenteam.betgames.bdd.Mongo;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class TeamsCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	
	public TeamsCommand(GuildMessageReceivedEvent event, String[] args) {
		
		this.event = event;
		this.args = args;	
	}
	
	public void Teams() {
		
		ShowMessage mess = new ShowMessage(event);
		
		//in team there must 2 arg, if there are more than 2 args return an error
		if(this.args.length > 2) 
		{
			mess.showMess("🔴 Please try again, you have entered too many arguments (see #info).", 0xCA0707);
		}
		
		//in team there must 2 arg, if there are less than 2 args return an error
		if(this.args.length < 2) 
		{
			 mess.showMess("🔴 Please try again by checking if you have entered the game ID (see #info).", 0xCA0707);
		}
		
		//if the team exist in the array teamName
		if(this.args.length == 2)
		{
			Mongo mongo = new Mongo("Party");
		    if(Boolean.compare(mongo.searchForExistingParty(this.args[1]), false) == 0)
		    {
		    	mess.showMess("🔴 The ID entered isn't valid, check the available games with the command #games.", 0xCA0707);
		    }
		    else
		    {
				InfoAPI infos = new InfoAPI(this.args[1]);
				infos.partyInfoMongo();
				try
				{
					infos.retrieveParticipantsInfo();
				}
				catch (UnirestException e)
				{
					e.printStackTrace();
				}
				for(int i = 0; i<infos.getParticipant().length; i++) 
				{
					String colorTeam = "";
					if(infos.getParticipant()[i].getTeam() == 100)
					{
						colorTeam = "blue";
					}
					else if(infos.getParticipant()[i].getTeam() == 200)
					{
						colorTeam = "red";
					}
					mess.showMess("Player " + infos.getParticipant()[i].getName() + " of the [" + colorTeam 
					+ "] team is playing with character [" + infos.getParticipant()[i].getChampion() + "].", 0x1A93D8);
				}
				mess.showMess("Enter #odds [team_color] [game_id] (team_color: blue | red) "
				+ "to know the odds on this game (game_id: " + this.args[1] + ").", 0x9003fc);
		    }
		}
	}
}
