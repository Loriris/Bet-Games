package discordBot;
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
	}

	public JsonObject getPartyInfo()
	{
		return this.partyInfo;
	}
	
	public void retrieveParticipantsInfo()
	{
		JsonArray participantArray = this.partyInfo.get("participants").getAsJsonArray();
		for(int index = 0; index < participantArray.size(); index++)
		{
			
			this.participant[index] = new Participant(
					participantArray.get(index).getAsJsonObject().get("summonerName").getAsString(),
					participantArray.get(index).getAsJsonObject().get("championId").getAsInt(),
					participantArray.get(index).getAsJsonObject().get("teamId").getAsInt(),
					participantArray.get(index).getAsJsonObject().get("spell1Id").getAsInt(),
					participantArray.get(index).getAsJsonObject().get("spell2Id").getAsInt());
		}
		
	}

	public Participant[] getParticipant()
	{
		return this.participant;
	}
}
