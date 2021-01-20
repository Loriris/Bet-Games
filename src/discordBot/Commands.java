package discordBot;

import java.util.Arrays;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	
	//prefix to used for the bot to recognize that it's being spoken to
	private static String prefix = "#";
	private static String [] teamName = {"A", "B", "C" };
	private static String [] teamValue = {"2", "5", "8" };
		
	// LoL server name 
	private static String [] serverName = {"BR1", "EUN1", "EUW1", "LA1", 
	"LA2", "NA1", "OCE", "OC1", "RU1", "TR1", "JP1", "KR", "PBE" };

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		int i, money;
		
		// to read arguments type on discord
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
/*--------------------------------------------------------------------------------------------*/		
		// type #info to display all commands
		if(args[0].equalsIgnoreCase(prefix + "info"))
		{
			//in info there must be only one arg, if there are several args return an error
			if(args.length > 1)
			{
				 event.getChannel().sendMessage("🔴Veuillez réassayer en saisissant #info "
				 + "car vous avez donné trop d'arguments").queue();
			}
			
			else
			{
				EmbedBuilder info = new EmbedBuilder();
				info.setTitle("Liste des commandes :");
				info.addField("Pour connaitre les équipes disponibles :", "#teams", false);
				info.addField("Pour connaitre une cote :", "#cote nom_de_l'équipe", false);
				info.addField("Pour faire un paris :", "#bet nom_de_l'équipe somme_engagée", false);
				info.addField("Pour se connecter à une partie :", "#connexion pseudo_joueur region", false);
				info.addField("Liste serveurs :", "\"BR1\", \"EUN1\", \"EUW1\", \"LA1\", \r\n" + 
				"\"LA2\", \"NA1\", \"OCE\", \"OC1\", \"RU1\", \"TR1\", \"JP1\", \"KR\", \"PBE\"", false);
				info.setColor(0x9003fc);
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage(info.build()).queue();
				info.clear(); //Resets this builder to default state.
			}
		}

/*--------------------------------------------------------------------------------------------*/
		// allow to know the odds of betting of the team you that you want	
		if(args[0].equalsIgnoreCase(prefix + "cote"))
		{	
			//in cote there must be 2 args, if there are more than 2 args return an error
			if(args.length > 2)
			{
				 event.getChannel().sendMessage("🔴Veuillez réassayer, "
				 + "vous avez saisi trop d'arguments (voir #info).").queue();
			}
			//in cote there must be 2 args, if there are less than 2 args return an error
			if(args.length < 2)
			{
				 event.getChannel().sendMessage("🔴Veuillez réassayer, "
				 + "vous n'avez pas saisi assez d'arguments (voir #info).").queue();
			}
			//if the team exist in the array teamName
			if(args.length == 2)
			{
				if(Arrays.stream(teamName).anyMatch(args[1]::equals) == false)
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("🔴 L'équipe sélectionnée n'est pas valide, "
						+ "saisisser #teams pour voir les équipes disponibles.").queue();
				}
				
				for(i = 0; i<teamName.length; i++) 
				{
					if(args[1].equalsIgnoreCase(teamName[i]))
					{
						event.getChannel().sendTyping().queue();
						event.getChannel().sendMessage("Cote à " + teamValue[i] 
						+ " pour l'équipe " + teamName[i] + ".").queue();
					}			
				}
			}
		}

/*--------------------------------------------------------------------------------------------*/		
		//allow to bet on the selected team
		if(args[0].equalsIgnoreCase(prefix + "bet"))
		{
			// Check how many arguments were passed in, we need 3 args
		    if(args.length < 3)
		    {
		        event.getChannel().sendMessage("🔴 Veuillez réassayer en verifiant si vous "
		        + "avez bien saisi l'équipe et/ou le montant à parier (voir #info).").queue();
		    }
		    
		    if(args.length > 3)
		    {
		        event.getChannel().sendMessage("🔴 Veuillez réassayer, "
		        + "vous avez saisi trop d'arguments (voir #info).").queue();
		    }
		    
		    if(args.length == 3)
		    {
		    	if(Arrays.stream(teamName).anyMatch(args[1]::equals) == false)
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("🔴 L'équipe sélectionnée n'est pas valide, "
						+ "saisisser #teams pour voir les équipes disponibles.").queue();
				}
		    	
		    	for(i = 0; i<teamName.length; i++) 
				{
					if(args[1].equalsIgnoreCase(teamName[i]) && args[2].isEmpty() == false)
					{
						money = Integer.parseInt(args[2]);
						if(money <=0 || money>100000)
						{
							event.getChannel().sendTyping().queue();
							event.getChannel().sendMessage("🔴 Le montant selectionné n'est pas valide, "
								+ "les valeurs doivent être comprises entre 1 et 100 000.").queue();
						}
						else
						{
							event.getChannel().sendTyping().queue();
							event.getChannel().sendMessage("🟢 Paris validé.").queue();
							// perform an action to save the amount of money that was bet
						}
					}
				}
		    }  
		}

/*--------------------------------------------------------------------------------------------*/
		// allow to know the team available 
		if(args[0].equalsIgnoreCase(prefix + "teams"))
		{
			//in info there must be only one arg, if there are several args return an error
			if(args.length > 1) 
			{
				 event.getChannel().sendMessage("🔴Veuillez réassayer, "
				 + "vous avez saisi trop d'arguments (voir #info)").queue();
			}
			
			//if the team exist in the array teamName
			else
			{
				for(i = 0; i<teamName.length; i++) 
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Equipe " + teamName[i] 
					+ "  disponible.").queue();
				}				
			}
		}
		
/*--------------------------------------------------------------------------------------------*/
		// Return the pseudo and the region to use in the LoL API
		if(args[0].equalsIgnoreCase(prefix + "connexion"))
		{
			// Check how many arguments were passed in, we need 3 args
		    if(args.length < 3)
		    {
		        event.getChannel().sendMessage("🔴 Veuillez réassayer en verifiant si vous "
		        + "avez bien saisi le bon pseudo et/ou la bonne région (voir #info).").queue();
		    }
		    
		    if(args.length > 3)
		    {
		        event.getChannel().sendMessage("🔴 Veuillez réassayer, "
		        + "vous avez saisi trop d'arguments (voir #info).").queue();
		    }
		    
		    if(args.length == 3)
		    {
		    	Main.gameLog[0] = args[1]; 
		    	Main.gameLog[1] = args[2];
		    	
	    		if(Arrays.stream(serverName).anyMatch(args[2]::equals) == false)
	    		{
	    			event.getChannel().sendMessage("🔴 Veuillez réassayer, "
	    			+ "le serveur saisie n'existe pas (voir #info).").queue();
	    		}
		    }
		}
		
/*--------------------------------------------------------------------------------------------*/
	}	
}
