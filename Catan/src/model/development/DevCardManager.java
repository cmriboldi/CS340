package model.development;

import java.util.Random;

import shared.definitions.DevCardType;
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
	private boolean[] hasPlayedDevCardsList;

	public DevCardManager()
	{
		devCardStack = new DevCardList();
		playerDevCards = new PlayerDevCards();
		hasPlayedDevCardsList = new boolean[4];
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
	public DevCardType drawCard(int playerIndex) throws NotEnoughDevCardsException
	{
		DevCardType cardType;
		do{
			int randomInt = new Random().nextInt(4);
			cardType = DevCardType.values()[randomInt];
		} while (!devCardStack.hasDevCard(cardType));
	    
	    devCardStack.removeDevCard(cardType);
	    playerDevCards.addCardToPlayer(cardType, playerIndex);
		
		return cardType;
	}
	
	
	/**
	 * Call to find out if the player has played a development card this turn.
	 * 
	 * @param playerIndex
	 */
	public boolean hasPlayedDevCard(int playerIndex)
	{
		return hasPlayedDevCardsList[playerIndex];
	}
	
	/**
	 * Check if a player has a development card of the given type.
	 * 
	 * @param card
	 * @return
	 */
	public boolean hasDevCard(int playerIndex, DevCardType devCard)
	{
		return playerDevCards.hasDevCard(playerIndex, devCard);
	}
	
	/**
	 * Check if there are enough development cards left to draw one.
	 * 
	 * @param card
	 * @return
	 */
	public boolean canDrawDevCard()
	{
		return devCardStack.getDevCardCount() > 0;
	}
	
}
