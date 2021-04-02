package com.isenteam.betgames;

import javax.security.auth.login.LoginException;
import org.springframework.boot.SpringApplication;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.isenteam.betgames.bot.Commands;
import com.mashape.unirest.http.exceptions.UnirestException;

@SpringBootApplication
public class BetgamesApplication {

	//jda creation 
	private static JDA jda;

	public static void main(String[] args) throws LoginException, InterruptedException, UnirestException {
		SpringApplication.run(BetgamesApplication.class, args);
		
		//bot creation with adapted token	
		jda = JDABuilder.createDefault("NzgwMzgyMjMxNDExNjg3NDQ1.X7uRlg.Yc-yiu67ZbqSCN8Rcf7VIqG-CTQ")
				.setActivity(Activity.watching("Type #info to display all commands"))
				.build()
				.awaitReady(); //This method will block until JDA has reached the status JDA.Status.CONNECTED.

		//command call
		jda.addEventListener(new Commands());
		jda.getPresence().setStatus(OnlineStatus.IDLE);	
	
	}

}