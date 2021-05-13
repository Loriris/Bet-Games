package com.isenteam.betgames.bdd;

import com.mongodb.client.MongoClients;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.isenteam.betgames.bot.ActiveGames;
import com.isenteam.betgames.bot.Bet;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;

import java.util.ArrayList;
import java.util.Iterator;

import org.bson.Document;


public class Mongo {
	private MongoClient mClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	public Mongo(String collection) {
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
		Document doc = new Document("bet", bet.getBet());
		doc.append("team", bet.getTeam());
		doc.append("odd", bet.getOdd());
		doc.append("server", bet.getGame());
		doc.append("userName", bet.getUserName());
		doc.append("gameId", bet.getGameId());
		doc.append("userId", bet.getUserId());
		doc.append("done", "false");
		doc.append("status", "null");
		
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
	
	public void insertParty(JsonObject party, String player)
	{
		Document part = new Document("party",party.toString());
		part.append("_id", party.get("gameId").getAsString());
		part.append("_player", player);
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
            ActiveGames active = new ActiveGames(party.get("gameId").getAsString(), party.get("gameType").getAsString(), doc.get("_player").toString());
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
            	this.collection.deleteMany(Filters.eq("_id", id));
            }
        }
	}
	
	public JsonObject retreiveParty(String id)
    {
        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        JsonObject party = null ;

         while (it.hasNext()) {
             Document doc = (Document) it.next();
             JsonObject docObj = JsonParser.parseString(doc.toJson()).getAsJsonObject();
             party = JsonParser.parseString(docObj.get("party").getAsString()).getAsJsonObject();
             if(party.get("gameId").getAsString().contentEquals(id))
             {
                 return party;
             }
         }
 
        return party;
    }
	
	public void doneBet(String id, String team)
	{
        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
            if(doc.containsValue(id) && doc.getString("team").equals(team))
            {
            	Document content = doc;
            	content.put("done", "true");
            	content.put("status","win");
            	content.put("_id", doc.getObjectId("_id"));
            	Document update = new Document("$set", content);
            	this.collection.updateOne(Filters.eq("_id", doc.getObjectId("_id")), update);
            }
            else if(doc.containsValue(id) && !doc.getString("team").equals(team))
            {
            	Document content = doc;
            	content.put("done", "true");
            	content.put("status","lose");
            	content.put("_id", doc.getObjectId("_id"));
            	Document update = new Document("$set", content);
            	this.collection.updateOne(Filters.eq("_id", doc.getObjectId("_id")), update);
            }
        }
	}
	
	public ArrayList<Bet> displayBetNotDone(String id)
    {
		//tous les doc de la collection
        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        ArrayList<Bet> tab = new ArrayList<>();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
        	//recuperation de tous le doc
        	if(doc.getString("userId").equals(id) && doc.containsValue("false"))
        	{
                Bet active = new Bet(doc.getLong("bet"), doc.getString("team"), doc.getDouble("odd").floatValue(), doc.getString("userName"), doc.getString("gameId"), doc.getString("userId"), doc.getString("done"), doc.getString("status"));
                tab.add(active);
        	}
        }
        
        return tab;
    }
	
	
	public ArrayList<Bet> displayAllBet(String userId)
    {
		//tous les doc de la collection
        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        ArrayList<Bet> tab = new ArrayList<>();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
        	if(doc.getString("userId").equals(userId))
        	{
        		 Bet active = new Bet(doc.getLong("bet"), doc.getString("team"), doc.getDouble("odd").floatValue(), doc.getString("userName"), doc.getString("gameId"), doc.getString("userId"), doc.getString("done"), doc.getString("status"));
                 tab.add(active);
        	}
        }
        return tab;
    }
	
	public ArrayList<Bet> displayBetDone(String userId)
    {
		//tous les doc de la collection
        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        ArrayList<Bet> tab = new ArrayList<>();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
        	if(doc.getString("userId").equals(userId) && !doc.containsValue("false"))
        	{
                Bet active = new Bet(doc.getLong("bet"), doc.getString("team"), doc.getDouble("odd").floatValue(), doc.getString("userName"), doc.getString("gameId"), doc.getString("userId"), doc.getString("done"), doc.getString("status"));
                tab.add(active);
        	}
        	
        }
        
        return tab;
    }
	
	public ArrayList<Bet> displayBetDoneOnParty(String id, String gameId)
    {
		//tous les doc de la collection
        FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        ArrayList<Bet> tab = new ArrayList<>();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
        	//recuperation de tous le doc
        	if(doc.getString("userId").equals(id) && !doc.containsValue("false") && doc.getString("gameId").equals(gameId))
        	{
                Bet active = new Bet(doc.getLong("bet"), doc.getString("team"), doc.getDouble("odd").floatValue(), doc.getString("userName"), doc.getString("gameId"), doc.getString("userId"), doc.getString("done"), doc.getString("status"));
                tab.add(active);
        	}
        	
        }
        
        return tab;
    }
	
	public void updateUserInfo(ArrayList<Bet> allBet)
	{
		FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
        	if(doc.containsValue(allBet.get(0).getUserId()))
        	{
        		int win = 0;
        		int lose = 0;
        		double gain = 0;
        		for(int i =0; i<allBet.size(); i++)
        		{
        			if(allBet.get(i).getStatus().equals("win"))
        			{
        				win++;
        				gain = gain + allBet.get(i).getOdd()*allBet.get(0).getBet(); 
        			}
        			else
        			{
        				lose++;
        				gain = gain - allBet.get(i).getBet();
        			}
        		}
        		
        		Document content = doc;
            	content.put("win", doc.getInteger("win") + win);
            	content.put("lose", doc.getInteger("lose") + lose);
            	content.put("totalGain", doc.getDouble("totalGain").floatValue() + gain);
            	Document update = new Document("$set", content);
            	this.collection.updateOne(Filters.eq("_id", allBet.get(0).getUserId()), update);
        	}
        }
	}
	
	public void createUser(ArrayList<Bet> allBet)
	{
		Document User = new Document();
		User.append("name", allBet.get(0).getUserName());
		User.append("_id", allBet.get(0).getUserId());
		int win = 0;
		int lose = 0;
		double gain = 0;
		for(int i =0; i<allBet.size(); i++)
		{
			
			if(allBet.get(i).getStatus().equals("win"))
			{
				win++;
				gain = gain + allBet.get(i).getOdd()*allBet.get(0).getBet();
			}
			else
			{
				lose++;
				gain = gain - allBet.get(i).getBet();
			}
		}
		User.append("win", win);
		User.append("lose", lose);
		User.append("totalGain", gain);
		this.collection.insertOne(User);
	}
	
	public Boolean isUserExist(String id)
	{
		FindIterable<Document> iterDoc = this.collection.find();
        Iterator it = iterDoc.iterator();
        while (it.hasNext()) 
        {
        	Document doc = (Document) it.next();
        	if(doc.containsValue(id))
        	{
        		return true;
        	}
        }
        return false;
	}
	
}	
