package model.players;

import java.util.ArrayList;

import client.data.PlayerInfo;
import clientfacade.Facade;
import shared.definitions.CatanColor;
import shared.exceptions.player.*;

/**
 * The PlayerManager generally manages turns and player game flow
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class PlayerManager
{

	/** An array of the current players logged in to play Catan */
	public Player[] catanPlayers;
	public PlayerTurnTracker turnTracker = null; 
	int indexOfLargestArmy = -1; 
	int indexOfLongestRoad = -1; 
	

	public int getIndexOfLargestArmy() {
		return indexOfLargestArmy;
	}

	public void setIndexOfLargestArmy(int indexOfLargestArmy) {
		this.indexOfLargestArmy = indexOfLargestArmy;
	}

	public int getIndexOfLongestRoad() {
		return indexOfLongestRoad;
	}

	public void setIndexOfLongestRoad(int indexOfLongestRoad) {
		this.indexOfLongestRoad = indexOfLongestRoad;
	}

	public PlayerManager()
	{
		catanPlayers = new Player[4];
		turnTracker = new PlayerTurnTracker();
	}
	
	public Player[] getCatanPlayers() {
		return catanPlayers;
	}

	public void setCatanPlayers(Player[] catanPlayers) {
		this.catanPlayers = catanPlayers;
	}

	public PlayerTurnTracker getTurnTracker() {
		return turnTracker;
	}

	public void setTurnTracker(PlayerTurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	public void setStatus(String status)
	{
		this.turnTracker.setStatus(status);
	}
	
	
	public CatanColor getPlayerColor(String nameCheck) throws PlayerNameNotFoundException
	{
		
		for (int i = 0; i < catanPlayers.length; i++)
		{
			Player player = catanPlayers[i]; 
			if (player.getName().equals(nameCheck)) 
			{
				return player.getColor();
			}
		}
		
		throw new PlayerNameNotFoundException();
	}

	public int getIndexFromId(int id)
	{
		int playerIndex = -1;
		for (int i = 0; i < catanPlayers.length; i++)
		{
			Player player = catanPlayers[i]; 
			if(player.getId() == id)
			{
				playerIndex = player.getPlayerIndex();
			}
		}
		return playerIndex;
	}
	
	public Player getPlayerByIndex(int index)
	{
		return catanPlayers[index];
	}

	public PlayerInfo[] getOpponentsInfo()
	{
		int localPlayer = Facade.getLocalPlayerIndex();
		
		ArrayList<PlayerInfo> playersInfo = new ArrayList<PlayerInfo>();
		for(int i = 0; i < catanPlayers.length; i++) {
			if(catanPlayers[i].getPlayerIndex() != localPlayer) {
				playersInfo.add((new PlayerInfo(catanPlayers[i].getId(), catanPlayers[i].getPlayerIndex(), catanPlayers[i].getName(), catanPlayers[i].getColor().toString())));
			}
		}
		
		PlayerInfo[] arrPlayerInfo = new PlayerInfo[playersInfo.size()];
		playersInfo.toArray(arrPlayerInfo);
		return arrPlayerInfo;
	}


}
