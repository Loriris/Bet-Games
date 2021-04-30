package com.isenteam.betgames.bot;

import com.isenteam.betgames.API.InfoAPI;
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
		
		//in info there must 2 arg, if there are more than 2 args return an error
		if(this.args.length > 2) 
		{
			 this.event.getChannel().sendMessage("🔴Veuillez réassayer, "
			 + "vous avez saisi trop d'arguments (voir #info)").queue();
		}
		
		//if the team exist in the array teamName
		if(this.args.length == 2)
		{
			InfoAPI infos = new InfoAPI(this.args[1]);
			infos.PartyInfoMongo();
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
				this.event.getChannel().sendMessage("Equipe " + infos.getParticipant()[i].getTeam() 
				+ "  disponible," + " champion " + infos.getParticipant()[i].getChampion()).queue();
			}				
		}
	}
}
