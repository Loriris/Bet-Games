package com.isenteam.betgames.bot;


import java.util.Arrays;

import com.isenteam.betgames.API.InfoAPI;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	
	//prefix to used for the bot to recognize that it's being spoken to
	private static String prefix = "#";
	private String [] teamName = {"blue", "red"};
	
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
		
		
		String[] commandsName = {"#info", "#odds", "#games", "#bet", "#teams", "#betgoing", "#wallet", "#connection"};
		
		if(Arrays.stream(commandsName).anyMatch(args[0]::equals) == false && args[0].substring(0, 1) == "#")
		{
			mess.showMess("🔴 Please try again, the command you entered doesn't exist (see #info).", 0xCA0707);
		}
		else
		{
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
				OddsCommand oddsCom = new OddsCommand(event, args, teamName, coteEq1, coteEq2);
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
				BetCommand betCom = new BetCommand(event, args, teamName, coteEq1, coteEq2, regionServer);
				betCom.BetComm();
			}

			/*--------------------------------------------------------------------------------------------*/
			
			// allow to know the team available 
			if(args[0].equalsIgnoreCase(prefix + "teams"))
			{
				TeamsCommand teamsCom = new TeamsCommand(event, args);
				teamsCom.Teams();
			}
			
			/*--------------------------------------------------------------------------------------------*/
			
			// allow to know the bet you take on unfinished games 
			if(args[0].equalsIgnoreCase(prefix + "betgoing"))
			{
				BetgoingCommand betgoingCom = new BetgoingCommand(event, args);
				betgoingCom.betGoing();
			}
			
			/*--------------------------------------------------------------------------------------------*/
			
			// allows you to know your wallet
			if(args[0].equalsIgnoreCase(prefix + "wallet"))
			{
				WalletCommand walletCom = new WalletCommand(event, args);
				walletCom.walletComm();
			}
			
			/*--------------------------------------------------------------------------------------------*/
			
			// Return the pseudo and the region to use in the LoL API
			if(args[0].equalsIgnoreCase(prefix + "connection"))
			{
				// Check how many arguments were passed in, we need 3 args
			    if(args.length < 3)
			    {
			        mess.showMess("🔴 Please try again by checking if you've entered "
			        		+ "the correct nickname and/or region (see #info).", 0xCA0707);
			        
			    }
			    
			    if(args.length > 3)
			    {
			    	mess.showMess("🔴 Please try again, you have entered too many arguments (see #info).", 0xCA0707);
			    }
			    
			    if(args.length == 3)
			    {	
			    	regionServer = args[2]; //region server
			    	
		    		if(Arrays.stream(serverName).anyMatch(args[2]::equals) == false)
		    		{
		    			mess.showMess("🔴 Please try again, the server you entered doesn't exist (see #info).", 0xCA0707);
		    		}
		    		else 
		    		{
		    			try
		    			{
							infos = new InfoAPI(args[1], args[2]);
							int resultat = infos.partyInfo();
							if(resultat == 404)
							{
								mess.showMess("🔴 Please try again, the player's nickname you entered doesn't exist.", 0xCA0707);
							}
							else
							{
								mess.showMess("🟢 Connected.", 0x27AE1E);
							}
						} catch (UnirestException e) {
							e.printStackTrace();
						} 
		    		}
			    }
			}
		}				
	}	
}
