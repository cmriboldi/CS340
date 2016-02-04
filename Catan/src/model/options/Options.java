package model.options;

import model.CatanModel;
import model.players.PlayerTurnTracker;
import shared.definitions.DevCardType;
import shared.exceptions.resources.TradeOfferNullException;
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
		return canPlay(playerIndex) && catanModel.cardManager.hasPlayedDevCard(playerIndex);
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
	 * Check if a player can trade
	 * 
	 * @param playerIndex index of the player asking to trade.
	 * @return True if allowed False otherwise
	 */
	public boolean canTrade(int playerIndex)
	{
		boolean canTrade = false; 
		
		try
		{
			canTrade = canPlay(playerIndex) || catanModel.resourceManager.canTrade(playerIndex);
		} catch (TradeOfferNullException e)
		{
			System.out.println("Exception: " + e);
			e.printStackTrace();
		}
		
		return canTrade;
	}

	/**
	 * Check if a player can trade via port
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canMaritimeTrade(int playerIndex)
	{
		return true;
	}

	/**
	 * Check if it is a player turn
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canPlay(int playerIndex)
	{
		
		PlayerTurnTracker turnTracker = catanModel.getPlayerManager().getTurnTracker(); 
		System.out.println("Can Play Method"); 
		
		if (playerIndex == turnTracker.getTurnIndex() && turnTracker.getStatus().equals("Playing")) return true; 
		else return false; 
		
	}

	
	public boolean canRollNumber(int playerIndex)
	{
		
		PlayerTurnTracker turnTracker = catanModel.getPlayerManager().getTurnTracker(); 
		
		if (playerIndex == turnTracker.getTurnIndex() && turnTracker.getStatus().equals("Rolling")) return true; 
		else return false; 	
	}

	public boolean canPlaceRobber(int playerIndex)
	{
		
		PlayerTurnTracker turnTracker = catanModel.getPlayerManager().getTurnTracker(); 
		
		if (playerIndex == turnTracker.getTurnIndex() && turnTracker.getStatus().equals("Robbing")) return true; 
		else return false; 	
	}
	
	public boolean canDiscardCards(int playerIndex)
	{
		
		PlayerTurnTracker turnTracker = catanModel.getPlayerManager().getTurnTracker(); 
		
		if (playerIndex == turnTracker.getTurnIndex() && turnTracker.getStatus().equals("Discarding")) return true; 
		else return false; 	
	}
	
	public boolean canFinishTurn(int playerIndex)
	{
		
		PlayerTurnTracker turnTracker = catanModel.getPlayerManager().getTurnTracker(); 
		
		if (playerIndex == turnTracker.getTurnIndex() && turnTracker.getStatus().equals("Playing")) return true; 
		else return false; 	
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
		return true;
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
		return true;
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
		return true;
	}
}
