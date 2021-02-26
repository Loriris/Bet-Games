package discordBot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class InfosCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	
	public InfosCommand(GuildMessageReceivedEvent event, String[] args) {
		this.event = event;
		this.args = args;
	}
	
	public void displayInfo() {
		//in info there must be only one arg, if there are several args return an error
		if(this.args.length > 1)
		{
			 this.event.getChannel().sendMessage("🔴Veuillez réassayer en saisissant #info "
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
			this.event.getChannel().sendMessage(info.build()).queue();
			info.clear(); //Resets this builder to default state.
		}
	}
}
