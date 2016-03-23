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
		devCardStack = new DevCardList(2, 5, 2, 14, 2);
		newDevCards = new PlayerDevCards();
		oldDevCards = new PlayerDevCards();
		playedDevCards = new PlayerDevCards();
		hasPlayedDevCardsList = new boolean[] {false, false, false, false};
	}

	public DevCardManager(PlayerDevCards newDevCards, PlayerDevCards oldDevCards, PlayerDevCards playedDevCards, boolean[] hasPlayedDevCardsList, DevCardList devCardStack)
	{
		this.newDevCards = newDevCards;
		this.oldDevCards = oldDevCards;
		this.playedDevCards = playedDevCards;
		this.hasPlayedDevCardsList = hasPlayedDevCardsList;
		this.devCardStack = devCardStack;
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

	public boolean hasDevCard(int playerIndex, DevCardType devCard)
	{
		return newDevCards.hasDevCard(playerIndex, devCard) || oldDevCards.hasDevCard(playerIndex, devCard);
	}
	
	public int getIndexOfLargestArmy()
	{
		int largestArmyIndex = -1; 
		int largestArmySize = -1; 
		for (int i = 0; i < playedDevCards.length(); i++)
		{
			int playerIArmySize = playedDevCards.getCardTypeCountForPlayer(i, DevCardType.SOLDIER);
			if (playerIArmySize > largestArmySize)
			{
				largestArmyIndex = i;
			}
		}
		return largestArmyIndex; 
	}

	public Integer playerDevCardCount(int playerIndex, DevCardType devCard) {
		int unusedCardsCount = 0;
		if(playerIndex < 4 && playerIndex >= 0) {
			unusedCardsCount = newDevCards.getCardTypeCountForPlayer(playerIndex, devCard) + oldDevCards.getCardTypeCountForPlayer(playerIndex, devCard);
		}
		return unusedCardsCount;
	}
	
	public Integer playedDevCardCount(int playerIndex, DevCardType devCard) {
		return playedDevCards.getCardTypeCountForPlayer(playerIndex, devCard);
	}

	public boolean hasDevCards()
	{
		return devCardStack.getDevCardCount() > 0;
	}

	public void addDevCard(DevCardType devCard, int playerIndex) throws NotEnoughDevCardsException
	{
		newDevCards.addCardToPlayer(devCard, playerIndex);
		devCardStack.removeDevCard(devCard);
	}

	public void playDevCard(DevCardType devCard, int playerIndex) throws NotEnoughDevCardsException
	{
		oldDevCards.removeCardFromPlayer(devCard, playerIndex);
		playedDevCards.addCardToPlayer(devCard, playerIndex);
	}

	public void finishTurnRotateCards(int playerIndex)
	{
		oldDevCards.addDevCards(playerIndex, newDevCards.getDevCardsForPlayer(playerIndex));
		newDevCards.setDevCardsForPlayer(playerIndex, new DevCardList());
	}
	
}
