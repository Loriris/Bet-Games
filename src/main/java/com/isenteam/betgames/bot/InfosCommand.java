package com.isenteam.betgames.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class InfosCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	
	public InfosCommand(GuildMessageReceivedEvent event, String[] args) {
		this.event = event;
		this.args = args;
	}
	
	public void displayInfo() {
		//in info there must be only one arg, if there are several args return an error
		if(this.args.length > 1)
		{
			 this.event.getChannel().sendMessage("🔴 Please try again by entering #info because you gave too many arguments.").queue();
		}
		else
		{
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Commands list :");
			info.addField("To connect to a game :", "#connection [player's_nickname_that_you're_watching] [region]"
					+ " (for the player's name, it is necessary to write in one word), "
					+ "you can bet on live games with porofessor: https://porofessor.gg/ ", false);
			info.addField("To know the available games and their ID :", "#games", false);
			info.addField("To know the available teams :", "#teams [game_id]", false);
			info.addField("To know the odd :", "#odds [team_color] [game_id]", false);
			info.addField("To make a bet :", "#bet [team_color] [amount_to_bet] [game_id] (the amount can have the form x or x.y)", false);
			info.addField("To know the current bets :", "#betgoing", false);
			info.addField("To know your wallet :", "#wallet", false);
			info.addField("Servers list :", "\"BR1\", \"EUN1\", \"EUW1\", \"LA1\", \r\n" + 
			"\"LA2\", \"NA1\", \"OCE\", \"OC1\", \"RU1\", \"TR1\", \"JP1\", \"KR\", \"PBE\"", false);
			info.addField("Visit our website for further information (only fr for the moment)", "https://playbet-games.fr/", false);
			info.setColor(0x9003fc);
			this.event.getChannel().sendMessage(info.build()).queue();
			info.clear(); //Resets this builder to default state.
		}
	}
}
