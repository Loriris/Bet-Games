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
	private String betorId;
	private boolean win;
	private long bet;
	private float odd;
	
	listBetor(String name, boolean win, long bet, float odd) {
		this.betorId = name;
		this.win = win;
		this.bet = bet;
		this.odd = odd;
	}
	
	listBetor(String name, boolean win) {
		this.betorId = name;
		this.win = win;
	}
	
	listBetor() {}
	
	
	public String getBetorId() {
		return betorId;
	}

	public void setBetorId(String betorId) {
		this.betorId = betorId;
	}

	public boolean isWin() {
		return win;
	}

	public void setWin(boolean win) {
		this.win = win;
	}

	public long getBet() {
		return bet;
	}

	public void setBet(long bet) {
		this.bet = bet;
	}

	public float getOdd() {
		return odd;
	}

	public void setOdd(float odd) {
		this.odd = odd;
	}

	public static List<listBetor> getList(String game_id, String team) {
		Mongo db = new Mongo("Bets");
		List<listBetor> betors = new ArrayList<listBetor>();
		FindIterable<Document> iterator = db.getCollection().find(eq("gameId", game_id));
		Iterator it = iterator.iterator();
		while(it.hasNext()) {
			Document document = (Document) it.next();
			JsonObject bet = JsonParser.parseString(document.toJson()).getAsJsonObject();
			String id = bet.get("userId").getAsString();
			String _team = bet.get("team").getAsString();
			long money = bet.get("bet").getAsLong();
			float _odd = bet.get("odd").getAsFloat();
			listBetor betor = new listBetor();
			if(team.equals(_team)) {
				betor = new listBetor(id, true, money, _odd);
			}
			else {
				betor = new listBetor(id, false);
			}
			betors.add(betor);
		}
		return betors;
	}
}
