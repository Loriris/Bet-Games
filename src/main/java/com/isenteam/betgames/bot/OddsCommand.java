package com.isenteam.betgames.bot;

import java.util.Arrays;

import com.isenteam.betgames.API.InfoAPI;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class OddsCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args, teamName;
	private float coteEq1, coteEq2;
	private InfoAPI infos;
	
	public OddsCommand(GuildMessageReceivedEvent event, String[] args, 
			String[] teamName, float coteEq1, float coteEq2, InfoAPI infos) {
		
		this.event = event;
		this.args = args;
		this.teamName = teamName;
		this.coteEq1 = coteEq1;
		this.coteEq2 = coteEq2;
		this.infos = infos;
	}
	
	public void Odds() {
		
				
		//in odds there must be 2 args, if there are more than 2 args return an error
		if(this.args.length > 2)
		{
			 this.event.getChannel().sendMessage("🔴Veuillez réassayer, "
			 + "vous avez saisi trop d'arguments (voir #info).").queue();
		}
		//in odds there must be 2 args, if there are less than 2 args return an error
		if(this.args.length < 2)
		{
			 this.event.getChannel().sendMessage("🔴Veuillez réassayer, "
			 + "vous n'avez pas saisi assez d'arguments (voir #info).").queue();
		}
		//if the team exist in the array teamName
		if(this.args.length == 2)
		{
			if(Arrays.stream(this.teamName).anyMatch(this.args[1]::equals) == false)
			{
				this.event.getChannel().sendMessage("🔴 L'équipe sélectionnée n'est pas valide, "
					+ "saisir #teams pour voir les équipes disponibles.").queue();
			}
			
			for(int i = 0; i<this.teamName.length; i++) 
			{
				if(this.args[1].equalsIgnoreCase(this.teamName[i]))
				{
					// we get the odd of each teams with cal
					CalculCote cal = new CalculCote(this.infos);
					try {
						cal.calcul();
					} catch (UnirestException e) {
						e.printStackTrace();
					}
					
					this.coteEq1 = cal.coteEq1;
					this.coteEq2 = cal.coteEq2;
					
					float [] teamValue = {this.coteEq1, this.coteEq2};
					
					this.event.getChannel().sendMessage("Cote à " + teamValue[i] 
					+ " pour l'équipe " + this.teamName[i] + ".").queue();	
				}	
			}
		}
	}
}
