package com.isenteam.betgames.bot;

import java.util.List;

import com.isenteam.betgames.BetgamesApplication;
import com.isenteam.betgames.API.InfoAPI;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.RestAction;


public class NotifyUser {
	
	private List<listBetor> betors;
	private String gameId; 
	
	public NotifyUser(List<listBetor> betors, String gameId) {
		
		this.betors = betors;
		this.gameId = gameId;
		
	}
	
	public void notif()
	{
		float gains; 
        
        for(int i =0; i < this.betors.size(); i++)
		{
        	// gains = odd * bet
        	gains = this.betors.get(i).getOdd() * this.betors.get(i).getBet();
        	
        	// to get the private  user's channel 
    		RestAction<User> userRestAction = BetgamesApplication.jda.retrieveUserById(this.betors.get(i).getBetorId());
            User userBet = userRestAction.complete(); 
            
            //if win, else lost
        	if(Boolean.compare(this.betors.get(i).isWin(), true) == 0) 
        	{
        		sendResult(userBet, "😀 Win, you won " + gains + 
        				"BGtokens with the game n° " + this.gameId + ".");
        	}
        	else
        	{
        		sendResult(userBet, "😥 You've lost on the game n° " + this.gameId + ".");
        	}
		} 
	}
	
	// send a private message to the gambler to inform him if he has won or lost
		public static void sendResult(User user, String content) 
		{
			user.openPrivateChannel().queue(channel -> {
				channel.sendMessage(content).queue();
				});
		}
}
