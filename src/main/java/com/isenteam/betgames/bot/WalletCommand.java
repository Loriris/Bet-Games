package com.isenteam.betgames.bot;

import com.isenteam.betgames.bdd.Mongo;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class WalletCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	
	public WalletCommand(GuildMessageReceivedEvent event, String[] args) 
	{
		this.args = args;
		this.event = event;
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
			Mongo mongo = new Mongo("Users");
			System.out.println("Flag 1");
			User userWallet = mongo.retreiveUser(this.event.getAuthor().getId());
			System.out.println("Flag 2");
			if(userWallet == null)
			{
				System.out.println("Flag 3");
				BetCommand.sendResult(this.event.getAuthor(), "💬 Your wallet is empty.");
			}
			else
			{
				System.out.println("Flag 4");
				BetCommand.sendResult(this.event.getAuthor(), "💬 You currently have " + userWallet.getGain() 
				+ " tokens in your wallet, you have a total of " + userWallet.getWin() + " win(s) and " 
				+ userWallet.getLose() + " losse(s).");
			}
		}
	}
}
