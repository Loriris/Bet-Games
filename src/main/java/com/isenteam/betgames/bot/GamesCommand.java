package com.isenteam.betgames.bot;

import java.util.ArrayList;

import com.isenteam.betgames.bdd.Mongo;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class GamesCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	
	public GamesCommand(GuildMessageReceivedEvent event, String[] args) {
		this.event = event;
		this.args = args;
	}
	
	public void gamesComm() 
	{
		
		ShowMessage mess = new ShowMessage(event);
		
		//in info there must be only one arg, if there are several args return an error
		if(this.args.length > 1) 
		{
			mess.showMess("🔴 Veuillez réessayer, vous avez saisi trop d'arguments (voir #info).", 0xCA0707);
		}
		
		else
		{
			Mongo games = new Mongo("Party");
			ArrayList<ActiveGames> tabRecup = new ArrayList<>();
			tabRecup = games.displayGames();
			for(int i = 0; i < tabRecup.size(); i++)
			{
				mess.showMess("ID: " + tabRecup.get(i).getId() + "		Type: " + tabRecup.get(i).getType(), 0x1A93D8);
			}
		}
	}
}
