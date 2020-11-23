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
			event.getChannel().sendMessage("Cote � ").queue();
		}
			
		if(args[2].equalsIgnoreCase("A"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("" + Main.getA()+ " pour l'�quipe A.").queue();
		}
*/		
		/*	
		if(args[1].equalsIgnoreCase("cote") && args[2].equalsIgnoreCase("B"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote � " + Main.getB()+ " pour l'�quipe B.").queue();
		}
		
		if(args[1].equalsIgnoreCase("bet") && args[2].equalsIgnoreCase(""))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote � " + Main.getB()+ " pour l'�quipe B.").queue();
		}
		*/
		
		
		if(args[0].equalsIgnoreCase(Main.prefix + "cote") && args[1].equalsIgnoreCase("A"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote � " + Main.getA()+ " pour l'�quipe A.").queue();
		}
		
		if(args[0].equalsIgnoreCase(Main.prefix + "cote") && args[1].equalsIgnoreCase("B"))
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Cote � " + Main.getB()+ " pour l'�quipe B.").queue();
		}
	/*	
		if(args[0].equalsIgnoreCase(Main.prefix + "bet") && args[1].equalsIgnoreCase("A"))
		{
			if(args[2].isEmpty() == true) //ca n'a pas fonctionn�
			{
				event.getChannel().sendMessage("Veuillez r�assayer en entrant la somme que vous voulez parier").queue();
			}
			else
			{
				event.getChannel().sendTyping().queue();
				event.getChannel().sendMessage("Paris valid�.").queue();
				//faire une action
			}
			
		}
	 */
		
		if(args[0].equalsIgnoreCase(Main.prefix + "bet") && args[1].equalsIgnoreCase("A") && args[2].isEmpty() == false)
		{
			event.getChannel().sendTyping().queue();
			event.getChannel().sendMessage("Paris valid�.").queue();
			//faire une action
		}
		else if(args[0].equalsIgnoreCase(Main.prefix + "bet") && args[1].equalsIgnoreCase("A") && args[2].equalsIgnoreCase(""))
		{
			//r�aliser une gestion d'erreur, surment avec try catch, throw ?
			event.getChannel().sendMessage("Veuillez r�assayer en entrant la somme que vous voulez parier").queue();
		}
		
	}
	
}
