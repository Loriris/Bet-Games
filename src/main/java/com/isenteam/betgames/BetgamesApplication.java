package com.isenteam.betgames;

import java.io.IOException;
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
	public static JDA jda;

	public static void main(String[] args) throws LoginException, InterruptedException, UnirestException, IOException {
		SpringApplication.run(BetgamesApplication.class, args);
		
		//bot creation with adapted token	
		jda = JDABuilder.createDefault(System.getenv("TOKEN_API"))
				.setActivity(Activity.watching("Type #info to display all commands"))
				.build()
				.awaitReady(); //This method will block until JDA has reached the status JDA.Status.CONNECTED.
		//command call
		jda.addEventListener(new Commands());
		jda.getPresence().setStatus(OnlineStatus.ONLINE);
	}
}
