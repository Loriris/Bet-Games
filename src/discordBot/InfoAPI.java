package discordBot;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.Consumer;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class InfoAPI	
{
	private JsonObject partyInfo;
	private String player;
	private static String key = "RGAPI-a0d056dd-cf01-4448-ab56-d93627eb78b5"; 
	private String region;
	private Participant[] participant;

	
	public InfoAPI(String playerToSet, String regionToSet) throws UnirestException
	{
		this.player = playerToSet;
		this.region = regionToSet;
		this.participant = new Participant[10];
	}
	
	public void PartyInfo() throws UnirestException
	{
		System.out.println(player);
		HttpResponse <JsonNode> response = Unirest.get("https://" +  region + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + player + "?api_key=" + key).asJson();
		JsonObject summonerInfo = JsonParser.parseString(response.getBody().toString()).getAsJsonObject();
			      
		HttpResponse <JsonNode> response2 = Unirest.get("https://"+ region + ".api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summonerInfo.get("id").getAsString() + "?" + "api_key=" + key ).asJson();
	    this.partyInfo = JsonParser.parseString(response2.getBody().toString()).getAsJsonObject();
	    System.out.println(this.partyInfo);
	}

	public JsonObject getPartyInfo()
	{
		return this.partyInfo;
	}
	
	public void retrieveParticipantsInfo() throws UnirestException
	{
		String name = "";
		HttpResponse <JsonNode> responseDragon= Unirest.get("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-summary.json").asJson();;
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
			
			this.participant[index] = new Participant(
					participantArray.get(index).getAsJsonObject().get("summonerName").getAsString(),
					name,
					participantArray.get(index).getAsJsonObject().get("teamId").getAsInt(),
					participantArray.get(index).getAsJsonObject().get("spell1Id").getAsInt(),
					participantArray.get(index).getAsJsonObject().get("spell2Id").getAsInt());
		}
		
	}

	public Participant[] getParticipant()
	{
		return this.participant;
	}
	
	public static void test() throws UnirestException
	{
		HttpResponse <JsonNode> responseDragon = Unirest.get("https://raw.communitydragon.org/latest/plugins/rcp-be-lol-game-data/global/default/v1/champion-summary.json").asJson();
		JsonArray champInfo = JsonParser.parseString(responseDragon.getBody().toString()).getAsJsonArray();
		Boolean name = champInfo.get(1).getAsJsonObject().has("1");
		System.out.println(name);
	}
}
