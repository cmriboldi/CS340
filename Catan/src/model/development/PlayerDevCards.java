package model.development;

/** The PlayerDevCards class is a container for all four players development cards.
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/
import shared.definitions.DevCardType;

public class PlayerDevCards
{
	private DevCardList[] playerDevCards;
	
	public PlayerDevCards()
	{
		
	}
	
	/**
	 * Adds a development card to the given players hand.
	 * @param devCard A DevCardType specifying the type of development card to add.
	 * @param playerIndex The index of the player receiving the development card.
	 */
	public void addCardToPlayer(DevCardType devCard, int playerIndex)
	{
		
	}
	
	/**
	 * Removes a card from the given player's development card hand.
	 * @param devCard A DevCardType specifying the type of development card to remove.
	 * @param playerIndex The index of the player losing the development card.
	 */
	public void removeCardFromPlayer(DevCardType devCard, int playerIndex)
	{
		
	}
	
	/**
	 * Returns the DevCardList for the given player.
	 * @param playerIndex The index of the player requesting the development list.
	 * @return The DevCardList for the given player.
	 */
	public DevCardList getDevCardsForPlayer(int playerIndex)
	{
		return playerDevCards[playerIndex];
	}
	
}
