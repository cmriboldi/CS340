package model;

import model.development.DevCardManager;
import model.map.MapManager;
import model.messagelog.ChatManager;
import model.options.Options;
import model.players.PlayerManager;
import model.players.PlayerTurnTracker;
import model.resources.ResourceManager;

/**
 * The CatanModel contains all of the Manager classes and is a facade to all of the data.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class CatanModel
{

	public DevCardManager cardManager;
	public MapManager mapManager;
	public ChatManager chatManager;
	public Options options;
	public PlayerManager playerManager;
	public ResourceManager resourceManager;
	public PlayerTurnTracker turnTracker;
	public int version;

	public CatanModel()
	{

	}

	public CatanModel(ResourceManager resourceManager, DevCardManager devCardManager, PlayerManager playerManager, MapManager mapManager, ChatManager chatManager, int version)
	{
		this.cardManager = devCardManager;
		this.mapManager = mapManager;
		this.chatManager = chatManager;
		this.playerManager = playerManager;
		this.resourceManager = resourceManager;
		this.version = version;
		this.options = new Options(this);		
	}

	public DevCardManager getCardManager() {
		return cardManager;
	}

	public void setCardManager(DevCardManager cardManager) {
		this.cardManager = cardManager;
	}

	public MapManager getMapManager() {
		return mapManager;
	}

	public void setMapManager(MapManager mapManager) {
		this.mapManager = mapManager;
	}

	public ChatManager getChatManager() {
		return chatManager;
	}

	public void setChatManager(ChatManager chatManager) {
		this.chatManager = chatManager;
	}

	public Options getOptions() {
		return options;
	}

	public void setOptions(Options options) {
		this.options = options;
	}

	public PlayerManager getPlayerManager() {
		return playerManager;
	}

	public void setPlayerManager(PlayerManager playerManager) {
		this.playerManager = playerManager;
	}

	public ResourceManager getResourceManager() {
		return resourceManager;
	}

	public void setResourceManager(ResourceManager resourceManager) {
		this.resourceManager = resourceManager;
	}

	public PlayerTurnTracker getTurnTracker() {
		return turnTracker;
	}

	public void setTurnTracker(PlayerTurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}
}
