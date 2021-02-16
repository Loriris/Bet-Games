package discordBot;

import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoClient;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.ConnectionString;
import com.mongodb.client.MongoCollection;
//import com.mongodb.ServerAddress;
import com.mongodb.client.MongoDatabase;
//import com.mongodb.MongoCredential;
//import com.mongodb.MongoClientOptions;

import org.bson.Document;

//import java.util.Arrays;

public class Mongo {
	private MongoClient mClient;
	private MongoDatabase database;
	private MongoCollection<Document> collection;
	
	
	public Mongo() {
		this.mClient = MongoClients.create("mongodb://admin:FyxEhu9vj4NEQn7A2n@34.89.161.106:27017");
		this.database = mClient.getDatabase("BetData");
		this.collection = database.getCollection("test");
		
	}
	
	
	public void insert(Bet bet) {
		//Création d'un document qui contiendra pour le champ bet la valeur de la mise 
		Document doc = new Document("bet", bet.bet());
		doc.append("team", bet.team());
		doc.append("odd", bet.odd());
		doc.append("game", bet.game());
		
		//insertion du document dans la collection "collection" (cad collectionTest)
		this.collection.insertOne(doc);
	}
	
	
		
	
}	
	
	
	
	
	

	
