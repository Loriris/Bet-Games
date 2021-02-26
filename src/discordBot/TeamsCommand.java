package discordBot;

import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class TeamsCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	private InfoAPI infos;
	
	public TeamsCommand(GuildMessageReceivedEvent event, String[] args, InfoAPI infos) {
		
		this.event = event;
		this.args = args;
		this.infos = infos;		
	}
	
	public void Teams() {
		
		//in info there must be only one arg, if there are several args return an error
		if(this.args.length > 1) 
		{
			 this.event.getChannel().sendMessage("🔴Veuillez réassayer, "
			 + "vous avez saisi trop d'arguments (voir #info)").queue();
		}
		
		//if the team exist in the array teamName
		else
		{
			try
			{
				this.infos.retrieveParticipantsInfo();
			}
			catch (UnirestException e)
			{
				e.printStackTrace();
			}
			for(int i = 0; i<this.infos.getParticipant().length; i++) 
			{
				this.event.getChannel().sendMessage("Equipe " + this.infos.getParticipant()[i].getTeam() 
				+ "  disponible," + " champion " + this.infos.getParticipant()[i].getChampion()).queue();
			}				
		}
	}

}
