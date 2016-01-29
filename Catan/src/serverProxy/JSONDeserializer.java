package serverProxy;

import shared.communication.GameModelJSON;
import model.resources.*;
import model.players.*;
import model.map.*;
import model.CatanModel;
import model.development.*;
import model.messagelog.*;
import shared.locations.*;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.*;



/**
 * Static object designed to deserialize the JSON object received from the server into a
 * GameModelJSON object
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Winter 2016.
 */
public class JSONDeserializer
{
	/**
	 * Parse through the JSON string received from the server and translate into GameModelJSON
	 * object
	 * 
	 * @param jsonObject String received from the server API
	 * @return The now parsed GameModelJSON object.
	 */
	public static CatanModel deserialize(String json)
	{
		CatanModel catanModel;
		
		JsonElement jelement = new JsonParser().parse(json);
	    JsonObject  jobject = jelement.getAsJsonObject();
	    JsonObject bank = jobject.getAsJsonObject("bank");
	    JsonObject chat = jobject.getAsJsonObject("chat");
	    JsonObject log = jobject.getAsJsonObject("log");
	    JsonObject map = jobject.getAsJsonObject("map");
	    JsonArray players = jobject.getAsJsonArray("players");
	    JsonObject tradeOffer = jobject.getAsJsonObject("tradeOffer");
	    JsonObject turnTracker = jobject.getAsJsonObject("turnTracker");
	    int version = jobject.getAsJsonObject("version").getAsInt();
	    int winner = jobject.getAsJsonObject("winner").getAsInt();
	    
	    
	    /*Set ResourceManager**************************************************/
	    ResourceManager resourceManager;
	    ResourceList bankResources;
	    ResourceList[] playerResources = new ResourceList[4];
	    
	    //Get bank resources
	    {
		    int brick = bank.getAsJsonObject("brick").getAsInt();
		    int ore = bank.getAsJsonObject("ore").getAsInt();
		    int sheep = bank.getAsJsonObject("sheep").getAsInt();
		    int wheat = bank.getAsJsonObject("wheat").getAsInt();
		    int wood = bank.getAsJsonObject("wood").getAsInt();	    
		    bankResources = new ResourceList(brick,ore,sheep,wheat,wood);
	    }
	    
	    //Get player resources
		for(int i = 0; i < players.size(); i++)
		{
			JsonObject player = players.get(i).getAsJsonObject();
			JsonObject resource = player.getAsJsonObject("resources");
			int index = player.get("playerIndex").getAsInt();
			ResourceList resources;
			
			int brick = resource.getAsJsonObject("brick").getAsInt();
		    int ore = resource.getAsJsonObject("ore").getAsInt();
		    int sheep = resource.getAsJsonObject("sheep").getAsInt();
		    int wheat = resource.getAsJsonObject("wheat").getAsInt();
		    int wood = resource.getAsJsonObject("wood").getAsInt();
		    
		    resources = new ResourceList(brick,ore,sheep,wheat,wood);
			playerResources[index] = resources;
		}
		resourceManager = new ResourceManager(playerResources,bankResources);
		/*End ResourceManager**************************************************/
		
		
		/*Set DevCardManager***************************************************/
		DevCardManager devCardManager;
		
	    for(int i = 0; i < players.size(); i++)
	    {
	    	JsonObject player = players.get(i).getAsJsonObject();
	    	int index = player.get("playerIndex").getAsInt();
	    	
	    	JsonObject newDevCards = player.getAsJsonObject("newDevCards");
	    	JsonObject oldDevCards = player.getAsJsonObject("oldDevCards");
	    	//New Dev Cards
	    	{
		    	int monopoly = newDevCards.getAsJsonObject("monopoly").getAsInt();
		    	int monument = newDevCards.getAsJsonObject("monument").getAsInt();
		    	int roadBuilding = newDevCards.getAsJsonObject("roadBuilding").getAsInt();
		    	int soldier = newDevCards.getAsJsonObject("soldier").getAsInt();
		    	int yearOfPlenty = newDevCards.getAsJsonObject("yearOfPlenty").getAsInt();
		    	//<======Set part of devCarManager here=====>//
	    	}
	    	//Old Dev Cards
	    	{
		    	int monopoly = oldDevCards.getAsJsonObject("monopoly").getAsInt();
		    	int monument = oldDevCards.getAsJsonObject("monument").getAsInt();
		    	int roadBuilding = oldDevCards.getAsJsonObject("roadBuilding").getAsInt();
		    	int soldier = oldDevCards.getAsJsonObject("soldier").getAsInt();
		    	int yearOfPlenty = oldDevCards.getAsJsonObject("yearOfPlenty").getAsInt();
		    	//<======Set other part of devCarManager here=====>//
	    	}
	    	//<=======Construct devCardManager here=======>//
	    	
	    }
	    /*End DevCardManager***************************************************/
		
		
		/*Set PlayerManager****************************************************/
	    PlayerManager playerManager;
	    
	    for(int i = 0; i < players.size(); i++)
	    {
	    	JsonObject player = players.get(i).getAsJsonObject();
	    	int index = player.get("playerIndex").getAsInt();
	    	
	    	String name = player.getAsJsonObject("name").getAsString();
	    	String color = player.getAsJsonObject("color").getAsString();
	    	int playerId = player.getAsJsonObject("playerID").getAsInt();
	    	int cities = player.getAsJsonObject("cities").getAsInt();
	    	int settlements = player.getAsJsonObject("settlements").getAsInt();
	    	int roads = player.getAsJsonObject("roads").getAsInt();
	    	int monuments = player.getAsJsonObject("monuments").getAsInt();
	    	int soldiers = player.getAsJsonObject("soldiers").getAsInt();
	    	int victoryPoints = player.getAsJsonObject("victoryPoints").getAsInt();
	    	boolean discarded = player.getAsJsonObject("discarded").getAsBoolean();
	    	boolean playedDevCard = player.getAsJsonObject("playedDevCard").getAsBoolean();
	    	//<========Construct player manager here============>
	    	
	    }
		/*End PlayerManager****************************************************/
	    
	    
	    /*Set MapManager*******************************************************/
	    MapManager mapManager;
	    
	    JsonArray hexes = map.getAsJsonArray("hexes");
	    JsonArray ports = map.getAsJsonArray("ports");
	    JsonArray roads = map.getAsJsonArray("roads");
	    JsonArray settlements = map.getAsJsonArray("settlements");
	    JsonArray cities = map.getAsJsonArray("cities");
	    JsonObject robber = map.getAsJsonObject("robber");
	    int radius = map.getAsJsonObject("radius").getAsInt();
	    
	    //Get Hex info
	    for(int i = 0; i < hexes.size(); i++)
	    {
	    	JsonObject hex = hexes.get(i).getAsJsonObject();    	
	    	JsonObject location = hex.getAsJsonObject("location");
	    	
	    	int x = location.getAsJsonObject("x").getAsInt();
	    	int y = location.getAsJsonObject("y").getAsInt();
	    	String resource = hex.getAsJsonObject("resource").getAsString();
	    	int number = hex.getAsJsonObject("number").getAsInt();
	    }
	    
	    //Get Port info
	    for(int i = 0; i < ports.size(); i++)
	    {
	    	JsonObject port = ports.get(i).getAsJsonObject();
	    	JsonObject location = port.getAsJsonObject("location");
	    	
	    	int x = location.getAsJsonObject("x").getAsInt();
	    	int y = location.getAsJsonObject("y").getAsInt();
	    	String resource = port.getAsJsonObject("resource").getAsString();
	    	String direction = port.getAsJsonObject("direction").getAsString();
	    	int ratio = port.getAsJsonObject("ratio").getAsInt();    	
	    }
	    
	    //Get road info
	    for(int i = 0; i < roads.size(); i++)
	    {
	    	JsonObject road = roads.get(i).getAsJsonObject();
	    	JsonObject location = road.getAsJsonObject("location");
	    	
	    	int x = location.getAsJsonObject("x").getAsInt();
	    	int y = location.getAsJsonObject("y").getAsInt();
	    	int owner = road.getAsJsonObject("owner").getAsInt();
	    	String direction = road.getAsJsonObject("direction").getAsString();
	    }
	    
	    //Get settlement info
	    for(int i = 0; i < settlements.size(); i++)
	    {
	    	JsonObject settlement = roads.get(i).getAsJsonObject();
	    	JsonObject location = settlement.getAsJsonObject("location");
	    	
	    	int x = location.getAsJsonObject("x").getAsInt();
	    	int y = location.getAsJsonObject("y").getAsInt();
	    	int owner = settlement.getAsJsonObject("owner").getAsInt();
	    	String direction = settlement.getAsJsonObject("direction").getAsString();
	    }
	    
	    //Get city info
	    for(int i = 0; i < cities.size(); i++)
	    {
	    	JsonObject city = roads.get(i).getAsJsonObject();
	    	JsonObject location = city.getAsJsonObject("location");
	    	
	    	int x = location.getAsJsonObject("x").getAsInt();
	    	int y = location.getAsJsonObject("y").getAsInt();
	    	int owner = city.getAsJsonObject("owner").getAsInt();
	    	String direction = city.getAsJsonObject("direction").getAsString();
	    }
	    
	    int robberX = robber.getAsJsonObject("x").getAsInt();
	    int robberY = robber.getAsJsonObject("y").getAsInt();
	    /*End MapManager*******************************************************/
	    
	    
	    /*Set ChatManager******************************************************/
	    ChatManager chatManager;
	    List<Line> chatMessages = new ArrayList<Line>();
	    List<Line> gameHistory = new ArrayList<Line>();
	    //Get chat info
	    {
		    JsonArray lines = chat.getAsJsonArray("lines");
		    for(int i = 0; i < lines.size(); i++)
		    {
		    	JsonObject line = lines.get(i).getAsJsonObject();
		    	
		    	String message = line.getAsJsonObject("message").getAsString();
		    	String source = line.getAsJsonObject("source").getAsString();
		    	Line l = new Line(message,source);
		    	chatMessages.add(l);
		    }
	    }
	    
	    //Get log info
	    {
	    	JsonArray lines = log.getAsJsonArray("lines");
	    	for(int i = 0; i < lines.size(); i++)
		    {
		    	JsonObject line = lines.get(i).getAsJsonObject();
		    	
		    	String message = line.getAsJsonObject("message").getAsString();
		    	String source = line.getAsJsonObject("source").getAsString();
		    	Line l = new Line(message,source);
		    	gameHistory.add(l);
		    }	    	
	    }
	    chatManager = new ChatManager(chatMessages, gameHistory);
	    /*End ChatManager******************************************************/
	    
	    
	    /*Set TradeManager*****************************************************/
	    int sender = tradeOffer.getAsJsonObject("sender").getAsInt();
	    int receiver = tradeOffer.getAsJsonObject("receiver").getAsInt();
	    
	    JsonObject offer = tradeOffer.getAsJsonObject("offer");
	    {
	    	
	    	int brick = offer.getAsJsonObject("brick").getAsInt();
		    int ore = offer.getAsJsonObject("ore").getAsInt();
		    int sheep = offer.getAsJsonObject("sheep").getAsInt();
		    int wheat = offer.getAsJsonObject("wheat").getAsInt();
		    int wood = offer.getAsJsonObject("wood").getAsInt();
	    }
	    /*End TradeManager*****************************************************/
	    
	    
	    /*Set TurnManager******************************************************/
	    int currentTurn = turnTracker.getAsJsonObject("currentTurn").getAsInt();
	    String status = turnTracker.getAsJsonObject("status").getAsString();
	    int longestRoad = turnTracker.getAsJsonObject("longestRoad").getAsInt();
	    int largestArmy = turnTracker.getAsJsonObject("largestArmy").getAsInt();
	    /*End TurnManager******************************************************/
	    
		return catanModel = new CatanModel();
	}
}
