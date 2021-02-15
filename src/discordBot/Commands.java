package discordBot;

import java.io.Console;
import java.util.Arrays;
import java.util.Random;

import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Commands extends ListenerAdapter{
	
	//prefix to used for the bot to recognize that it's being spoken to
	private static String prefix = "#";
	private String [] teamName = {"100", "200"};
	private String [] teamValue = {"2", "5"};
	
	private InfoAPI infos;
		
	// LoL server name 
	private static String [] serverName = {"BR1", "EUN1", "EUW1", "LA1", 
	"LA2", "NA1", "OCE", "OC1", "RU1", "TR1", "JP1", "KR", "PBE" };

	public void onGuildMessageReceived(GuildMessageReceivedEvent event)
	{
		int i, money, nb;
		float odd, gains;
		
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
				info.addField("Pour connaitre une cote :", "#odds [nom_de_l'équipe]", false);
				info.addField("Pour faire un paris :", "#bet [nom_de_l'équipe] [somme_engagée]", false);
				info.addField("Pour se connecter à une partie :", "#connexion [pseudo_joueur] [region]"
						+ " (pour le nom du joueur il faut écrire en un seul mot)", false);
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
		if(args[0].equalsIgnoreCase(prefix + "odds"))
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
						+ "saisir #teams pour voir les équipes disponibles.").queue();
				}
				
				for(i = 0; i<teamName.length; i++) 
				{
					if(args[1].equalsIgnoreCase(teamName[i]))
					{
						event.getChannel().sendTyping().queue();
						event.getChannel().sendMessage("Cote à " + teamValue[i] 
						+ " pour l'équipe " + teamName[i] + ".").queue();
					}	
					//new CalculCote();
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
						+ "saisir #teams pour voir les équipes disponibles.").queue();
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
					
							Random rand = new Random();
							nb =rand.nextInt(10);
							//System.out.println("nb: " + nb);
							
							odd = 1/Float.parseFloat(teamValue[i]);
							//System.out.print("odd: ");
							//System.out.println(odd);
							
							if(nb < odd*10)
							{
								//System.out.println("gagner");
								//event.getChannel().sendMessage("😀 Gagner").queue();
								gains = money * Float.parseFloat(teamValue[i]);;
								Float.toString(gains);
								sendResult(event.getAuthor(), "😀 Gagner, votre gain est de " + gains + "€");
							}
							else
							{
								//System.out.println("perdu");
								//event.getChannel().sendMessage("😥 Perdu").queue();
								sendResult(event.getAuthor(), "😥 Perdu");
							}
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
				infos.retrieveParticipantsInfo();
				for(i = 0; i<infos.getParticipant().length; i++) 
				{
					event.getChannel().sendTyping().queue();
					event.getChannel().sendMessage("Equipe " + infos.getParticipant()[i].getTeam() 
					+ "  disponible," + " champion " + infos.getParticipant()[i].getChampion()).queue();
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
		    {	//args used in InfoAPI (main)
		    	Main.gameLog[0] = args[1]; //pseudo
		    	Main.gameLog[1] = args[2]; //region server
		    	
		    	
	    		if(Arrays.stream(serverName).anyMatch(args[2]::equals) == false)
	    		{
	    			event.getChannel().sendMessage("🔴 Veuillez réassayer, "
	    			+ "le serveur saisie n'existe pas (voir #info).").queue();
	    		}
	    		else
	    		{
	    			try 
	    			{
						infos = new InfoAPI(args[1], args[2]);
						infos.PartyInfo();
						System.out.println(infos.getPartyInfo());
					} catch (UnirestException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    			
	    			event.getChannel().sendMessage("🟢 connexion effectuée.").queue();
	    		}
		    }
		}
		
/*--------------------------------------------------------------------------------------------*/
	}	
	// send a private message to the gambler to inform him if he has won or lost
	static void sendResult(User user, String content) {
	    user.openPrivateChannel().queue(channel -> {
	        channel.sendMessage(content).queue();
	    });
	}
}
