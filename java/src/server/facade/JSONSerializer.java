package server.facade;

import com.google.gson.*;
import model.CatanModel;
import model.development.*;
import model.map.*;
import model.messagelog.*;
import model.players.*;
import model.resources.*;
import serverProxy.JSONDeserializer;
import serverProxy.JsonLoader;
import shared.definitions.*;
import shared.exceptions.player.*;
import shared.locations.*;
import shared.communication.IdNumber;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class JSONSerializer {

	private static JSONSerializer _instance;
	
	private JSONSerializer(){}
	
	private static JSONSerializer instance() {
		
		if (_instance == null)
			_instance = new JSONSerializer();

		return _instance;
	}
	
	private ResourceManager resourceManager;
	private DevCardManager devCardManager;
	private PlayerManager playerManager;
	private MapManager mapManager;
	private ChatManager chatManager;
	private PlayerTurnTracker playerTurnTracker;
	
	private JsonObject catan;
	private JsonObject bank;
	private JsonObject deck;
	private JsonObject chat;
	private JsonObject log;
	private JsonObject map;
	private JsonArray players;
	private JsonObject tradeOffer;
	private JsonObject turnTracker;
	
	
	private void setBank()
	{		
		bank = new JsonObject();
		
		bank.addProperty("brick", resourceManager.getBankResourceCount(ResourceType.BRICK));
		bank.addProperty("ore", resourceManager.getBankResourceCount(ResourceType.ORE));
		bank.addProperty("sheep", resourceManager.getBankResourceCount(ResourceType.SHEEP));
		bank.addProperty("wheat", resourceManager.getBankResourceCount(ResourceType.WHEAT));
		bank.addProperty("wood", resourceManager.getBankResourceCount(ResourceType.WOOD));
		
		//System.out.println(bank.toString());
		catan.add("bank", bank);
	}
	
	private void setDeck()
	{
		deck = new JsonObject();
		
		deck.addProperty("monopoly", devCardManager.getDevCardStack().getCardTypeCount(DevCardType.MONOPOLY));
		deck.addProperty("monument", devCardManager.getDevCardStack().getCardTypeCount(DevCardType.MONUMENT));
		deck.addProperty("roadBuilding", devCardManager.getDevCardStack().getCardTypeCount(DevCardType.ROAD_BUILD));
		deck.addProperty("soldier", devCardManager.getDevCardStack().getCardTypeCount(DevCardType.SOLDIER));
		deck.addProperty("yearOfPlenty", devCardManager.getDevCardStack().getCardTypeCount(DevCardType.YEAR_OF_PLENTY));
		
		//System.out.println(deck.toString());
		catan.add("deck", deck);
	}
	
	private void setChat()
	{
		chat = new JsonObject();
		
		JsonArray lines = new JsonArray();		
		List<Line> list = chatManager.getChatMessages();
		for(Line l : list)
		{
			String message = l.getMessage();
			String source = l.getSource();
			
			JsonObject line = new JsonObject();
			line.addProperty("message", message);
			line.addProperty("source", source);
			
			lines.add(line);
		}		
		chat.add("lines", lines);
		
		//System.out.println(chat.toString());
		catan.add("chat", chat);
	}
	
	private void setLog()
	{
		log = new JsonObject();
		
		JsonArray lines = new JsonArray();
		List<Line> list = chatManager.getGameHistory();
		for(Line l : list)
		{
			String message = l.getMessage();
			String source = l.getSource();
			
			JsonObject line = new JsonObject();
			line.addProperty("message", message);
			line.addProperty("source", source);
			
			lines.add(line);
		}		
		log.add("lines", lines);
		
		//System.out.println(log.toString());
		catan.add("log", log);
	}
	
	private void setMap()
	{
		map = new JsonObject();
		
		JsonArray hexes = new JsonArray();
		HashMap<HexLocation, Hex> hexesMap = mapManager.getHexes();
		for(Hex h : hexesMap.values())
		{
			JsonObject hex = new JsonObject();			
			JsonObject location = new JsonObject();
			
			
			location.addProperty("x", h.location.getX());
			location.addProperty("y", h.location.getY());
			hex.add("location", location);
			hex.addProperty("resource", h.getResource().toString().toLowerCase());
			hex.addProperty("number", h.getNumber());
						
			hexes.add(hex);
		}
		
		JsonArray ports = new JsonArray();
		HashMap<EdgeLocation, Port> portsMap = mapManager.getPorts();
		for(Port p : portsMap.values())
		{
			JsonObject port = new JsonObject();			
			JsonObject location = new JsonObject();
			
			
			location.addProperty("x", p.location.getHexLoc().getX());
			location.addProperty("y", p.location.getHexLoc().getY());
			if(p.getType() != PortType.THREE)
				port.addProperty("resource", p.getType().toString().toLowerCase());
			port.add("location", location);
			port.addProperty("direction", p.location.getDir().toString());
			port.addProperty("ratio", p.getRatio());
						
			ports.add(port);
		}
		
		JsonArray roads = new JsonArray();
		HashMap<EdgeLocation, Road> roadsMap = mapManager.getRoads();
		for(Road r : roadsMap.values())
		{
			JsonObject road = new JsonObject();			
			JsonObject location = new JsonObject();
			
			
			location.addProperty("x", r.location.getHexLoc().getX());
			location.addProperty("y", r.location.getHexLoc().getY());
			road.addProperty("owner", r.getOwner());
			road.add("location", location);
			road.addProperty("direction", r.location.getDir().toString());
						
			roads.add(road);
		}
		
		JsonArray setts = new JsonArray();
		HashMap<VertexLocation, Settlement> settMap = mapManager.getSettlements();
		for(Settlement s : settMap.values())
		{
			if(s.isCity())
				continue;
			
			JsonObject sett = new JsonObject();			
			JsonObject location = new JsonObject();
			
			
			location.addProperty("x", s.location.getHexLoc().getX());
			location.addProperty("y", s.location.getHexLoc().getY());
			sett.addProperty("owner", s.getPlayer());
			sett.add("location", location);
			sett.addProperty("direction", s.location.getDir().toString());
						
			setts.add(sett);
		}
		
		JsonArray cities = new JsonArray();
		for(Settlement s : settMap.values())
		{
			if(!s.isCity())
				continue;
			
			JsonObject sett = new JsonObject();			
			JsonObject location = new JsonObject();
			
			
			location.addProperty("x", s.location.getHexLoc().getX());
			location.addProperty("y", s.location.getHexLoc().getY());
			sett.addProperty("owner", s.getPlayer());
			sett.add("location", location);
			sett.addProperty("direction", s.location.getDir().toString());
						
			cities.add(sett);
		}
		
		map.add("hexes", hexes);
		map.add("ports", ports);
		map.add("roads", roads);
		map.add("settlements", setts);
		map.add("cities", cities);
		
		map.addProperty("radius", mapManager.getMapRadius());
		
		JsonObject robber = new JsonObject();
		robber.addProperty("x", mapManager.getRobber().getX());
		robber.addProperty("y", mapManager.getRobber().getY());
		
		map.add("robber", robber);	
		
		//System.out.println(map.toString());
		catan.add("map", map);
	}
	
	private void setPlayers()
	{
		Player[] playerList = playerManager.getCatanPlayers();
		players = new JsonArray();
		
		for(Player p : playerList)
		{
			if(p != null)
			{
				JsonObject player = new JsonObject();

				player.addProperty("cities", p.getCitiesRemaining());
				player.addProperty("color", p.getColor().toString());
				player.addProperty("discarded", resourceManager.hasPlayerDiscarded(p.getPlayerIndex()));
				player.addProperty("monuments", devCardManager.playedDevCardCount(p.getPlayerIndex(), DevCardType.MONUMENT));
				player.addProperty("name", p.getName());

				JsonObject newDevCards = new JsonObject();
				newDevCards.addProperty("monopoly", devCardManager.getNewDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.MONOPOLY));
				newDevCards.addProperty("monument", devCardManager.getNewDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.MONUMENT));
				newDevCards.addProperty("roadBuilding", devCardManager.getNewDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.ROAD_BUILD));
				newDevCards.addProperty("soldier", devCardManager.getNewDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.SOLDIER));
				newDevCards.addProperty("yearOfPlenty", devCardManager.getNewDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.YEAR_OF_PLENTY));
				player.add("newDevCards", newDevCards);

				JsonObject oldDevCards = new JsonObject();
				oldDevCards.addProperty("monopoly", devCardManager.getOldDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.MONOPOLY));
				oldDevCards.addProperty("monument", devCardManager.getOldDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.MONUMENT));
				oldDevCards.addProperty("roadBuilding", devCardManager.getOldDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.ROAD_BUILD));
				oldDevCards.addProperty("soldier", devCardManager.getOldDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.SOLDIER));
				oldDevCards.addProperty("yearOfPlenty", devCardManager.getOldDevCards().getCardTypeCountForPlayer(p.getPlayerIndex(), DevCardType.YEAR_OF_PLENTY));
				player.add("oldDevCards", oldDevCards);

				player.addProperty("playerIndex", p.getPlayerIndex());
				player.addProperty("playedDevCard", devCardManager.hasPlayedDevCard(p.getPlayerIndex()));
				player.addProperty("playerID", p.getId());

				JsonObject resources = new JsonObject();
				resources.addProperty("brick", resourceManager.getResourceCount(p.getPlayerIndex(), ResourceType.BRICK));
				resources.addProperty("ore", resourceManager.getResourceCount(p.getPlayerIndex(), ResourceType.ORE));
				resources.addProperty("sheep", resourceManager.getResourceCount(p.getPlayerIndex(), ResourceType.SHEEP));
				resources.addProperty("wheat", resourceManager.getResourceCount(p.getPlayerIndex(), ResourceType.WHEAT));
				resources.addProperty("wood", resourceManager.getResourceCount(p.getPlayerIndex(), ResourceType.WOOD));
				player.add("resources", resources);

				player.addProperty("roads", p.getRoadsRemaining());
				player.addProperty("settlements", p.getSettlementsRemaining());
				player.addProperty("soldiers", devCardManager.playedDevCardCount(p.getPlayerIndex(), DevCardType.SOLDIER));
				player.addProperty("victoryPoints", p.getPoints());

				players.add(player);
			}
			else
			{
				JsonElement nullObject = null;
				players.add(nullObject);
			}

		}
		
		//System.out.println(players.toString());
		catan.add("players", players);
	}
	
	private void setTradeOffer()
	{
		if(resourceManager.getTradeOffer() == null)
			return;
		
		tradeOffer.addProperty("sender", resourceManager.getTradeOffer().getSender());
		tradeOffer.addProperty("receiver", resourceManager.getTradeOffer().getReceiver());
		
		JsonObject offer = new JsonObject();
		offer.addProperty("brick", resourceManager.getTradeOffer().getResourcesOffer().getResourceTypeCount(ResourceType.BRICK));
		offer.addProperty("ore", resourceManager.getTradeOffer().getResourcesOffer().getResourceTypeCount(ResourceType.ORE));
		offer.addProperty("sheep", resourceManager.getTradeOffer().getResourcesOffer().getResourceTypeCount(ResourceType.SHEEP));
		offer.addProperty("wheat", resourceManager.getTradeOffer().getResourcesOffer().getResourceTypeCount(ResourceType.WHEAT));
		offer.addProperty("wood", resourceManager.getTradeOffer().getResourcesOffer().getResourceTypeCount(ResourceType.WOOD));
		tradeOffer.add("offer", offer);
		
		//System.out.println(tradeOffer.toString());
		catan.add("tradeOffer", tradeOffer);
	}
	
	private void setTurnTracker()
	{
		turnTracker = new JsonObject();

		turnTracker.addProperty("currentTurn", playerTurnTracker.getTurnIndex());
		turnTracker.addProperty("status", playerTurnTracker.getStatus().toString());
		turnTracker.addProperty("longestRoad", playerManager.getIndexOfLongestRoad());
		turnTracker.addProperty("largestArmy", playerManager.getIndexOfLargestArmy());
		
		//System.out.println(turnTracker.toString());
		catan.add("turnTracker", turnTracker);
	}
	
	private String _serialize(CatanModel model)
	{
		this.resourceManager = model.resourceManager;
		this.devCardManager = model.cardManager;
		this.playerManager = model.playerManager;
		this.mapManager = model.mapManager;
		this.chatManager = model.chatManager;
		this.playerTurnTracker = playerManager.turnTracker;
		
		catan = new JsonObject();
		
		this.setBank();
		this.setDeck();
		this.setChat();
		this.setLog();
		this.setMap();
		this.setPlayers();
		this.setTradeOffer();
		this.setTurnTracker();
		catan.addProperty("version", model.getVersion());
		catan.addProperty("winner", -1);
		
		//System.out.println(catan.toString());
		return catan.toString();
	}
	
	public static String serialize(CatanModel model)
	{
		return instance()._serialize(model);
	}
	
	public static void test() throws TurnIndexException, InvalidTurnStatusException, GeneralPlayerException, IOException{
		instance()._serialize(JSONDeserializer.deserialize(JsonLoader.readFile("/Users/JPPowers/OneDrive/CS Dev/workspace340/CS340/json/default.json")));
	}
}