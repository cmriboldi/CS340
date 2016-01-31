package model.resources;

import shared.definitions.ResourceType;
import shared.exceptions.player.InvalidPlayerIndexException;
import shared.exceptions.resources.NotEnoughPlayerResourcesException;

/**
 * The Trader handles all Player to Player transactions.
 * 
 * @author Christian Riboldi
 * @author Clayton Condie
 * @author Jacob Brewer
 * @author Joshua Powers
 * @author Joshua Van Steeter
 * @version 1.0 Build Jan, 2016.
 */

public class Trader
{

	private PlayerResources playerResources = null;
	private TradeOffer tradeOffer = null;

	public Trader(PlayerResources newPlayerResources, TradeOffer tradeOffer)
	{
		this.playerResources = newPlayerResources;
		this.tradeOffer = tradeOffer;
	}

	/**
	 * This function is were trading Player to Player is implemented.
	 * 
	 * @param resLists This is an ResourceList where the positive numbers in the arrays are the
	 *            resources going to one player and the negative numbers are coming from another
	 *            player.
	 * @param toPlayerIndex The index of the player who is receiving the positive numbered
	 *            resources.
	 * @param fromPlayerIndex The index of the player who is receiving the negative numbered
	 *            resources.
	 */
	public void tradeWithPlayer(ResourceList resList, int fromPlayerIndex, int toPlayerIndex) throws NotEnoughPlayerResourcesException
	{

	}

	/**
	 * @param playerIndex The index of the player who is using the card.
	 * @param resource The resource that the player is asking for.
	 */
	public void useMonopolyCard(int playerIndex, ResourceType resource)
	{

	}

	public void acceptPlayerTrade(int playerIndex) throws InvalidPlayerIndexException, NotEnoughPlayerResourcesException
	{
		if(playerIndex != tradeOffer.getReceiver())
		{
			throw new InvalidPlayerIndexException("The player index must match the index of the trade offer reciever.");
		}
		boolean senderCanAfforTrade = playerResources.canPlayerAfford(tradeOffer.getSender(), tradeOffer.getResourcesOffer().invert());
		boolean recieverCanAfforTrade = playerResources.canPlayerAfford(tradeOffer.getReceiver(), tradeOffer.getResourcesOffer());
	
		if(!senderCanAfforTrade)
		{
			throw new NotEnoughPlayerResourcesException("The player offering the trade doesn't have enough resources.");
		} else if(!recieverCanAfforTrade)
		{
			throw new NotEnoughPlayerResourcesException("The player accepting the trade doesn't have enough resources.");
		}
		
		playerResources.addResourcesToPlayer(tradeOffer.getResourcesOffer(), tradeOffer.getReceiver());
		playerResources.addResourcesToPlayer(tradeOffer.getResourcesOffer().invert(), tradeOffer.getSender());
		
	}

}
