package com.isenteam.betgames.bot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ShowMessage {
	
	private GuildMessageReceivedEvent event;
	
	public ShowMessage(GuildMessageReceivedEvent event) 
	{
		this.event = event;
	}
	
	public void showMess(String message, int color)
	{
		EmbedBuilder response = new EmbedBuilder();
		response.setTitle(message);
		response.setColor(color);
		this.event.getChannel().sendMessage(response.build()).queue();
		response.clear(); //Resets this builder to default state.
	}
}
