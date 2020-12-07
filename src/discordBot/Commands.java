package discordBot;



import java.util.Arrays;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		int i, money;
		
		// to read arguments type on discord
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		
		if(args[0].equalsIgnoreCase(Main.prefix + "info"))
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
				info.setColor(0x9003fc);
				
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage(info.build()).queue();
				info.clear(); //Resets this builder to default state.
			}

		}

	
/*--------------------------------------------------------------------------------------------*/
		// allow to know the odds of betting of the team you that you want	
		if(args[0].equalsIgnoreCase(Main.prefix + "cote"))
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
				if(Arrays.stream(Main.teamName).anyMatch(args[1]::equals) == false)
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("🔴 L'équipe sélectionnée n'est pas valide, "
						+ "saisisser #teams pour voir les équipes disponibles.").queue();
				}
				
				for(i = 0; i<Main.teamName.length; i++) 
				{
					if(args[1].equalsIgnoreCase(Main.teamName[i]))
					{
						event.getChannel().sendTyping().queue();
						event.getChannel().sendMessage("Cote à " + Main.teamValue[i] 
						+ " pour l'équipe " + Main.teamName[i] + ".").queue();
					}			
				}
			}
		}
		

/*--------------------------------------------------------------------------------------------*/		
		//allow to bet on the selected team
		if(args[0].equalsIgnoreCase(Main.prefix + "bet"))
		{
			// Check how many arguments were passed in, we need 3 args
		    if(args.length < 3)
		    {
		        event.getChannel().sendMessage("🔴 Veuillez réassayer en verifiant si vous "
		        + "avez bien saisi l'équipe et/ou le montant à parier (voir #info).").queue();
		    }
		    
		    if(args.length == 3)
		    {
		    	if(Arrays.stream(Main.teamName).anyMatch(args[1]::equals) == false)
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("🔴 L'équipe sélectionnée n'est pas valide, "
						+ "saisisser #teams pour voir les équipes disponibles.").queue();
				}
		    	
		    	for(i = 0; i<Main.teamName.length; i++) 
				{
					if(args[1].equalsIgnoreCase(Main.teamName[i]) && args[2].isEmpty() == false)
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
		if(args[0].equalsIgnoreCase(Main.prefix + "teams"))
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
				for(i = 0; i<Main.teamName.length; i++) 
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Equipe " + Main.teamName[i] 
					+ "  disponible.").queue();
				}				
			}
		}
		
	
/*--------------------------------------------------------------------------------------------*/
		
	}	
}
