package model.development;

import definitions.DevCardType;
import exception.NotEnoughDevCardsException;

/** The PlayerDevCards class is a container for all four players development cards.
* @author Christian Riboldi
* @author Clayton Condie
* @author Jacob Brewer
* @author Joshua Powers
* @author Joshua Van Steeter
* @version 1.0 Build Jan, 2016.
*/

public class PlayerDevCards
{
	private DevCardList[] playerDevCards;

	public PlayerDevCards()
	{
		playerDevCards = new DevCardList[4];
		for (int i = 0; i < 4; i++)
		{
			playerDevCards[i] = new DevCardList();
		}
	}
	
	public int length()
	{
		return playerDevCards.length;
	}

	/**
	 * Adds a development card to the given players hand.
	 * 
	 * @param devCard A DevCardType specifying the type of development card to add.
	 * @param playerIndex The index of the player receiving the development card.
	 */
	public void addCardToPlayer(DevCardType devCard, int playerIndex)
	{
		playerDevCards[playerIndex].addDevCard(devCard);
	}

	/**
	 * Removes a card from the given player's development card hand.
	 * 
	 * @param devCard A DevCardType specifying the type of development card to remove.
	 * @param playerIndex The index of the player losing the development card.
	 */
	public void removeCardFromPlayer(DevCardType devCard, int playerIndex) throws NotEnoughDevCardsException
	{
		try
		{
			playerDevCards[playerIndex].removeDevCard(devCard);
		} catch (NotEnoughDevCardsException e)
		{
			throw e;
		}
	}

	/**
	 * Returns the DevCardList for the given player.
	 * 
	 * @param playerIndex The index of the player requesting the development list.
	 * @return The DevCardList for the given player.
	 */
	public DevCardList getDevCardsForPlayer(int playerIndex)
	{
		return playerDevCards[playerIndex];
	}
	
	

	public void setDevCardsForPlayer(int playerIndex, DevCardList playerDevCards)
	{
		this.playerDevCards[playerIndex] = playerDevCards;
	}

	/**
	 * Check if the given player has the given development card.
	 * 
	 * @param playerIndex
	 * @param devCard
	 * @return
	 */
	public boolean hasDevCard(int playerIndex, DevCardType devCard)
	{
		boolean hasCard = false;
		if(playerIndex < 4 && playerIndex >= 0) {
			hasCard = playerDevCards[playerIndex].hasDevCard(devCard);
		}
		return hasCard;
	}

	public int getCardTypeCountForPlayer(int playerIndex, DevCardType cardType)
	{
		return playerDevCards[playerIndex].getCardTypeCount(cardType);
	}

	public void addDevCards(int playerIndex, DevCardList devCards)
	{
		playerDevCards[playerIndex].plus(devCards);
	}

	
}
