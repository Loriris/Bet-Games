package com.isenteam.betgames.bdd;

import com.mongodb.client.MongoClients;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isenteam.betgames.bot.ActiveGames;
import com.isenteam.betgames.bot.Bet;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.ConnectionString;
import com.mongodb.client.MongoCollection;
//import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.MongoCredential;
//import com.mongodb.MongoClientOptions;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;

//import java.util.Arrays;

public class Mongo {
	private MongoClient mClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	public Mongo( String collection) {
		this.mClient = MongoClients.create("mongodb://admin:FyxEhu9vj4NEQn7A2n@34.89.161.106:27017");
		this.database = mClient.getDatabase("BetData");
		this.collection = database.getCollection(collection);
		
	}
		
	public MongoClient getmClient() {
		return mClient;
	}

	public void setmClient(MongoClient mClient) {
		this.mClient = mClient;
	}

	public MongoDatabase getDatabase() {
		return database;
	}

	public void setDatabase(MongoDatabase database) {
		this.database = database;
	}

	public MongoCollection<Document> getCollection() {
		return collection;
	}

	public void setCollection(MongoCollection<Document> collection) {
		this.collection = collection;
	}

	public void insert(Bet bet) {
		//Cr�ation d'un document qui contiendra pour le champ bet la valeur de la mise 
		Document doc = new Document("bet", bet.bet());
		doc.append("team", bet.team());
		doc.append("odd", bet.odd());
		doc.append("server", bet.game());
		doc.append("userId", bet.user());
		
		//insertion du document dans la collection "collection" (cad collectionTest)
		this.collection.insertOne(doc);
	}
	
	public boolean searchForExistingParty(String id)
    {
        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
         
         while (it.hasNext()) {
        	 Document doc = (Document) it.next();
        	 JsonObject docObj = JsonParser.parseString(doc.toJson()).getAsJsonObject();
             JsonObject party = JsonParser.parseString(docObj.get("party").getAsString()).getAsJsonObject();
             if(party.get("gameId").getAsString().contentEquals(id))
             {
            	 return true;
             }
         }
 
		return false;
    }
	
	public void insertParty(JsonObject party)
	{
		Document part = new Document("party",party.toString());
		part.append("_id", party.get("gameId").getAsString());
        this.collection.insertOne(part);
	}
	
	public ArrayList<ActiveGames> displayGames()
    {
		//tous les doc de la collection
        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        ArrayList<ActiveGames> tab = new ArrayList<>();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
        	//recuperation de tous le doc
        	JsonObject docObj = JsonParser.parseString(doc.toJson()).getAsJsonObject(); 
        	//recuperation données partie
            JsonObject party = JsonParser.parseString(docObj.get("party").getAsString()).getAsJsonObject();
            ActiveGames active = new ActiveGames(party.get("gameId").getAsString(), party.get("gameType").getAsString());
            tab.add(active);
        }
        
        return tab;
    }
	
	public void deleteParty(String id)
	{

        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
        	JsonObject docObj = JsonParser.parseString(doc.toJson()).getAsJsonObject();
            JsonObject party = JsonParser.parseString(docObj.get("party").getAsString()).getAsJsonObject();
            if(party.get("gameId").getAsString().contentEquals(id))
            {
            	this.collection.deleteOne(Filters.eq("_id", id));
            }
        }
	}
}	
	
	
	
	
	

	
