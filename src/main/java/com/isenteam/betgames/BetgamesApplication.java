package com.isenteam.betgames;

import javax.security.auth.login.LoginException;
import org.springframework.boot.SpringApplication;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.isenteam.betgames.bot.Commands;
import com.mashape.unirest.http.exceptions.UnirestException;

@SpringBootApplication
public class BetgamesApplication {

	//jda creation 
	private static JDA jda;

	public static void main(String[] args) throws LoginException, InterruptedException, UnirestException {
		SpringApplication.run(BetgamesApplication.class, args);
		
		//bot creation with adapted token	
		// officiel NzgwMzgyMjMxNDExNjg3NDQ1.X7uRlg.Yc-yiu67ZbqSCN8Rcf7VIqG-CTQ
		// test ODI5NjIxNDk3Mjg2ODg1Mzk2.YG6zOg.ordrRLH9jD0B8G7P4-Y0ttnW9HA
		
		jda = JDABuilder.createDefault("ODI5NjIxNDk3Mjg2ODg1Mzk2.YG6zOg.ordrRLH9jD0B8G7P4-Y0ttnW9HA")
				.setActivity(Activity.watching("Type #info to display all commands"))
				.build()
				.awaitReady(); //This method will block until JDA has reached the status JDA.Status.CONNECTED.

		//command call
		jda.addEventListener(new Commands());
		jda.getPresence().setStatus(OnlineStatus.IDLE);	
	
	}

}
