package model.development;

import shared.exceptions.development.NotEnoughDevCardsException;

/**
 * The DevCardManager handles all interactions with development cards.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */
public class DevCardManager
{

	private DevCardList devCardStack;
	private PlayerDevCards playerDevCards;

	public DevCardManager()
	{
		devCardStack = new DevCardList();
		playerDevCards = new PlayerDevCards();
	}

	/**
	 * Draws a development card for the given player.
	 * 
	 * @pre 0 < playerIndex < 4
	 * 
	 * @post playerDevCards[playerIndex].count++
	 * @post playerDevCards[playerIndex] will have a new random card.
	 * 
	 * @throws NotEnoughDevCardsException if there are no development cards left to draw.
	 * 
	 * @param playerIndex The index of the player drawing the card.
	 */
	public void drawCard(int playerIndex) throws NotEnoughDevCardsException
	{

	}

}
