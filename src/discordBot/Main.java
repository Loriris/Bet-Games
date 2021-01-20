package discordBot;

import javax.security.auth.login.LoginException;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mashape.unirest.http.exceptions.UnirestException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;


public class Main {

	//jda creation 

	private static JDA jda;
	
	
	public static void main(String[] args) throws LoginException, InterruptedException, UnirestException
	{
		/*
		//cr�e le bot avec le token adapt�		
		jda = JDABuilder.createDefault("NzgwMzgyMjMxNDExNjg3NDQ1.X7uRlg.Yc-yiu67ZbqSCN8Rcf7VIqG-CTQ")
				.setActivity(Activity.watching("Type #info to display all commands"))
				.build()
				.awaitReady(); //This method will block until JDA has reached the status JDA.Status.CONNECTED.

		//command call
		jda.addEventListener(new Commands());
		jda.getPresence().setStatus(OnlineStatus.IDLE); */
		
		InfoAPI info = new InfoAPI("trayton", "EUW1");
		info.PartyInfo();
		info.retrieveParticipantsInfo();
		System.out.println(info.getParticipant()[0].getSort1());
		System.out.println(info.getParticipant()[0].getSort2());
	
	
	}
	
		
	      
	
	
}

