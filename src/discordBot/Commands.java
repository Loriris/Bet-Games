package discordBot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		//Pour lire les arguments saisies sur Discord
		String[] args = event.getMessage().getContentRaw().split("\\s+");
		
		if(args[0].equalsIgnoreCase(Main.prefix + "hello"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Hello everybody :)").queue();
		}
/*		
		if(args[1].equalsIgnoreCase("cote"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote à ").queue();
		}
			
		if(args[2].equalsIgnoreCase("A"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("" + Main.getA()+ " pour l'équipe A.").queue();
		}
*/		
		/*	
		if(args[1].equalsIgnoreCase("cote") && args[2].equalsIgnoreCase("B"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote à " + Main.getB()+ " pour l'équipe B.").queue();
		}
		
		if(args[1].equalsIgnoreCase("bet") && args[2].equalsIgnoreCase(""))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote à " + Main.getB()+ " pour l'équipe B.").queue();
		}
		*/
		
		
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
	/*	
		if(args[0].equalsIgnoreCase(Main.prefix + "bet") && args[1].equalsIgnoreCase("A"))
		{
			if(args[2].isEmpty() == true) //ca n'a pas fonctionné
			{
				event.getChannel().sendMessage("Veuillez réassayer en entrant la somme que vous voulez parier").queue();
			}
			else
			{
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Paris validé.").queue();
				//faire une action
			}
			
		}
	 */
		
		if(args[0].equalsIgnoreCase(Main.prefix + "bet") && args[1].equalsIgnoreCase("A") && args[2].isEmpty() == false)
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Paris validé.").queue();
			//faire une action
		}
		else if(args[0].equalsIgnoreCase(Main.prefix + "bet") && args[1].equalsIgnoreCase("A") && args[2].equalsIgnoreCase(""))
		{
			//réaliser une gestion d'erreur, surment avec try catch, throw ?
			event.getChannel().sendMessage("Veuillez réassayer en entrant la somme que vous voulez parier").queue();
		}
		
	}
	
}
