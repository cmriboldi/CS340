package serverProxy;

import shared.communication.GameModelJSON;
import model.resources.*;
import model.players.*;
import model.map.*;
import model.CatanModel;
import model.development.*;
import model.messagelog.*;
import model.options.Options;
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
	private static JSONDeserializer _instance;
	
	private JSONDeserializer(){}
	
	private static JSONDeserializer instance() {
		
		if (_instance == null)
			_instance = new JSONDeserializer();

		return _instance;
	}
	
	public static CatanModel deserialize(String json)
	{
		return instance()._deserialize(json);
	}
	
	private ResourceManager resourceManager;
	private DevCardManager devCardManager;
	private PlayerManager playerManager;
	private MapManager mapManager;
	private ChatManager chatManager;
	
	private void SetResourceManager(JsonObject bank, JsonArray players, JsonObject tradeOffer)
	{
	    ResourceList bankResources;
	    ResourceList[] playerResources = new ResourceList[4];
	    TradeOffer tradeResourcesOffer;
	    
	    //Get bank resources
	    {
		    int brick = bank.getAsJsonPrimitive("brick").getAsInt();
		    int ore = bank.getAsJsonPrimitive("ore").getAsInt();
		    int sheep = bank.getAsJsonPrimitive("sheep").getAsInt();
		    int wheat = bank.getAsJsonPrimitive("wheat").getAsInt();
		    int wood = bank.getAsJsonPrimitive("wood").getAsInt();	    
		    bankResources = new ResourceList(brick,ore,sheep,wheat,wood);
	    }
	    
	    //Get player resources
		for(int i = 0; i < players.size(); i++)
		{
			JsonObject player = players.get(i).getAsJsonObject();
			JsonObject resource = player.getAsJsonObject("resources");
			int index = player.getAsJsonPrimitive("playerIndex").getAsInt();
			ResourceList resources;
			
			int brick = resource.getAsJsonPrimitive("brick").getAsInt();
		    int ore = resource.getAsJsonPrimitive("ore").getAsInt();
		    int sheep = resource.getAsJsonPrimitive("sheep").getAsInt();
		    int wheat = resource.getAsJsonPrimitive("wheat").getAsInt();
		    int wood = resource.getAsJsonPrimitive("wood").getAsInt();
		    
		    resources = new ResourceList(brick,ore,sheep,wheat,wood);
			playerResources[index] = resources;
		}
		
        if(tradeOffer != null) //This is null if there is no current trade offers
        {
            int sender = tradeOffer.getAsJsonPrimitive("sender").getAsInt();
            int receiver = tradeOffer.getAsJsonPrimitive("receiver").getAsInt();
            
            ResourceList resourcesOffer;
            JsonObject offer = tradeOffer.getAsJsonObject("offer");
            {    	
                int brick = offer.getAsJsonPrimitive("brick").getAsInt();
                int ore = offer.getAsJsonPrimitive("ore").getAsInt();
                int sheep = offer.getAsJsonPrimitive("sheep").getAsInt();
                int wheat = offer.getAsJsonPrimitive("wheat").getAsInt();
                int wood = offer.getAsJsonPrimitive("wood").getAsInt();
                resourcesOffer = new ResourceList(brick, ore, sheep, wheat, wood);
            }
            tradeResourcesOffer = new TradeOffer(resourcesOffer, sender, receiver);
        }
		
		this.resourceManager = new ResourceManager(playerResources, bankResources, tradeResourcesOffer);
	}
	
	private void SetDevCardManager(JsonArray players)
	{	
	    PlayerDevCards newPlayerDevCards = new PlayerDevCards();
	    PlayerDevCards oldPlayerDevCards = new PlayerDevCards();
	    PlayerDevCards playedDevCards = new PlayerDevCards();
	    boolean[] hasPlayedDevCardsList = new boolean[4];
		
		for(int i = 0; i < players.size(); i++)
	    {
	    	JsonObject player = players.get(i).getAsJsonObject();
	    	int index = player.getAsJsonPrimitive("playerIndex").getAsInt();
	    	
	    	JsonObject newDevCards = player.getAsJsonObject("newDevCards");
	    	JsonObject oldDevCards = player.getAsJsonObject("oldDevCards");
	    	//New Dev Cards
	    	{
		    	int monopoly = newDevCards.getAsJsonPrimitive("monopoly").getAsInt();
		    	int monument = newDevCards.getAsJsonPrimitive("monument").getAsInt();
		    	int roadBuilding = newDevCards.getAsJsonPrimitive("roadBuilding").getAsInt();
		    	int soldier = newDevCards.getAsJsonPrimitive("soldier").getAsInt();
		    	int yearOfPlenty = newDevCards.getAsJsonPrimitive("yearOfPlenty").getAsInt();
		    	//<======Set part of devCarManager here=====>//
		    	newPlayerDevCards.setDevCardsForPlayer(index, new DevCardList(monopoly, monument, roadBuilding, soldier, yearOfPlenty));
	    	}
	    	//Old Dev Cards
	    	{
	    		int monopoly = oldDevCards.getAsJsonPrimitive("monopoly").getAsInt();
		    	int monument = oldDevCards.getAsJsonPrimitive("monument").getAsInt();
		    	int roadBuilding = oldDevCards.getAsJsonPrimitive("roadBuilding").getAsInt();
		    	int soldier = oldDevCards.getAsJsonPrimitive("soldier").getAsInt();
		    	int yearOfPlenty = oldDevCards.getAsJsonPrimitive("yearOfPlenty").getAsInt();
		    	//<======Set other part of devCarManager here=====>//
		    	oldPlayerDevCards.setDevCardsForPlayer(index, new DevCardList(monopoly, monument, roadBuilding, soldier, yearOfPlenty));
	    	}
	    	//Played Dev Cards
	    	{
	    		int monuments = player.getAsJsonPrimitive("monuments").getAsInt();
		    	int soldiers = player.getAsJsonPrimitive("soldiers").getAsInt();
		    	playedDevCards.setDevCardsForPlayer(index, new DevCardList(0,monuments,0,soldiers,0));
	    	}
	    	//Has Played Dev Cards List
	    	{
	    		boolean playedDevCard = player.getAsJsonPrimitive("playedDevCard").getAsBoolean();
	    		hasPlayedDevCardsList[index] = playedDevCard;
	    	}
	    }
		this.devCardManager = new DevCardManager(newPlayerDevCards, oldPlayerDevCards, playedDevCards, hasPlayedDevCardsList);
	}
	
	private void SetPlayerManager(JsonArray players)
	{    
	    for(int i = 0; i < players.size(); i++)
	    {
	    	JsonObject player = players.get(i).getAsJsonObject();
	    	int index = player.getAsJsonPrimitive("playerIndex").getAsInt();
	    	
	    	String name = player.getAsJsonPrimitive("name").getAsString();
	    	String color = player.getAsJsonPrimitive("color").getAsString();
	    	int playerId = player.getAsJsonPrimitive("playerID").getAsInt();
	    	int cities = player.getAsJsonPrimitive("cities").getAsInt();
	    	int settlements = player.getAsJsonPrimitive("settlements").getAsInt();
	    	int roads = player.getAsJsonPrimitive("roads").getAsInt();
	    	int victoryPoints = player.getAsJsonPrimitive("victoryPoints").getAsInt();
	    	boolean discarded = player.getAsJsonPrimitive("discarded").getAsBoolean();
	    	//<========Construct part of player manager here============>	    	
	    }
	    //<========Construct player manager here============>
	}
	
	private void SetMapManager(JsonObject map)
	{	    
	    JsonArray hexes = map.getAsJsonArray("hexes");
	    JsonArray ports = map.getAsJsonArray("ports");
	    JsonArray roads = map.getAsJsonArray("roads");
	    JsonArray settlements = map.getAsJsonArray("settlements");
	    JsonArray cities = map.getAsJsonArray("cities");
	    JsonObject robber = map.getAsJsonObject("robber");
	    int radius = map.getAsJsonPrimitive("radius").getAsInt();
	    
	    //Get Hex info
	    for(int i = 0; i < hexes.size(); i++)
	    {
	    	JsonObject hex = hexes.get(i).getAsJsonObject();    	
	    	JsonObject location = hex.getAsJsonObject("location");
	    	String resource;
	    	int number;
	    	
	    	int x = location.getAsJsonPrimitive("x").getAsInt();
	    	int y = location.getAsJsonPrimitive("y").getAsInt();
	    	
	    	//if hex doesn't have resource and number it is desert hex
	    	if(hex.has("resource") && hex.has("number"))
	    	{
	    		resource = hex.getAsJsonPrimitive("resource").getAsString();
	    		number = hex.getAsJsonPrimitive("number").getAsInt();
	    	}
	    }
	    
	    //Get Port info
	    for(int i = 0; i < ports.size(); i++)
	    {
	    	JsonObject port = ports.get(i).getAsJsonObject();
	    	JsonObject location = port.getAsJsonObject("location");
	    	String resource;
	    	
	    	int x = location.getAsJsonPrimitive("x").getAsInt();
	    	int y = location.getAsJsonPrimitive("y").getAsInt();
	    	
	    	//if port doesn't have resource than it is 3:1 and those are for any resource
	    	if(port.has("resource"))
	    		resource = port.getAsJsonPrimitive("resource").getAsString();
	    	String direction = port.getAsJsonPrimitive("direction").getAsString();
	    	int ratio = port.getAsJsonPrimitive("ratio").getAsInt();    	
	    }
	    
	    //Get road info
	    for(int i = 0; i < roads.size(); i++)
	    {
	    	JsonObject road = roads.get(i).getAsJsonObject();
	    	JsonObject location = road.getAsJsonObject("location");
	    	
	    	int x = location.getAsJsonPrimitive("x").getAsInt();
	    	int y = location.getAsJsonPrimitive("y").getAsInt();
	    	int owner = road.getAsJsonPrimitive("owner").getAsInt();
	    	String direction = location.getAsJsonPrimitive("direction").getAsString();
	    }
	    
	    //Get settlement info
	    for(int i = 0; i < settlements.size(); i++)
	    {
	    	JsonObject settlement = roads.get(i).getAsJsonObject();
	    	JsonObject location = settlement.getAsJsonObject("location");
	    	
	    	int x = location.getAsJsonPrimitive("x").getAsInt();
	    	int y = location.getAsJsonPrimitive("y").getAsInt();
	    	int owner = settlement.getAsJsonPrimitive("owner").getAsInt();
	    	String direction = location.getAsJsonPrimitive("direction").getAsString();
	    }
	    
	    //Get city info
	    for(int i = 0; i < cities.size(); i++)
	    {
	    	JsonObject city = roads.get(i).getAsJsonObject();
	    	JsonObject location = city.getAsJsonObject("location");
	    	
	    	int x = location.getAsJsonPrimitive("x").getAsInt();
	    	int y = location.getAsJsonPrimitive("y").getAsInt();
	    	int owner = city.getAsJsonPrimitive("owner").getAsInt();
	    	String direction = location.getAsJsonPrimitive("direction").getAsString();
	    }
	    
	    int x = robber.getAsJsonPrimitive("x").getAsInt();
	    int y = robber.getAsJsonPrimitive("y").getAsInt();
	}
	
	private void SetChatManager(JsonObject chat, JsonObject log)
	{
	    List<Line> chatMessages = new ArrayList<Line>();
	    List<Line> gameHistory = new ArrayList<Line>();
	    //Get chat info
	    {
		    JsonArray lines = chat.getAsJsonArray("lines");
		    for(int i = 0; i < lines.size(); i++)
		    {
		    	JsonObject line = lines.get(i).getAsJsonObject();
		    	
		    	String message = line.getAsJsonPrimitive("message").getAsString();
		    	String source = line.getAsJsonPrimitive("source").getAsString();
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
		    	
		    	String message = line.getAsJsonPrimitive("message").getAsString();
		    	String source = line.getAsJsonPrimitive("source").getAsString();
		    	Line l = new Line(message,source);
		    	gameHistory.add(l);
		    }	    	
	    }
	    this.chatManager = new ChatManager(chatMessages, gameHistory);
	}
	
	private void SetTurnManager(JsonObject turnTracker)
	{
		int currentTurn = turnTracker.getAsJsonPrimitive("currentTurn").getAsInt();
	    String status = turnTracker.getAsJsonPrimitive("status").getAsString();
	    int longestRoad = turnTracker.getAsJsonPrimitive("longestRoad").getAsInt();
	    int largestArmy = turnTracker.getAsJsonPrimitive("largestArmy").getAsInt();
	}
	
	/**
	 * Parse through the JSON string received from the server and translate into GameModelJSON
	 * object
	 * 
	 * @param jsonObject String received from the server API
	 * @return The now parsed GameModelJSON object.
	 */
	private CatanModel _deserialize(String json)
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
	    int version = jobject.getAsJsonPrimitive("version").getAsInt();
	    int winner = jobject.getAsJsonPrimitive("winner").getAsInt();
	    
	    SetResourceManager(bank, players, tradeOffer);
	    SetDevCardManager(players);
	    SetPlayerManager(players);
	    SetMapManager(map);
	    SetChatManager(chat, log);
	    SetTurnManager(turnTracker);
	    
		return catanModel = new CatanModel(resourceManager, devCardManager, playerManager, mapManager, chatManager);
	}
}
