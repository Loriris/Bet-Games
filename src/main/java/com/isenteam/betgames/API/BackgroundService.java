package com.isenteam.betgames.API;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isenteam.betgames.bdd.Mongo;
import com.isenteam.betgames.bot.Bet;
import com.isenteam.betgames.bot.NotifyUser;
import com.isenteam.betgames.bot.listBetor;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mongodb.client.FindIterable;

@Configuration
@EnableScheduling
public class BackgroundService {
	
	 @Scheduled(fixedDelay = 50000)
	 public void backgroundServiceMethod() throws UnirestException
	 {
	     System.out.println("Method executed at every 5 minutes.");
	     Mongo mongoParty = new Mongo("Party");
	     Mongo mongoBet = new Mongo("Bets");
	     Mongo mongoUser = new Mongo("Users");
	     InfoAPI init = new InfoAPI();
	     List<listBetor> betors;
	     FindIterable<Document> iterDoc = mongoParty.getCollection().find();
	     Iterator it = iterDoc.iterator();
	         
	     while (it.hasNext()) 
	     {
	    	 Document doc = (Document) it.next();
	    	 JsonObject docObj = JsonParser.parseString(doc.toJson()).getAsJsonObject();
	    	 JsonObject party = JsonParser.parseString(docObj.get("party").getAsString()).getAsJsonObject();
	    	 HttpResponse <JsonNode> response2 = Unirest.get("https://euw1.api.riotgames.com/lol/match/v4/matches/"+ docObj.get("_id").getAsString() + "?api_key=" + InfoAPI.key ).asJson();
	    	 //System.out.println("Status: " + response2.getStatus());
	    	 if( response2.getStatus() == 200)
		     { 	 
			     JsonObject jsonResponse = JsonParser.parseString(response2.getBody().toString()).getAsJsonObject();
			     JsonArray team = jsonResponse.get("teams").getAsJsonArray();
			     if(team.get(0).getAsJsonObject().get("win").getAsString() == "win")
			     {
			    	 //team 100 a gagné
			    	 betors = listBetor.getList(jsonResponse.get("gameId").getAsString(), "100");
			    	 mongoBet.doneBet(docObj.get("_id").getAsString(), "100");
			    	 NotifyUser notif = new NotifyUser(betors, jsonResponse.get("gameId").getAsString());
			    	 notif.notif(); 
			     }
			     else
			     {
			    	 //team 200 a gagné
			    	 betors = listBetor.getList(jsonResponse.get("gameId").getAsString(), "200");
			    	 mongoBet.doneBet(docObj.get("_id").getAsString(), "200");
			    	 NotifyUser notif = new NotifyUser(betors, jsonResponse.get("gameId").getAsString());
			    	 notif.notif();
			     }
			     mongoParty.deleteParty(docObj.get("_id").getAsString());
			     
			     for(int index = 0 ; index < betors.size(); index++)
			     {
			    	 ArrayList<Bet> betorBet = mongoBet.displayBetDoneOnParty(betors.get(index).getBetorId(),docObj.get("_id").getAsString());
			    	 if(mongoUser.isUserExist(betors.get(index).getBetorId()))
			    	 {
			    		 mongoUser.updateUserInfo(betorBet);
			    	 }
			    	 else
			    	 {
			    		 mongoUser.createUser(betorBet);
			    	 }
			     }
		     }
	     }
	 }
}
