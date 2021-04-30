package com.isenteam.betgames.API;

import java.util.Date;
import java.util.Iterator;

import org.bson.Document;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isenteam.betgames.bdd.Mongo;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mongodb.client.FindIterable;

//@Configuration
//@EnableScheduling
public class BackgroundService {
	
	 @Scheduled(fixedDelay = 50000)
	 public void backgroundServiceMethod() throws UnirestException
	 {
	     System.out.println("Method executed at every 5 minutes. Current time is :: "+ new Date());
	     Mongo mongoParty = new Mongo("Party");
	     Mongo mongoBet = new Mongo("Bets");
	     
	     FindIterable<Document> iterDoc = mongoParty.getCollection().find();
	     Iterator it = iterDoc.iterator();
	         
	     while (it.hasNext()) 
	     {
	    	 Document doc = (Document) it.next();
	    	 JsonObject docObj = JsonParser.parseString(doc.toJson()).getAsJsonObject();
	    	 JsonObject party = JsonParser.parseString(docObj.get("party").getAsString()).getAsJsonObject();
	    	 HttpResponse <JsonNode> response2 = Unirest.get("https://euw1.api.riotgames.com/lol/match/v4/timelines/by-match/"+ docObj.get("_id").getAsString() + "?api_key=" + InfoAPI.key ).asJson();
		     if( response2.getStatus() == 200)
		     {
			     JsonObject jsonResponse = JsonParser.parseString(response2.getBody().toString()).getAsJsonObject();
			     JsonArray team = jsonResponse.get("teams").getAsJsonArray();
			     if(team.get(0).getAsJsonObject().get("win").getAsString() == "win")
			     {
			    	 //team 100 a gagné
			     }
			     else
			     {
			    	 //team 200 a gagné
			     }
			     // fonction de thomas pour notifier le gagnant
			     mongoParty.deleteParty(docObj.get("_id").getAsString());
			     mongoBet.deleteBet(docObj.get("_id").getAsString());
		     }
	     }
	 }
}
