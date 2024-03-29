package com.isenteam.betgames.API;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isenteam.betgames.bdd.Mongo;
import com.isenteam.betgames.bot.Participant;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class InfoAPI	
{
	private JsonObject partyInfo;
	private String player;
	public static String key;
	private String region;
	private Participant[] participant;
	private String id;

	
	public InfoAPI(String playerToSet, String regionToSet)
	{
		this.key = System.getenv("TOKEN_RIOT");
		this.player = playerToSet;
		this.region = regionToSet;
		this.participant = new Participant[10];
	}
	
	public InfoAPI(String id) 
	{
		this.key = System.getenv("TOKEN_RIOT");
		this.id = id;
		this.participant = new Participant[10];
	};
	
	public InfoAPI() 
	{
		this.key = System.getenv("TOKEN_RIOT");
	};
	
	//Pour appeler l'API riot
	public int partyInfo() throws UnirestException
	{
		HttpResponse <JsonNode> response = Unirest.get("https://" +  region + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + player + "?api_key=" + key).asJson();
		if(response.getStatus() == 200)
		{
			JsonObject summonerInfo = JsonParser.parseString(response.getBody().toString()).getAsJsonObject();
			HttpResponse <JsonNode> response2 = Unirest.get("https://"+ region + ".api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summonerInfo.get("id").getAsString() + "?" + "api_key=" + key ).asJson();
			if(response2.getStatus() == 200)
			{
				 this.partyInfo = JsonParser.parseString(response2.getBody().toString()).getAsJsonObject();	   
				 Mongo mongo = new Mongo("Party");
				 if(Boolean.compare(mongo.searchForExistingParty(this.partyInfo.get("gameId").getAsString()), false) == 0)
				 {
				    	mongo.insertParty(this.partyInfo, this.player);   	
				 }
			}
			return response2.getStatus();
		}
		return response.getStatus();
	}
	
	//Pour appeler la bdd
	public void partyInfoMongo()
    {
        Mongo mongo = new Mongo("Party");
        this.partyInfo = mongo.retreiveParty(this.id);
    }

	public JsonObject getPartyInfo()
	{
		return this.partyInfo;
	}
	
	public void retrieveParticipantsInfo() throws UnirestException
	{
		String name = "";
		float ratio;
		float win;
		float lose;
		HttpResponse <JsonNode> responseDragon = Unirest.get("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-summary.json").asJson();
		
		
		JsonArray champInfo = JsonParser.parseString(responseDragon.getBody().toString()).getAsJsonArray();
		
		JsonArray participantArray = this.partyInfo.get("participants").getAsJsonArray();
		for(int index = 0; index < participantArray.size(); index++)
		{
			for (int index2 = 0; index < champInfo.size(); index2++)
			{
				if(champInfo.get(index2).getAsJsonObject().get("id").getAsInt() == participantArray.get(index).getAsJsonObject().get("championId").getAsInt())
				{
					name = champInfo.get(index2).getAsJsonObject().get("name").getAsString();
					break;
				}
			}
			HttpResponse <JsonNode> ratioResponse = Unirest.get("https://"+ this.partyInfo.get("platformId").getAsString() +".api.riotgames.com/lol/league/v4/entries/by-summoner/"+ participantArray.get(index).getAsJsonObject().get("summonerId").getAsString()  + "?api_key=" + key).asJson();
			JsonArray league = JsonParser.parseString(ratioResponse.getBody().toString()).getAsJsonArray();
			if(league.size() == 0 )
			{
				ratio = 0;
			}
			else
			{
				
				win = league.get(0).getAsJsonObject().get("wins").getAsInt();
				lose = league.get(0).getAsJsonObject().get("losses").getAsInt();
				ratio = win/(win + lose);
			}
			
			
			this.participant[index] = new Participant(
					participantArray.get(index).getAsJsonObject().get("summonerName").getAsString(),
					name,
					participantArray.get(index).getAsJsonObject().get("teamId").getAsInt(),
					participantArray.get(index).getAsJsonObject().get("spell1Id").getAsInt(),
					participantArray.get(index).getAsJsonObject().get("spell2Id").getAsInt(),
					ratio);
		}
		
	}

	public Participant[] getParticipant()
	{
		return this.participant;
	}
}
