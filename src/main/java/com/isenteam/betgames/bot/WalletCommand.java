package com.isenteam.betgames.bot;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class WalletCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	
	public WalletCommand(GuildMessageReceivedEvent event, String[] args) 
	{
		this.args = args;
	}
	
	public void walletComm()
	{
		ShowMessage mess = new ShowMessage(event);
		
		//in wallet there must be only one arg, if there are several args return an error
		if(this.args.length > 1) 
		{
			mess.showMess("🔴 Please try again, you have entered too many arguments (see #info).", 0xCA0707);
		}
		else
		{
			
		}
	}

}
