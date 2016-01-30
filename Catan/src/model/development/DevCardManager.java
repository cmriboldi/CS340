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
	private PlayerDevCards newDevCards;
	private PlayerDevCards oldDevCards;
	private PlayerDevCards playedDevCards;
	private boolean[] hasPlayedDevCardsList;

	public DevCardManager()
	{
		devCardStack = new DevCardList();
		newDevCards = new PlayerDevCards();
		oldDevCards = new PlayerDevCards();
		playedDevCards = new PlayerDevCards();
		hasPlayedDevCardsList = new boolean[4];
	}

	public DevCardManager(PlayerDevCards newDevCards, PlayerDevCards oldDevCards, PlayerDevCards playedDevCards, boolean[] hasPlayedDevCardsList)
	{
		this.newDevCards = newDevCards;
		this.oldDevCards = oldDevCards;
		this.playedDevCards = playedDevCards;
		this.hasPlayedDevCardsList = hasPlayedDevCardsList;
		this.devCardStack = calculateDevCardStack();
	}

	private DevCardList calculateDevCardStack()
	{
//		14 Soldiers
//		5 Victory Points
//		2 Monopoly
//		2 Road Building
//		2 Year of Plenty
		
		DevCardList devStack = new DevCardList(2,5,2,14,2);
		devStack.minus(this.newDevCards);
		devStack.minus(this.oldDevCards);
		devStack.minus(this.playedDevCards);
		
		return devStack;
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
	    newDevCards.addCardToPlayer(cardType, playerIndex);
		
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
	public boolean canPlayDevCard(int playerIndex, DevCardType devCard)
	{
		return oldDevCards.hasDevCard(playerIndex, devCard);
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

	public void setDevCardStack(DevCardList devCardStack)
	{
		this.devCardStack = devCardStack;
	}

	public DevCardList getDevCardStack()
	{
		return devCardStack;
	}

	public PlayerDevCards getNewDevCards()
	{
		return newDevCards;
	}
	
	public PlayerDevCards getOldDevCards()
	{
		return oldDevCards;
	}
	
	public PlayerDevCards getPlayedDevCards()
	{
		return playedDevCards;
	}
	
}
