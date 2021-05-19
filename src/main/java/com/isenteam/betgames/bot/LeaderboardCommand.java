package com.isenteam.betgames.bot;

import java.util.ArrayList;
import java.util.Collections;

import com.isenteam.betgames.bdd.Mongo;

import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class LeaderboardCommand {
	
	private GuildMessageReceivedEvent event;
	private String[] args;
	
	public LeaderboardCommand(GuildMessageReceivedEvent event, String[] args) 
	{
		this.args = args;
		this.event = event;
	}
	
	public void leaderboardComm()
	{
		ShowMessage mess = new ShowMessage(event);
		
		if(this.args.length > 1) 
		{
			mess.showMess("🔴 Please try again, you have entered too many arguments (see #info).", 0xCA0707);
		}
		else
		{
			Mongo mongo = new Mongo("Users");
			ArrayList<User> leaderBoard = mongo.retreiveAllUser();
			Collections.sort(leaderBoard, (User u1, User u2) -> Float.compare(u1.getGain(), u2.getGain()));
			Collections.reverse(leaderBoard);
			
			if(leaderBoard == null)
			{
				mess.showMess("🔴 Please try later, the leaderboard is currently empty.", 0xCA0707);
			}
			else
			{
				for (int i = 0; i < leaderBoard.size(); i++) //(Math.round((1/ratioEq1) *100.0)/100.0)
				{
					mess.showMess(leaderBoard.get(i).getName() + " has " + (Math.round((leaderBoard.get(i).getLeaderBoardScore()) *100.0)/100.0)
					+ " BGtokens in his wallet with a record of " + leaderBoard.get(i).getLeaderBoardWin() + 
					" win(s) and " + leaderBoard.get(i).getLeaderBoardLose() + " loss(es).", 0x1A93D8);
				}
			}
		}
	}
}
