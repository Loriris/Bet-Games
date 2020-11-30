package discordBot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		
		// to read arguments type on discord
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		
		// type #hello to say hello to the bot
		if(args[0].equalsIgnoreCase(Main.prefix + "hello"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Hello everybody :)").queue();
		}

		
		// allow to know the odds of betting of the team you that you want
		// improvement to do with the team selection
		if(args[0].equalsIgnoreCase(Main.prefix + "cote") && args[1].equalsIgnoreCase("A"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote à " + Main.getA()+ " pour l'équipe A.").queue();
		}
		
		if(args[0].equalsIgnoreCase(Main.prefix + "cote") && args[1].equalsIgnoreCase("B"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote à " + Main.getB()+ " pour l'équipe B.").queue();
		}
		
	
		//allow to bet on the selected team
		if(args[0].equalsIgnoreCase(Main.prefix + "bet"))
		{
			// Check how many arguments were passed in
		    if(args.length == 2)
		    {
		        event.getChannel().sendMessage("Veuillez réassayer en verifiant si vous "
		        		+ "avez bien saisi l'équipe et le montant à parier").queue();
		        //System.exit(0);
		    }
		    if(args.length == 3)
		    {
		    	if(args[1].equalsIgnoreCase("A") && args[2].isEmpty() == false)
			    {
			    	event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Paris validé.").queue();
					// perform an action 
			    }
		    }
		    
		}

	
	}
	
}
