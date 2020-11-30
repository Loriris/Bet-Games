package discordBot;


import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		int i;
		
		// to read arguments type on discord
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
/*--------------------------------------------------------------------------------------------*/		
		// type #hello to say hello to the bot
		if(args[0].equalsIgnoreCase(Main.prefix + "hello"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Hello everybody :)").queue();
		}
		
/*--------------------------------------------------------------------------------------------*/		
		// type #info to display all commands
		if(args[0].equalsIgnoreCase(Main.prefix + "info"))
		{
			EmbedBuilder info = new EmbedBuilder();
			info.setTitle("Liste des commandes :");
			info.addField("Pour connaitre une cote :", "#cote nom_de_l'équipe", false);
			info.addField("Pour faire un paris :", "#bet nom_de_l'équipe somme_engagée", false);
			info.setColor(0x9003fc);
			
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage(info.build()).queue();
			info.clear(); //Resets this builder to default state.
		}

	
/*--------------------------------------------------------------------------------------------*/
		// allow to know the odds of betting of the team you that you want	
		if(args[0].equalsIgnoreCase(Main.prefix + "cote"))
		{
			if(args.length > 2)
			{
				 event.getChannel().sendMessage("🔴Veuillez réassayer, "
				 + "vous avez saisi trop d'arguments (voir #info)").queue();
			}
			
			//if the team exist in the array teamName
			for(i = 0; i<3; i++) 
			{
				if(args[1].equalsIgnoreCase(Main.teamName[i]))
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Cote à " + Main.teamValue[i] 
					+ " pour l'équipe " + Main.teamName[i] + ".").queue();
				}
			}
		}
		

/*--------------------------------------------------------------------------------------------*/		
		//allow to bet on the selected team
		if(args[0].equalsIgnoreCase(Main.prefix + "bet"))
		{
			// Check how many arguments were passed in
		    if(args.length == 2)
		    {
		        event.getChannel().sendMessage("🔴 Veuillez réassayer en verifiant si vous "
		        + "avez bien saisi l'équipe et le montant à parier (voir #info)").queue();
		    }
		    if(args.length == 3)
		    {
		    	for(i = 0; i<3; i++) 
				{
					if(args[1].equalsIgnoreCase(Main.teamName[i]) && args[2].isEmpty() == false)
					{
						event.getChannel().sendTyping().queue();
						event.getChannel().sendMessage("🟢 Paris validé.").queue();
						// perform an action to save the amount of money that was bet
					}
				}
		    }  
		}

/*--------------------------------------------------------------------------------------------*/
		
	}	
}
