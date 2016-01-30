package model;

import model.development.DevCardManager;
import model.map.MapManager;
import model.messagelog.ChatManager;
import model.options.Options;
import model.players.PlayerManager;
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

	public CatanModel()
	{

	}

	public CatanModel(ResourceManager resourceManager, DevCardManager devCardManager, PlayerManager playerManager, MapManager mapManager, ChatManager chatManager)
	{
		this.cardManager = devCardManager;
		this.mapManager = mapManager;
		this.chatManager = chatManager;
		this.playerManager = playerManager;
		this.resourceManager = resourceManager;
		options = new Options();
	}

	public void setCardManager(DevCardManager cardManager)
	{
		this.cardManager = cardManager;
	}

	public void setMapManager(MapManager mapManager)
	{
		this.mapManager = mapManager;
	}

	public void setChatManager(ChatManager chatManager)
	{
		this.chatManager = chatManager;
	}
	
	public void setOptions(Options options)
	{
		this.options = options;
	}

	public void setPlayerManager(PlayerManager playerManager)
	{
		this.playerManager = playerManager;
	}
	
	public void setResourceManager(ResourceManager resourceManager)
	{
		this.resourceManager = resourceManager;
	}

}
