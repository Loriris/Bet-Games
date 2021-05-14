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
		
		//in games there must be only one arg, if there are several args return an error
		if(this.args.length > 1) 
		{
			mess.showMess("🔴 Please try again, you have entered too many arguments (see #info).", 0xCA0707);
		}
		
		else
		{
			Mongo games = new Mongo("Party");
			ArrayList<ActiveGames> tabRecup = new ArrayList<>();
			tabRecup = games.displayGames();
			if(tabRecup.size() == 0)
			{
				mess.showMess("🔴 There is no game currently.", 0xCA0707);
			}
			else
			{
				for(int i = 0; i < tabRecup.size(); i++)
				{
					mess.showMess("ID: " + tabRecup.get(i).getId() + "		Connection with player: " + tabRecup.get(i).getPlayer(), 0x1A93D8);
				}
			}
		}
	}
}
