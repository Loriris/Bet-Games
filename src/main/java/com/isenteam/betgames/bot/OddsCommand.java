package com.isenteam.betgames.bot;

import java.util.Arrays;

import com.isenteam.betgames.API.InfoAPI;
import com.isenteam.betgames.bdd.Mongo;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class OddsCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args, teamName;
	private float coteEq1, coteEq2;
	
	public OddsCommand(GuildMessageReceivedEvent event, String[] args, 
			String[] teamName, float coteEq1, float coteEq2) {
		
		this.event = event;
		this.args = args;
		this.teamName = teamName;
		this.coteEq1 = coteEq1;
		this.coteEq2 = coteEq2;
	}
	
	public void Odds() 
	{
		ShowMessage mess = new ShowMessage(event);
				
		//in odds there must be 3 args, if there are more than 3 args return an error
		if(this.args.length > 3)
		{
			mess.showMess("🔴 Veuillez réassayer, vous avez saisi trop d'arguments (voir #info).", 0xCA0707);
		}
		//in odds there must be 3 args, if there are less than 3 args return an error
		if(this.args.length < 3)
		{
			mess.showMess("🔴Veuillez réassayer, vous n'avez pas saisi assez d'arguments (voir #info).", 0xCA0707);
		}
		//if the team exist in the array teamName
		if(this.args.length == 3)
		{
			Mongo mongo = new Mongo("Party");
		    if(Boolean.compare(mongo.searchForExistingParty(this.args[2]), false) == 0)
		    {
		    	mess.showMess("🔴 L'ID saisie n'est pas valide, verifiez les "
		    	+ "parties disponibles avec la commande #games.", 0xCA0707);
		    }
		    else
		    {
				if(Arrays.stream(this.teamName).anyMatch(this.args[1]::equals) == false)
				{
					mess.showMess("🔴 L'équipe sélectionnée n'est pas valide, "
					+ "saisir #teams pour voir les équipes disponibles.", 0xCA0707);
				}
				
				for(int i = 0; i<this.teamName.length; i++) 
				{
					if(this.args[1].equalsIgnoreCase(this.teamName[i]))
					{
						InfoAPI infos = new InfoAPI(this.args[2]);
			
	
						// we get the odd of each teams with cal
						CalculCote cal = new CalculCote(infos);
						try {
							cal.calcul();
						} catch (UnirestException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				
						
						this.coteEq1 = cal.coteEq1;
						this.coteEq2 = cal.coteEq2;
						
						float [] teamValue = {this.coteEq1, this.coteEq2};
			 
						mess.showMess("Cote à " + teamValue[i] + " pour l'équipe " + this.teamName[i] + ".", 0x1A93D8);
					}	
				}
		    }
		}
	}
}
