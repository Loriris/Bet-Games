package com.isenteam.betgames;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.security.auth.login.LoginException;
import org.springframework.boot.SpringApplication;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.isenteam.betgames.bot.Commands;
import com.isenteam.betgames.bot.listBetor;
import com.mashape.unirest.http.exceptions.UnirestException;

@SpringBootApplication
public class BetgamesApplication {

	//jda creation 
	private static JDA jda;

	public static void main(String[] args) throws LoginException, InterruptedException, UnirestException, IOException {
		SpringApplication.run(BetgamesApplication.class, args);
		/*
		List<listBetor> betors = listBetor.getList("5249068267", "100");
		for(int i =0; i < betors.size(); i++)
		{
			System.out.print(betors.get(i).getBetorName()+ "    ");
			System.out.println(betors.get(i).getWin());
		}
		*/
		//bot creation with adapted token	
		Gson config = new Gson();
		JsonReader reader = new JsonReader(new FileReader("config.json"));
		JsonObject conf = config.fromJson(reader, JsonObject.class);
		jda = JDABuilder.createDefault(conf.get("test-token").getAsString())
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
