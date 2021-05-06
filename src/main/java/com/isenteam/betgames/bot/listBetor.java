package com.isenteam.betgames.bot;

import com.mongodb.client.FindIterable;

import org.bson.Document;
import java.util.Iterator;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isenteam.betgames.bdd.Mongo;
import static com.mongodb.client.model.Filters.*;
import java.util.ArrayList;
import java.util.List;

public class listBetor {
	private String betorName;
	private boolean win;
	
	listBetor(String name, boolean win) {
		this.betorName = name;
		this.win = win;
	}
	
	listBetor() {}
	
	public String getBetorName() {
		return this.betorName;
	}
	
	public boolean getWin() {
		return this.win;
	}
	
	public static List<listBetor> getList(String game_id, String team) {
		Mongo db = new Mongo("Bets");
		List<Document> doc = new ArrayList<Document>();
		List<listBetor> betors = new ArrayList<listBetor>();
		FindIterable<Document> iterator = db.getCollection().find(eq("gameId", game_id));
		Iterator it = iterator.iterator();
		while(it.hasNext()) {
			Document document = (Document) it.next();
			JsonObject bet = JsonParser.parseString(document.toJson()).getAsJsonObject();
			String name = bet.get("userId").getAsString();
			String _team = bet.get("team").getAsString();
			listBetor betor = new listBetor();
			if(team.equals(_team)) {
				betor = new listBetor(name, true);
			}
			else {
				betor = new listBetor(name, false);
			}
			betors.add(betor);
		}
		return betors;
	}
}
