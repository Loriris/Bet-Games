package com.isenteam.betgames.bot;


import java.util.Arrays;
import com.isenteam.betgames.API.InfoAPI;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	
	//prefix to used for the bot to recognize that it's being spoken to
	private static String prefix = "#";
	private String [] teamName = {"100", "200"};
	
	private InfoAPI infos;
		
	// LoL server name 
	private static String [] serverName = {"BR1", "EUN1", "EUW1", "LA1", 
	"LA2", "NA1", "OCE", "OC1", "RU1", "TR1", "JP1", "KR", "PBE" };

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		float  coteEq1 = 0, coteEq2 = 0;
		String regionServer = null;
		
		// to read arguments type on discord
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		ShowMessage mess = new ShowMessage(event);
		
/*--------------------------------------------------------------------------------------------*/	
		
		// type #info to display all commands
		if(args[0].equalsIgnoreCase(prefix + "info"))
		{
			InfosCommand infoCom = new InfosCommand(event, args);
			infoCom.displayInfo();
		}

/*--------------------------------------------------------------------------------------------*/
		
		// allow to know the odds of betting of the team you that you want	
		if(args[0].equalsIgnoreCase(prefix + "odds"))
		{	
			OddsCommand oddsCom = new OddsCommand(event, args, teamName, coteEq1, coteEq2, infos);
			oddsCom.Odds();
		}
		
/*--------------------------------------------------------------------------------------------*/
		
		// allow to know the games available 
		if(args[0].equalsIgnoreCase(prefix + "games"))
		{
			GamesCommand gamesCom = new GamesCommand(event, args);
			gamesCom.gamesComm();
		}

/*--------------------------------------------------------------------------------------------*/
		
		//allow to bet on the selected team
		if(args[0].equalsIgnoreCase(prefix + "bet"))
		{
			BetCommand betCom = new BetCommand(event, args, teamName, coteEq1, coteEq2, infos, regionServer);
			betCom.BetComm();
		}

/*--------------------------------------------------------------------------------------------*/
		
		// allow to know the team available 
		if(args[0].equalsIgnoreCase(prefix + "teams"))
		{
			TeamsCommand teamsCom = new TeamsCommand(event, args, infos);
			teamsCom.Teams();
		}
		
/*--------------------------------------------------------------------------------------------*/
		
		// Return the pseudo and the region to use in the LoL API
		if(args[0].equalsIgnoreCase(prefix + "connexion"))
		{
			// Check how many arguments were passed in, we need 3 args
		    if(args.length < 3)
		    {
		        mess.showMess("🔴 Veuillez réassayer en verifiant si vous "
		        + "avez bien saisi le bon pseudo et/ou la bonne région (voir #info).", 0xCA0707);
		        
		    }
		    
		    if(args.length > 3)
		    {
		    	mess.showMess("🔴 Veuillez réassayer, vous avez saisi trop d'arguments (voir #info).", 0xCA0707);
		    }
		    
		    if(args.length == 3)
		    {	
		    	regionServer = args[2]; //region server
		    	
	    		if(Arrays.stream(serverName).anyMatch(args[2]::equals) == false)
	    		{
	    			mess.showMess("🔴 Veuillez réassayer, le serveur saisie n'existe pas (voir #info).", 0xCA0707);
	    		}
	    		else
	    		{
	    			try
	    			{
						infos = new InfoAPI(args[1], args[2]);
						infos.PartyInfo();
						//System.out.println(infos.getPartyInfo());
					} catch (UnirestException e) {
						e.printStackTrace();
					}
	    			mess.showMess("🟢 connexion effectuée.", 0x27AE1E);
	    		}
		    }
		}			
	}	
	
/*--------------------------------------------------------------------------------------------*/
	/*
	// send a private message to the gambler to inform him if he has won or lost
	public static void sendResult(User user, String content) {
	    user.openPrivateChannel().queue(channel -> {
	        channel.sendMessage(content).queue();
	    });
	}	*/
}
