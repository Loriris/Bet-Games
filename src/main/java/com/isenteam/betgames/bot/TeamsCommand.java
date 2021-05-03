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
			mess.showMess("🔴Veuillez réassayer, vous avez "
			+ "saisi trop d'arguments (voir #info)", 0xCA0707);
		}
		
		//in team there must 2 arg, if there are less than 2 args return an error
		if(this.args.length < 2) 
		{
			 mess.showMess("🔴 Veuillez réassayer en verifiant si vous avez bien "
			 + "saisi l'ID de la partie (voir #info).", 0xCA0707);
		}
		
		//if the team exist in the array teamName
		if(this.args.length == 2)
		{
			Mongo mongo = new Mongo("Party");
		    if(Boolean.compare(mongo.searchForExistingParty(this.args[1]), false) == 0)
		    {
		    	mess.showMess("🔴 L'ID saisie n'est pas valide, verifiez les "
		    	+ "parties disponibles avec la commande #games.", 0xCA0707);
		    }
		    else
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
					mess.showMess("Equipe " + infos.getParticipant()[i].getTeam() + "  disponible," + 
					" champion " + infos.getParticipant()[i].getChampion(), 0x1A93D8);
				}		
		    }
		}
	}
}
