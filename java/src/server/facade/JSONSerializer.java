package server.facade;

import com.google.gson.*;
import model.CatanModel;
import model.development.*;
import model.map.*;
import model.messagelog.*;
import model.players.*;
import model.resources.*;
import serverProxy.JSONDeserializer;
import shared.definitions.*;
import shared.exceptions.player.*;
import shared.locations.*;
import shared.communication.IdNumber;

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
	
	private JsonObject catan;
	private JsonObject bank;
	private JsonObject chat;
	private JsonObject log;
	private JsonObject map;
	private JsonObject players;
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
			hex.addProperty("resource", h.getResource().toString());
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
			port.addProperty("resource", p.getType().toString());
			port.add("location", location);
			port.addProperty("direction", p.getRatio());
			port.addProperty("ratio", p.location.getDir().toString());
						
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
	}
	
	private void setPlayers()
	{
		Player[] playerList = playerManager.getCatanPlayers();
		for(Player p : playerList)
		{
			players.addProperty("cities", p.getCitiesRemaining());
			players.addProperty("color", p.getColor().toString());
			players.addProperty("discarded", resourceManager.hasPlayerDiscarded(p.getPlayerIndex()));
			players.addProperty("monuments", devCardManager.playedDevCardCount(p.getPlayerIndex(), DevCardType.MONUMENT));
			players.addProperty("name", p.getName());
		}
	}
	
	private String _serialize(CatanModel model)
	{
		this.resourceManager = model.resourceManager;
		this.devCardManager = model.cardManager;
		this.playerManager = model.playerManager;
		this.mapManager = model.mapManager;
		this.chatManager = model.chatManager;
		
		catan = new JsonObject();
		
		catan.add("bank", bank);
		catan.add("chat", chat);
		catan.add("log", log);
		catan.add("map", map);
		catan.add("players", players);
		catan.add("tradeOffer", tradeOffer);
		catan.add("turnTracker", turnTracker);
		
		return catan.getAsString();
	}
	
	public static String serialize(CatanModel model) throws TurnIndexException, InvalidTurnStatusException, GeneralPlayerException
	{
		return instance()._serialize(model);
	}
}
