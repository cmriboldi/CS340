package model.options;

import model.CatanModel;
import shared.definitions.DevCardType;
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
		return true;
	}

	/**
	 * Check if a player can afford a town
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordTown(int playerIndex)
	{
		return true;
	}

	/**
	 * Check if a player can afford a city
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordCity(int playerIndex)
	{
		return true;
	}
	
	/**
	 * Check if a player can afford a development card
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canAffordDevCard(int playerIndex)
	{
		return true;
	}
	
	/**
	 * Check if a player can play a development card
	 * 
	 * @param playerIndex
	 * @return
	 */
	public boolean canPlayDevCard(int playerIndex)
	{
		//call playerTurnManager to see if it players turn.
		//call hasPlayedDevCard(playerIndex) to see if a player has played a devCard this turn.
		return true;
	}
	
	/**
	 * Check if a player has a development card of the given type.
	 * 
	 * @param playerIndex index of the player.
	 * @param card DevCardType of the card in question.
	 * @return
	 */
	public boolean hasDevCard(int playerIndex, DevCardType card)
	{
		//call devCardManager to see if player has card of given type.
		return true;
	}
	
	/**
	 * Check if there are enough cards in the development card stack to draw a card.
	 * 
	 * @return
	 */
	public boolean canDrawDevCard()
	{
		// call devCardManager to see if there enough cards in development card deck.
		return true;
	}

	/**
	 * Check if a player can trade
	 * 
	 * @param playerIndex index
	 * @return True if allowed False otherwise
	 */
	public boolean canTrade(int playerIndex)
	{
		return true;
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
		return true;
	}

	
	public boolean canRoll(int playerIndex)
	{
		
		return true;
	}

	
	public boolean canRob(int playerIndex)
	{
		
		return true;
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
