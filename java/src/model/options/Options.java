package model.options;

import java.util.Set;

import model.CatanModel;
import model.players.PlayerTurnTracker;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.VertexLocation;

/**
 * The Options class is in charge of all canDo methods.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class Options
{
	
	CatanModel catanModel = null; 

	public Options(CatanModel catanModel)
	{
		this.catanModel = catanModel; 
	}

	/**
	 * Check if a player can afford a road
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordRoad(int playerIndex)
	{
		return catanModel.resourceManager.canAffordRoad(playerIndex);
	}

	/**
	 * Check if a player can afford a town
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordTown(int playerIndex)
	{
		return catanModel.resourceManager.canAffordTown(playerIndex);
	}

	/**
	 * Check if a player can afford a city
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordCity(int playerIndex)
	{
		return catanModel.resourceManager.canAffordCity(playerIndex);
	}
	
	/**
	 * Check if a player can afford a development card
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordDevCard(int playerIndex)
	{
		return catanModel.resourceManager.canAffordDevCard(playerIndex);
	}
	
	public boolean canBuyDevCard(int playerIndex)
	{
		return canPlay(playerIndex) && canAffordDevCard(playerIndex);
	}
	
	public boolean canUseYearOfPlenty(int playerIndex)
	{
		return canPlayDevCard(playerIndex) && hasDevCard(playerIndex, DevCardType.YEAR_OF_PLENTY);
	}
	
	public boolean canUseRoadBuilder(int playerIndex)
	{
		return canPlayDevCard(playerIndex) && hasDevCard(playerIndex, DevCardType.ROAD_BUILD);
	}
	
	public boolean canUseSoldier(int playerIndex)
	{
		return canPlayDevCard(playerIndex) && hasDevCard(playerIndex, DevCardType.SOLDIER);
	}
	
	public boolean canUseMonopoly(int playerIndex)
	{
		return canPlayDevCard(playerIndex) && hasDevCard(playerIndex, DevCardType.MONOPOLY);
	}
	
	public boolean canUseMonument(int playerIndex)
	{
		return canPlayDevCard(playerIndex) && hasDevCard(playerIndex, DevCardType.MONUMENT);
	}
	
	/**
	 * Check if a player can play a development card
	 * 
	 * @param playerIndex
	 * @return
	 */
	public boolean canPlayDevCard(int playerIndex)
	{
		return canPlay(playerIndex) && !catanModel.cardManager.hasPlayedDevCard(playerIndex);
	}
	
	/**
	 * Check if a player has an un-played development card of the given type.
	 * 
	 * @param playerIndex index of the player.
	 * @param card DevCardType of the card in question.
	 * @return
	 */
	public boolean hasDevCard(int playerIndex, DevCardType card)
	{
		return catanModel.cardManager.hasDevCard(playerIndex, card);
	}

	/**
	 * Check if there are enough cards in the development card stack to draw a card.
	 * @param playerIndex The index of the player who needs to see if they can draw a card.
	 * @return
	 */
	public boolean canDrawDevCard(int playerIndex)
	{
		return canPlay(playerIndex) && catanModel.cardManager.canDrawDevCard();
	}

	/**
	 * Will check if the player is offering a trade to the player who is playing or is the player who's turn it is.
	 * @param toPlayerIndex index of the player receiving the trade offer.
	 * @param fromPlayerIndex index of the player making the trade offer.
	 * @return
	 */
	public boolean canOfferTrade(int toPlayerIndex, int fromPlayerIndex)
	{	
		return canPlay(toPlayerIndex) || canPlay(fromPlayerIndex);
	}

	/**
	 * Check if a player can trade via port
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public Set<PortType> canMaritimeTrade(int playerIndex)
	{	
		return catanModel.mapManager.getMap().canMaritimeTrade(playerIndex);
	}

	/**
	 * Check if it is a player turn
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canPlay(int playerIndex)
	{
		PlayerTurnTracker turnTracker = catanModel.playerManager.getTurnTracker(); 		
		return (playerIndex == turnTracker.getTurnIndex() && turnTracker.getStatus().equals("Playing"));
	}

	
	public boolean canRollNumber(int playerIndex)
	{
		PlayerTurnTracker turnTracker = catanModel.playerManager.getTurnTracker(); 
		return(playerIndex == turnTracker.getTurnIndex() && turnTracker.getStatus().equals("Rolling"));
	}

	public boolean canPlaceRobber(int playerIndex)
	{
		PlayerTurnTracker turnTracker = catanModel.playerManager.getTurnTracker(); 
		return(playerIndex == turnTracker.getTurnIndex() && turnTracker.getStatus().equals("Robbing"));
	}
	
	public boolean canDiscardCards(int playerIndex)
	{	
		PlayerTurnTracker turnTracker = catanModel.playerManager.getTurnTracker(); 
		return (turnTracker.getStatus().equals("Discarding"));
	}
	
	public boolean canFinishTurn(int playerIndex)
	{
		return canPlay(playerIndex);
	}
	

	/**
	 * Check if a road can be placed in specific position
	 * 
	 * @param playerIndex index
	 * @param location
	 * @return True if allowed False otherwise
	 */
	public boolean canPlaceRoad(int playerIndex, EdgeLocation location)
	{
		return canPlay(playerIndex) && catanModel.mapManager.getMap().canPlaceRoad(location, playerIndex);
	}

	/**
	 * Check if a town can be placed in specific position
	 * 
	 * @param playerIndex index
	 * @param location
	 * @return True if allowed False otherwise
	 */
	public boolean canPlaceTown(int playerIndex, VertexLocation location)
	{
		return canPlay(playerIndex) && catanModel.mapManager.getMap().canPlaceSettlement(location, playerIndex);
	}

	/**
	 * Check if a city can be placed in specific position
	 * 
	 * @param playerIndex index
	 * @param location
	 * @return True if allowed False otherwise
	 */
	public boolean canPlaceCity(int playerIndex, VertexLocation location)
	{
		return canPlay(playerIndex) && catanModel.mapManager.getMap().canPlaceCity(location, playerIndex);
	}
}
