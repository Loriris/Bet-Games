package com.isenteam.betgames;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import org.springframework.boot.SpringApplication;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.cloud.secretmanager.v1.AccessSecretVersionResponse;
import com.isenteam.betgames.bot.Commands;
import com.isenteam.betgames.secretsmanager.AccessSecretVersion;
import com.mashape.unirest.http.exceptions.UnirestException;

@SpringBootApplication
public class BetgamesApplication {

	//jda creation 
	private static JDA jda;

	public static void main(String[] args) throws LoginException, InterruptedException, UnirestException, IOException {
		SpringApplication.run(BetgamesApplication.class, args);
		
		//bot creation with adapted token	
		
		/*String officiel = "NzgwMzgyMjMxNDExNjg3NDQ1.X7uRlg.Yc-yiu67ZbqSCN8Rcf7VIqG-CTQ";
		String test = "ODI5NjIxNDk3Mjg2ODg1Mzk2.YG6zOg.ordrRLH9jD0B8G7P4-Y0ttnW9HA";
		jda = JDABuilder.createDefault(officiel)
				.setActivity(Activity.watching("Type #info to display all commands"))
				.build()
				.awaitReady(); //This method will block until JDA has reached the status JDA.Status.CONNECTED.

		//command call
		jda.addEventListener(new Commands());
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
		
		/*AccessSecretVersion secret = new AccessSecretVersion();
		secret.accessSecretVersion();*/
		
	}

}
