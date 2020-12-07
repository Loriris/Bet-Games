package discordBot;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;


public class InfoAPI	
{
	private JsonObject PartyInfo;
	private String player;
	private static String key = "RGAPI-412eee17-d3f6-4a42-a683-6c5a1d2785c9"; 
	private String region;

	
	public InfoAPI(String playerToSet, String regionToSet)
	{
		this.player = playerToSet;
		this.region = regionToSet; 
	}
	
	public JsonObject PartyInfo() throws UnirestException
	{
		System.out.println(player);
		HttpResponse <JsonNode> response = Unirest.get("https://" +  region + ".api.riotgames.com/lol/summoner/v4/summoners/by-name/" + player + "?api_key=" + key).asJson();
		JsonObject summonerInfo = JsonParser.parseString(response.getBody().toString()).getAsJsonObject();
		System.out.println(summonerInfo.get("id").getAsString());
			      
		HttpResponse <JsonNode> response2 = Unirest.get("https://"+ region + ".api.riotgames.com/lol/spectator/v4/active-games/by-summoner/" + summonerInfo.get("id").getAsString() + "?" + "api_key=" + key ).asJson();
	    this.PartyInfo = JsonParser.parseString(response2.getBody().toString()).getAsJsonObject();
	    return this.PartyInfo;
	}

	public JsonObject getPartyInfo()
	{
		return this.PartyInfo;
	}

	
	
	
}
